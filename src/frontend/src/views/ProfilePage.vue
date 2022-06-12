<template>
  <div class="profileContainer">
    <div id="profile" v-if="!noProfile">
      <div id="userInfoCard">
        <profile-pic>
          <img id="profileUserPic" alt="profile picture" src="../assets/imgs/userDark.svg" />
        </profile-pic>
        <div id="userInfo">
          <span id="usernameText" v-if="username">{{ username }}</span>
          <span id="joinDateText" v-if="joinDate">
            user since&nbsp;
            <formatted-date :date="joinDate" :format="'month'" />
          </span>
        </div>
        <button class="button-primaryDark-noBorder">Message user</button>
      </div>
      <div class="card" id="userContentCard">
        <user-plant-list :plants="userPlants"></user-plant-list>
        <div id="userContentNavContainer">
          <div id="navTabs">
            <span class="navTab" id="navTab1" @click="updateTab('all')" :style="[
              activeTab == 'all'
                ? { opacity: '1', height: '1.5rem' }
                : { opacity: '0.6', height: '1rem' },
            ]">
              All
            </span>
            <span class="navTab" id="navTab2" @click="updateTab('posts')" :style="[
              activeTab == 'posts'
                ? { opacity: '1', height: '1.5rem' }
                : { opacity: '0.6', height: '1rem' },
            ]">
              Posts
            </span>
            <span class="navTab" id="navTab3" @click="updateTab('comments')" :style="[
              activeTab == 'comments'
                ? { opacity: '1', height: '1.5rem' }
                : { opacity: '0.6', height: '1rem' },
            ]">
              Comments
            </span>
          </div>
        </div>
        <div id="userContent"></div>
      </div>
      <div class="rightBuffer"></div>
    </div>
    <div id="noProfile" class="card" v-if="noProfile">
      <img src="../assets/imgs/exclamationDark.svg" alt="exclamation point" />
      <span>We could not find user</span>
      <span id="noProfileUsername">
        "
        <span>{{ username }}</span>
        "
      </span>
    </div>
  </div>
</template>

<script>
import profilePic from "../components/ProfilePic.vue";
import formattedDate from "../components/FormattedDate.vue";
import userService from "../service/user.service";
import UserPlantList from "../components/UserPlantList.vue";

export default {
  components: { profilePic, formattedDate, UserPlantList },
  name: "Profile",
  data() {
    return {
      joinDate: "",
      username: this.$route.params.username,
      noProfile: false,
      activeTab: "all",
      userPlants: [],
    };
  },
  created() {

    if (this.$root.currentUser.hasProfileImage) {
      userService.getUserProfilePic(this.$route.params.username).then(
        (response) => {
          let imageNode = document.getElementById("profileUserPic");
          let imgUrl = URL.createObjectURL(response.data);
          console.log(imgUrl);
          imageNode.src = imgUrl;
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
    userService.getUserProfileInfo(this.$route.params.username).then(
      (response) => {
        this.joinDate = response.data.joinDate;
        this.username = response.data.username;
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
      userService
        .getUserPlants(this.$route.params.username, "userPlants")
        .then((res) => {
          this.userPlants = res;
        });
  },
  methods: {
    updateTab(tab) {
      this.activeTab = tab;
    },
  },
};
</script>
<style scoped lang="scss">
.profileContainer {
  display: flex;
  height: fit-content;
}

.card {
  align-items: center;
  align-self: center;
  display: flex;
  flex-direction: column;
  font: 2rem;
  height: fit-content;
  justify-content: center;
  max-width: 95%;
  row-gap: 1rem;
}

#profile {
  align-content: flex-start;
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: flex-start;
  min-height: 80vh;
  width: 100%;
}

#userInfoCard {
  align-items: center;
  box-shadow: none;
  display: flex;
  flex-direction: column;
  height: fit-content;
  justify-content: flex-start;
  left: 0;
  overflow: hidden;
  position: relative;
  width: 100%;
  z-index: 1;
}

#userContentCard {
  box-shadow: none;
  margin-top: 20px;
}

#userInfo {
  color: $primaryDark;
  display: flex;
  flex-direction: column;
  font-family: $inter;
  line-height: 1.6rem;
  margin: 15px 0 5px 0;
  text-align: center;
  z-index: 3;
}

#usernameText {
  font-size: 1.5rem;
  font-weight: medium;
}

#joinDateText {
  display: flex;
  flex-direction: row;
  font-size: 0.8rem;
}

#userContentNavContainer {
  border-top: 0;
  border-radius: 4px 4px 0 0;
  height: fit-content;
}

#navTabs {
  align-items: flex-end;
  column-gap: 5px;
  display: flex;
  height: 4vh;
  justify-content: space-around;
  width: 100%;
}

#navTabs>span {
  align-items: center;
  background-color: $primaryDark;
  border-radius: 20px;
  color: $primaryLight;
  cursor: pointer;
  display: flex;
  font-size: 0.8rem;
  justify-content: center;
  padding: 5px 15px;
  width: 4rem;
}

#noProfile {
  align-items: center;
  padding: 10px;
}

#noProfileUsername {
  font-size: 3rem;
}

#noProfileUsername>span {
  color: $accentOne;
}

#userPicImgCont {
  height: 6rem;
  margin-top: 5px;
  width: 6rem;
}

.rightBuffer {
  display: none;
}

@include screen-md() {
  .card {
    align-items: flex-start;
  }

  .profileContainer {
    display: flex;
    justify-content: center;
  }

  #profile {
    align-items: flex-start;
    display: flex;
    flex-direction: row;
    height: 100%;
    justify-content: center;
    margin-left: 10px;
    width: 85%;
  }

  #userInfoCard {
    border-radius: 4px 4px 0 0;
    position: sticky;
    top: 50px;
    width: 20rem;
  }

  #userContentCard {
    align-self: unset;
    justify-content: center;
    margin: 0;
    width: 40rem;
  }

  .rightBuffer {
    display: block;
    width: 20rem;
    background: $primaryLight;
  }
}
</style>
