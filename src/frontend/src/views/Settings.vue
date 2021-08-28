<template>
  <div class="container">
    <div id="settingTabPanel">
      <div id="userInfo">
      <profile-pic>
        <img id="userPic" src="../assets/profilePicPlaceholder.jpg" alt="profile picture" />
      </profile-pic>
        <span v-if="currentUser">{{ currentUser.username }}'s settings</span>
      </div>
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
    <div id="settingTabOptions">
      <div id="profileOptions" class="option" v-show="currentSettingTab == 'profile'">
        <div id="changePicture">
          <input type="file" id="picUploadBtn">
          <label for="picUploadBtn" id="picUploadLabel">Choose File</label>
          <div> {{ modalState }}</div>
        </div>
      </div>
      <div id="accountOptions" class="option">
        <modal></modal>
        <modal></modal>
      </div>
    </div>
  </div>
</template>

<script>
import ProfilePic from "../components/ProfilePic.vue";
import Modal from "../components/Modal.vue";

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
    modalState() {
      return this.$store.state.comp.displayModal;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }
  },
  methods: {
    displayTab() {
      console.log(this.currentSettingTab);
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

#userInfo {
  font-size: 1.2rem;
  display: flex;
  flex-direction: row;
  column-gap: 20px;
  align-items: center;
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

#profileOptions {
  
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