<template>
  <div class="container">
    <router-link to="/"
      ><img class="backArrow" src="../assets/backarrow.svg" alt="back-arrow" />
    </router-link>
    <div id="userInfoCard">
      <profile-pic>
        <img
          id="userPic"
          src="../assets/user.svg"
          alt="profile picture"
        />
      </profile-pic>
      <div id="userInfo">
        <span id="usernameText" v-if="currentUser">{{
          currentUser.username
        }}</span>
        <span id="joinDateText">user since {{ getUserJoinDate }}</span>
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
</template>

<script>
import ProfilePic from "../components/ProfilePic.vue";

export default {
  components: { ProfilePic },
  name: "Profile",
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
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
    if (!this.currentUser) {
      this.$router.push("/login");
    }
  },
};
</script>
<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  flex-flow: wrap;
}

.card {
  max-width: 95%;
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

#userPic {
  height: 4.8rem;
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
  display: flex;
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

#userContent {
  //height: 68vh;
  //background: $accentShade;
}
</style>