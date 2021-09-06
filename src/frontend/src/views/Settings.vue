<template>
  <div class="container">
    <modal v-if="getModalState">
      <div id="changeEmailModal" v-if="modalType == 'changeEmail'">
        <Form @submit="handleChangeEmail" :validation-schema="schema">
          <div class="form-in">
            <label for="email" class="form-label">New email</label>
            <Field name="email" class="form-control" type="email" />
            <ErrorMessage name="email" class="error-feedback" />
          </div>

          <button class="button-primary">Submit change</button>

          <div v-if="message" class="alert" role="alert">
            {{ message }}
          </div>
        </Form>
      </div>
      <div id="changePasswordModal" v-if="modalType == 'changePassword'">
        <Form>
          <div class="form-in">
            <label for="password" class="form-label">New password</label>
            <Field name="password" class="form-control" type="password" />
            <ErrorMessage name="password" class="error-feedback" />
          </div>

          <div class="form-submit">
            <button class="button-primary">Submit change</button>
          </div>
        </Form>
      </div>
    </modal>
    <div id="settingTabPanel">
      <span v-if="currentUser">{{ currentUser.username }}'s settings</span>
      <div id="settingTabs">
        <div
          class="settingTab"
          @click="currentSettingTab = 'profile'"
          :style="[
            currentSettingTab == 'profile'
              ? { color: '#286bc8', fontWeight: 'bold' }
              : { color: '#F5F5F5', fontWeight: 'normal' },
          ]"
        >
          Profile
        </div>
        <div
          class="settingTab"
          @click="currentSettingTab = 'account'"
          :style="[
            currentSettingTab == 'account'
              ? { color: '#286bc8', fontWeight: 'bold' }
              : { color: '#F5F5F5', fontWeight: 'normal' },
          ]"
        >
          Account
        </div>
      </div>
    </div>
    <div class="settingTabOptions">
      <div
        id="profileOptions"
        class="option"
        v-show="currentSettingTab == 'profile'"
      >
        <div id="changePicture">
          <profile-pic>
            <img
              id="userPic"
              src="../assets/profilePicPlaceholder.jpg"
              alt="profile picture"
            />
          </profile-pic>
          <input type="file" id="picUploadBtn" />
          <label for="picUploadBtn" class="labelButton">Choose File</label>
        </div>
      </div>
      <div
        id="accountOptions"
        class="option"
        v-show="currentSettingTab == 'account'"
      >
        <div id="changeEmailContainer" class="settingContainer">
          <div id="changeEmailText">
            <label for="changeEmailText">Email address</label>
            <br />
            <label
              id="currentEmailLabel"
              for="changeEmail"
              v-if="currentUser"
              >&nbsp;&nbsp;{{ currentUser.email }}</label
            >
          </div>
          <label
            for=""
            id="changeEmailButton"
            class="labelButton"
            @click="(modalType = 'changeEmail'), toggleModal()"
            >Change</label
          >
        </div>
        <div id="changePasswordContainer" class="settingContainer">
          <div id="changePasswordText">Account password</div>
          <label
            for=""
            id="changePasswordButton"
            class="labelButton"
            @click="(modalType = 'changePassword'), toggleModal()"
            >Change</label
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProfilePic from "../components/ProfilePic.vue";
import Modal from "../components/Modal.vue";
import { modalState } from "../store/comp.store";
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  components: { ProfilePic, Modal, Form, Field, ErrorMessage },
  name: "Settings",
  data() {
    const schema = yup.object().shape({
      email: yup.string().email().required("You must provide a new email"),
    });

    return {
      successful: false,
      loading: false,
      message: "",
      schema,
      currentSettingTab: "profile",
      modalType: "none",
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    getModalState() {
      return modalState.modalActive;
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }
  },
  methods: {
    toggleModal() {
      if (this.getModalState) {
        modalState.modalActive = false;
      } else {
        modalState.modalActive = true;
      }
    },
    handleChangeEmail(values) {
      this.successful = false;
      this.loading = true;
      this.message = "";
      this.$store.dispatch("auth/changeEmail", values).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
          this.$router.push("/settings");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },
  },
};
</script>

<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  width: 100%;
  color: $lightShade;
}

.settingTabOptions {
  width: 80%;
}

.option {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.settingContainer {
  font-size: 0.9rem;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  column-gap: 50px;
}

#userPic {
  height: 4.8rem;
}

#settingTabPanel {
  margin-top: 50px;
  box-shadow: none;
  width: 80%;
  max-width: 80%;
  display: flex;
  flex-direction: column;
  row-gap: 20px;
}

#settingTabs {
  border: $accentShade solid 1px;
  border-radius: 4px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  row-gap: 20px;
  margin-bottom: 20px;
}

#changePicture {
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-evenly;
}

#picUploadBtn {
  display: none;
}

#accountOptions > * {
  margin-bottom: 20px;
}

#currentEmailLabel {
  font-size: 0.7rem;
}
</style>