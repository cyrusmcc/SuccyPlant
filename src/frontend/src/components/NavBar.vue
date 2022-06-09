<template>
  <div class="navBarContainer">
    <router-link to="/" class="navBarText">succyplant</router-link>
    <div class="modalBackground" v-if="!displayHamburger" @click.self="toggleHamburger()"></div>
    <div class="hamburgerContent" v-if="!displayHamburger" @click="toggleHamburger">
      <slot></slot>
    </div>
    <div class="sideBarContent" v-show="displaySideBar">
      <router-link class="navLink sideBarLink" @click="toggleSideBar" v-if="$root.currentUser"
        :to="'/p/' + $root.currentUser.username">
        Profile
      </router-link>
      <router-link to="/settings" class="navLink sideBarLink" @click="toggleSideBar">
        Settings
      </router-link>
      <router-link to="/" class="navLink sideBarLink" @click="toggleSideBar" @click.prevent="$root.logOut()">
        Logout
      </router-link>
    </div>
    <div class="mobileNavBar" v-if="navType == 'mobile'">
      <div class="hamburgerLineContainer" v-if="displayHamburger" @click="toggleHamburger">
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
      </div>
      <div class="closeHamburgerButton" v-if="!displayHamburger" @click="toggleHamburger">
        +
      </div>
    </div>
    <div class="desktopNavBar" v-if="navType == 'desktop'">
      <div class="navLinks">
        <router-link to="/" class="navLink"> Home </router-link>
        <router-link to="/plants" class="navLink"> Plants </router-link>
      </div>
      <profile-pic v-if="$root.currentUser" @click="toggleSideBar()">
        <img id="navUserPic" alt="profile picture" src="../assets/imgs/userDark.svg" />
      </profile-pic>
      <router-link v-if="!$root.currentUser" to="/login" class="navLink">Login</router-link>
    </div>
  </div>
</template>

<script>
import ProfilePic from "./ProfilePic.vue";
import userService from "../service/user.service";

export default {
  name: "NavBar",
  data() {
    return {
      displayHamburger: true,
      displaySideBar: false,
      screenWidth: window.innerWidth,
      navType: "desktop",
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  created() {
    this.setWindowWidth();

    if (this.currentUser && this.navType == "desktop") {
      let currentUserName = this.currentUser.username;

      userService.getUserProfilePic(currentUserName).then(
        (response) => {
          let imageNode = document.getElementById("navUserPic");
          let imgUrl = URL.createObjectURL(response.data);
          if (imageNode) imageNode.src = imgUrl;
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
    }
  },
  components: {
    ProfilePic,
  },
  methods: {
    toggleHamburger() {
      this.displayHamburger = !this.displayHamburger;
    },
    toggleSideBar() {
      this.displaySideBar = !this.displaySideBar;
    },
    setWindowWidth() {
      this.screenWidth = window.innerWidth;
      if (this.screenWidth < 500) {
        this.navType = "mobile";
      } else {
        this.navType = "desktop";
      }
    },
  },
  mounted() {
    this.setWindowWidth();
    window.onresize = () => {
      this.setWindowWidth();
    };
  },
};
</script>

<style lang="scss" scoped>
.navBarContainer {
  position: relative;
  z-index: 4;
  width: 100%;
  //max-width: 1500px;
  height: 45px;
  position: fixed;
  top: 0;
  max-height: 100%;
  //background: $primaryLight;
  background: $primaryDark;
}

.mobileNav {
  position: relative;
  z-index: 5;
  height: 25px;
  max-width: 100%;
  font-family: $amatic;
  //color: $primaryDark;
  color: $primaryLight;
  font-size: 1.5rem;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 10px 0 10px 10px;
  //background: $primaryLight;
  background: $primaryDark;
}

.desktopNavBar {
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.navLinks {
  display: flex;
  flex-direction: row;
  column-gap: 10px;
  width: fit-content;
}

.sideBarContent {
  z-index: 5px;
  display: flex;
  flex-direction: column;
  border-radius: 4px;
  border: 1px solid $outline;
  width: 150px;
  height: fit-content;
  position: absolute;
  bottom: -99px;
  right: 1px;
  //background: $primaryLight;
  background: $primaryLight;
  box-shadow: $shadowLight;
}

.navBarText {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-family: $amatic;
  //color: $primaryDark;
  color: $primaryLight;
  font-size: 1.5rem;
  cursor: pointer;
  border-bottom: 2px solid transparent;
}

.hamburgerLineContainer {
  position: absolute;
  top: 10px;
  left: 10px;
}

.hamburgerLine {
  width: 20px;
  margin: 4px;
  height: 3px;
  border-radius: 4px;
  //background-color: $primaryDark;
  background-color: $primaryLight;
}

.closeHamburgerButton {
  z-index: 5;
  position: absolute;
  top: -5px;
  left: 12px;
  transform: rotate(45deg);
  //color: $primaryDark;
  color: $primaryLight;
  user-select: none;
  font-size: 45px;
  cursor: pointer;
}

.hamburgerContent {
  position: relative;
  z-index: 5;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 60px 15px 15px 15px;
  row-gap: 20px;
  //background: $primaryLight;
  background: $primaryDark;
  height: fit-content;
  max-width: 100%;
  font-size: 1.2rem;
}

#userPicImgCont {
  width: 2rem;
  height: 2rem;
  cursor: pointer;
}

.navLink {
  color: $primaryLight;
}

.sideBarLink {
  color: $primaryDark;
}
</style>
