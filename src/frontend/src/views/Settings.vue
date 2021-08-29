<template>
  <div class="container">
    <modal v-if="getModalState"> </modal>
    <div id="settingTabPanel">
      <span v-if="currentUser">{{ currentUser.username }}'s settings</span>
      <div id="settingTabs">
        <div
          class="settingTab"
          @click="currentSettingTab = 'profile'"
          :style="[
            currentSettingTab == 'profile'
              ? { color: '#09725F', fontWeight: 'bold' }
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
              ? { color: '#09725F', fontWeight: 'bold' }
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
          <label for="picUploadBtn" id="picUploadLabel">Choose File</label>
        </div>
      </div>
      <div id="accountOptions" class="option">
      </div>
    </div>
  </div>
</template>

<script>
import ProfilePic from "../components/ProfilePic.vue";
import Modal from "../components/Modal.vue";
import { modalState } from "../store/comp.store";

export default {
  components: { ProfilePic, Modal },
  name: "Settings",
  data() {
    return {
      currentSettingTab: "profile",
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
  justify-content: space-evenly;
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
  justify-content: flex-start;
  column-gap: 30px;
}

#picUploadBtn {
  display: none;
}

#picUploadLabel {
  border: $accentOne 1px solid;
  border-radius: 4px;
  padding: 5px;
}
</style>