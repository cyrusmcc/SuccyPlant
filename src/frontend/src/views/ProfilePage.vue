<template>
  <div class="profileContainer">
    <div id="profile" v-if="!noProfile">
      <div id="userInfoCard">
        <profile-pic>
          <img
            id="userPic"
            alt="profile picture"
            src="../assets/imgs/userDark.svg"
          />
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
            <span
              class="navTab"
              id="navTab1"
              @click="updateTab('all')"
              :style="[
                activeTab == 'all'
                  ? { opacity: '1', height: '1.5rem' }
                  : { opacity: '0.6', height: '1rem' },
              ]"
            >
              All
            </span>
            <span
              class="navTab"
              id="navTab2"
              @click="updateTab('posts')"
              :style="[
                activeTab == 'posts'
                  ? { opacity: '1', height: '1.5rem' }
                  : { opacity: '0.6', height: '1rem' },
              ]"
            >
              Posts
            </span>
            <span
              class="navTab"
              id="navTab3"
              @click="updateTab('comments')"
              :style="[
                activeTab == 'comments'
                  ? { opacity: '1', height: '1.5rem' }
                  : { opacity: '0.6', height: '1rem' },
              ]"
            >
              Comments
            </span>
          </div>
        </div>
        <div id="userContent"></div>
      </div>
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
import profilePic from '../components/ProfilePic.vue'
import formattedDate from '../components/FormattedDate.vue'
import userService from '../service/user.service'
import UserPlantList from '../components/UserPlantList.vue'

export default {
  components: { profilePic, formattedDate, UserPlantList },
  name: 'Profile',
  data() {
    return {
      joinDate: '',
      username: this.$route.params.username,
      noProfile: false,
      activeTab: 'all',
      userPlants: [],
    }
  },
  created() {
    userService.getUserProfilePic(this.$route.params.username).then(
      (response) => {
        let imageNode = document.getElementById('userPic')
        let imgUrl = URL.createObjectURL(response.data)
        imageNode.src = imgUrl
      },
      (error) => {
        this.message =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString()
      },
    ),
      userService.getUserProfileInfo(this.$route.params.username).then(
        (response) => {
          this.joinDate = response.data.joinDate
          this.username = response.data.username
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
          this.noProfile = true
        },
      ),
      userService
        .getUserPlants(this.$route.params.username, 'userPlants')
        .then((res) => {
          this.userPlants = res
        })
  },
  methods: {
    updateTab(tab) {
      this.activeTab = tab
    },
  },
}
</script>
<style scoped lang="scss">
.profileContainer {
  display: flex;
}

.card {
  display: flex;
  flex-direction: column;
  row-gap: 1rem;
  justify-content: center;
  align-items: center;
  align-self: center;
  max-width: 95%;
  height: fit-content;
  font: 2rem;
}

#profile {
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  justify-content: center;
  height: 100%;
  width: 100%;
}

#userInfoCard {
  z-index: 1;
  position: relative;
  left: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  overflow: hidden;
  height: fit-content;
  width: 100%;
  box-shadow: none;
}

#userContentCard {
  margin-top: 20px;
  box-shadow: none;
}

#userInfo {
  z-index: 3;
  display: flex;
  flex-direction: column;
  line-height: 1.6rem;
  margin: 15px 0 5px 0;
  text-align: center;
  font-family: $inter;
  color: $primaryDark;
}

#usernameText {
  font-weight: medium;
  font-size: 1.5rem;
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
  display: flex;
  align-items: flex-end;
  width: 100%;
  height: 4vh;
  justify-content: space-around;
  column-gap: 5px;
}

#navTabs > span {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 4rem;
  border-radius: 3px 3px 0 0;
  background-color: $accentDark;
  color: $primaryLight;
  font-size: 0.8rem;
  border-radius: 20px;
  padding: 5px 15px;
  cursor: pointer;
}
#noProfile {
  align-items: center;
  padding: 10px;
}

#noProfileUsername {
  font-size: 3rem;
}

#noProfileUsername > span {
  color: $accentOne;
}

#userPicImgCont {
  height: 6rem;
  width: 6rem;
}

@include screen-md() {
  .card {
    align-items: flex-start;
  }
  .profileContainer {
    display: flex;
    justify-content: center;
    height: 100%;
  }
  #profile {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(4, 1fr);
    grid-column-gap: 15px;
    grid-row-gap: 0px;
    width: 100%;
    align-items: start;
    margin-left: 10px;
    height: 100%;
  }

  #userInfoCard {
    position: sticky;
    top: 45px;
    border-radius: 4px 4px 0 0;
    width: 20rem;
  }

  #userContentCard {
    justify-content: center;
    height: 100%;
    margin: 0;
    width: 40rem;
    align-self: unset;
  }
}
</style>
