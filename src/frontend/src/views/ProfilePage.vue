<template>
  <div class="container">
    <router-link to="/"
      ><img class="backArrow" src="../assets/backarrow.svg" alt="back-arrow" />
    </router-link>
    <div id="profile" v-if="!noProfile">
      <div id="userInfoCard">
        <profile-pic>
          <img id="userPic" alt="profile picture" src="../assets/user.svg" />
        </profile-pic>
        <div id="userInfo">
          <span id="usernameText" v-if="username">{{ username }}</span>
          <span id="joinDateText">user since {{ joinDate }}</span>
        </div>
      </div>
      <div class="card" id="userContentCard">
        <div id="userContentNavContainer">
          <div id="navTabs">
            <span class="navTab" id="navTab1">All</span>
            <span class="navTab" id="navTab2">Posts</span>
            <span class="navTab" id="navTab3">Comments</span>
          </div>
        </div>
        <div id="userContent"></div>
      </div>
    </div>
    <div id="noProfile" class="card" v-if="noProfile">
      <img src="../assets/exclamation.svg" alt="exclamation point">
      <span>We could not find user</span>
      <span id ="noProfileUsername">"<span>{{ username }}</span>"</span>
    </div>
  </div>
</template>

<script>
import ProfilePic from "../components/ProfilePic.vue";
import UserService from "../service/user.service";

export default {
  components: { ProfilePic },
  name: "Profile",
  data() {
    return {
      joinDate: "",
      username: this.$route.params.username,
      noProfile: false,
    };
  },
  computed: {
    getUserJoinDate() {
      if (this.currentUser) {
        const userJoinDate = new Date(this.currentUser.joinDate),
          locale = "en-us",
          month = userJoinDate.toLocaleString(locale, { month: "short" }),
          year = userJoinDate.toLocaleString(locale, { year: "numeric" }),
          joined = month + " " + year;
        return joined;
      }
      return null;
    },
  },
  mounted() {
    UserService.getUserProfilePic(this.$route.params.username).then(
      (response) => {
        let imageNode = document.getElementById("userPic");
        let imgUrl = URL.createObjectURL(response.data);
        imageNode.src = imgUrl;
      },
      (error) => {
        this.message =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        this.noProfile = true;
      }
    ),
      UserService.getUserProfileInfo(this.$route.params.username).then(
        (response) => {
          const userJoinDate = new Date(response.data.joinDate),
            locale = "en-us",
            month = userJoinDate.toLocaleString(locale, { month: "short" }),
            year = userJoinDate.toLocaleString(locale, { year: "numeric" }),
            joined = month + " " + year;
          this.joinDate = joined;
          this.username = response.data.username;
        }
      );
  },
};
</script>
<style scoped lang="scss">
.container {
  display: flex;
}

.card {
  display: flex;
  flex-direction: column;
  row-gap: 1.5rem;
  justify-content: center;
  align-items: center;
  align-self: center;
  max-width: 95%;
  height: fit-content;
  color: $lightShade;
  padding: 1.5rem 0 1.5rem 0;
  font: 2rem;
}

#profile {
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  flex-flow: wrap;
  width: 100%;
}

#userInfoCard {
  z-index: 1;
  position: relative;
  display: flex;
  flex-direction: column;
  height: 8rem;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  padding: 20px 0 5px 0;
  width: 100%;
  background-color: $accentOne;
  box-shadow: none;
}

#userInfoCard > * {
  margin-bottom: 5px;
}

#userContentCard {
  margin-top: 20px;
  background-color: $darkShade;
  box-shadow: none;
}

#userInfo {
  z-index: 3;
  display: flex;
  flex-direction: column;
  line-height: 1.6rem;
  text-align: center;
}

#usernameText {
  font-weight: bold;
  color: $lightShade;
  font-size: 1.5rem;
}

#joinDateText {
  color: $lightShade;
  font-size: 0.8rem;
}

#userContentNavContainer {
  border-top: 0;
  border-radius: 4px 4px 0 0;
  height: fit-content;
  background: $darkShade;
}

#navTabs {
  display: flex;
  align-items: flex-end;
  width: 100%;
  height: 4vh;
  justify-content: space-around;
}

#navTabs > span {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  border-radius: 3px 3px 0 0;
  background-color: $accentShade;
  color: $lightShade;
  padding: 2px;
}

#navTab1 {
  opacity: 1;
  height: 3.5vh;
}

#navTab2 {
  border: thin solid $darkShade;
  border-bottom: 0;
  border-radius: 3px;
  opacity: 0.6;
}

#navTab3 {
  opacity: 0.6;
}

#noProfileUsername {
  font-size: 3rem;
}

#noProfileUsername > span{
  color: $accentOne;
}

@include screen-lg() {
  #userInfoCard {
    background-color: $accentTwo;
  }
}
</style>