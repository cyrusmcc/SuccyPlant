<template>
  <div class="container">
      <div class="card" id="userInfoCard">
        <div id="loginCardAccent"></div>
        <div class="imgCont">
          <img src="../assets/profilePicPlaceholder.jpg" alt="profile picture">
        </div>
        <div id="userInfo">
          <span id="usernameText">{{ currentUser.username }}</span>
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
        <div id="userContent">

        </div>
      </div>
  </div>
</template>

<script>
import TokenService from '../service/token.service'

export default {
  name: "Profile",
  computed: {
    currentUser() {
      console.log(TokenService.getUser());
      return TokenService.getUser();
    },
    getUserJoinDate() {
      if(this.currentUser) {
        const userJoinDate = new Date(this.currentUser.joinDate),
        locale = 'en-us',
        month = userJoinDate.toLocaleString(locale, { month: "short" }),
        year = userJoinDate.toLocaleString(locale, { year: "numeric"}),
        joined = month + " " + year;
        return joined;
      }
        return null;
    }
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
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  flex-flow: wrap;
}

.card {
  max-width: 90%;
}

.imgCont {
  z-index: 3;
  border-radius: 50%;
  max-height: 4.5em;
  width: 4.5rem;
  overflow: hidden;
  margin-left: 10px;
  border: 4px solid;
  border-color: $accentShade;
}

#loginCardAccent {
  z-index: 2;
  top: 0;
  left: 0;
  position: absolute;
  width: inherit;
  height: 55%;
  background-color: $accentThree;
}

#userInfoCard {
  z-index: 1;
  position: relative;
  display: flex;
  align-content: center;
  overflow: hidden;
  padding: 10px 0 10px 0;
}

#userContentCard {
  margin-top: 0;
  background-color: $darkShade;
  box-shadow: none;
}

#userInfo {
  z-index: 3;
  display: flex;
  flex-direction: column;
  margin: 20px 0 0 10px;
  line-height: 1.6rem;
}


#usernameText {
  font-weight: bold;
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
  height: 68vh;
  background: $accentShade;
}


</style>