<template>
  <div class="container">
    <!-- setting forms hidden in modal -->
    <modal v-if="showModal" @closeModal="toggleModal">
      <div id="changeEmailModal" v-if="getModalType == 'changeEmail'">
        <Form
          @submit="handleChangeEmailRequest"
          :validation-schema="emailSchema"
        >
          <p class="form-title">Change account email</p>
          <div class="form-in">
            <Field
              name="password"
              class="form-control"
              type="password"
              placeholder="Current password"
            />
            <ErrorMessage name="password" class="error-feedback" />
          </div>
          <div class="form-in">
            <Field
              name="email"
              class="form-control"
              type="email"
              placeholder="New email"
            />
            <ErrorMessage name="email" class="error-feedback" />
          </div>

          <button class="button-accentTwo-primaryDark">Submit change</button>

          <div v-if="loading" class="alert" role="alert">
            Submitting request...
          </div>
          <div v-if="message" class="alert" role="alert">
            {{ message }}
          </div>
        </Form>
      </div>
      <div
        id="changeEmailModalSuccess"
        v-if="getModalType == 'changeEmailSuccess'"
      >
        We've sent a confirmation email to the address provided. Click the link
        provided to update your account's email.
      </div>
      <div id="changePasswordModal" v-if="getModalType == 'changePassword'">
        <Form
          @Submit="handlePasswordResetRequest"
          :validation-schema="passwordSchema"
        >
          <p class="form-title">Change account password</p>
          <div class="form-in">
            <Field
              name="currentPassword"
              class="form-control"
              type="password"
              placeholder="Current password"
            />
            <ErrorMessage name="currentPassword" class="error-feedback" />
          </div>
          <div class="form-in">
            <Field
              name="newPassword"
              class="form-control"
              type="password"
              placeholder="New password"
            />
            <ErrorMessage name="newPassword" class="error-feedback" />
          </div>
          <div class="form-in">
            <Field
              name="confirmNewPassword"
              class="form-control"
              type="password"
              placeholder="Confirm new password"
              rules="confirmed:newpassword"
            />
            <ErrorMessage name="confirmNewPassword" class="error-feedback" />
          </div>

          <div class="form-submit">
            <button class="button-accentTwo-primaryDark">Submit change</button>
          </div>

          <div v-if="message" class="alert" role="alert">
            {{ message }}
          </div>
        </Form>
      </div>
    </modal>
    <!-- setting options -->
    <div id="settingContainer">
      <div id="settingTabPanel">
        <span v-if="currentUser">{{ currentUser.username }}'s settings</span>
        <div id="settingTabs">
          <div
            class="settingTab"
            @click="currentSettingTab = 'profile'"
            :style="[
              currentSettingTab == 'profile'
                ? { color: '#004da2', fontWeight: 'bold' }
                : { color: '#121113', fontWeight: 'normal' },
            ]"
          >
            Profile
          </div>
          <div
            class="settingTab"
            @click="currentSettingTab = 'account'"
            :style="[
              currentSettingTab == 'account'
                ? { color: '#004da2', fontWeight: 'bold' }
                : { color: '#121113', fontWeight: 'normal' },
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
            <profile-pic-upload />
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
              <label id="currentEmailLabel" for="changeEmail" v-if="currentUser"
                >&nbsp;&nbsp;{{ currentUser.email }}</label
              >
            </div>
            <label
              for=""
              id="changeEmailButton"
              class="button-primaryLight-highlightOne"
              @click="toggleModal('changeEmail')"
              >Change</label
            >
          </div>
          <div id="changePasswordContainer" class="settingContainer">
            <div id="changePasswordText">Account password</div>
            <label
              for=""
              id="changePasswordButton"
              class="button-primaryLight-highlightOne"
              @click="toggleModal('changePassword')"
              >Change</label
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Modal from "../components/Modal.vue";
import ProfilePicUpload from "../components/ProfilePicUpload.vue";
import { Form, Field, ErrorMessage, defineRule } from "vee-validate";
import { dimensions } from "@vee-validate/rules";

import * as yup from "yup";

defineRule("confirmed", (value, [target], ctx) => {
  if (value === ctx.form[target]) {
    return true;
  }

  return "Passwords must match";
});

defineRule("dimensions", dimensions);

export default {
  components: {
    Modal,
    ProfilePicUpload,
    Form,
    Field,
    ErrorMessage,
  },
  name: "Settings",
  data() {
    const emailSchema = yup.object().shape({
      email: yup.string().email().required("You must provide a new email"),
      password: yup.string().required(),
    });

    const passwordSchema = yup.object().shape({
      currentPassword: yup
        .string()
        .required("You must provide your current password"),
      newPassword: yup.string().required("You must provide a new password"),
    });

    return {
      successful: false,
      loading: false,
      message: "",
      emailSchema,
      passwordSchema,
      modalType: "",
      showModal: false,
      currentSettingTab: "profile",
      fileName: "Choose File",
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    getModalType() {
      return this.modalType;
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }
  },
  methods: {
    toggleModal(type) {
      this.showModal = !this.showModal;

      if (this.showModal && type != null) {
        this.modalType = type;
      }
    },
    handleChangeEmailRequest(values) {
      this.successful = false;
      this.loading = true;
      this.message = "";

      this.$store.dispatch("settings/changeEmailRequest", values).then(
        (data) => {
          this.modalType = "changeEmailSuccess";
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
    handlePasswordResetRequest(values) {
      this.message = "";
      this.$store.dispatch("settings/requestSettingPasswordReset", values).then(
        (data) => {
          this.message = data.message;
          this.$router.push("/login");
        },
        (error) => {
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
}

.settingTab {
  cursor: pointer;
  width: fit-content;
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
}

.button-primaryLight-highlightOne {
  padding: 0 3px 0 3px;
}

.button-accentTwo-primaryDark {
  margin-bottom: 10px;
  width: 100%;
}

#settingContainer {
  width: 85%;
  max-width: 50rem;
}

#userPic {
  height: 4.8rem;
}

#settingTabPanel {
  margin-top: 50px;
  box-shadow: none;
  display: flex;
  flex-direction: column;
  row-gap: 20px;
}

#settingTabs {
  border: $accentDark solid 1px;
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

#accountOptions > * {
  margin-bottom: 20px;
}

#currentEmailLabel {
  font-size: 0.7rem;
  color: $highlightOne;
}
</style>
