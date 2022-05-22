<template>
  <div class="viewContainer">
    <nav-bar v-show="!$route.meta.hideNav">
      <router-link class="navLink" to="/">Home</router-link>
      <router-link class="navLink" to="/plants">Plants</router-link>
      <router-link
        class="navLink"
        :to="'/p/' + currentUser.username"
        v-if="currentUser"
      >
        Profile
      </router-link>
      <router-link class="navLink" to="/settings" v-if="currentUser">
        Settings
      </router-link>
      <div class="navLink" v-if="!currentUser">
        <router-link to="/login">Login</router-link>
      </div>
      <div v-if="currentUser">
        <a class="navLink" @click.prevent="logOut">Logout</a>
      </div>
    </nav-bar>
    <router-view />
  </div>
  <page-footer v-show="!$route.meta.hideFooter" />
</template>

<script>
import TokenService from "./service/token.service";
import EventBus from "./EventBus";
import NavBar from "./components/NavBar.vue";
import PageFooter from "./components/PageFooter.vue";

export default {
  components: {
    NavBar,
    PageFooter,
  },
  computed: {
    currentUser() {
      // read user to local storage after refresh
      if (!TokenService.getUser()) {
        TokenService.setUser(this.$store.state.auth.user);
      }

      return this.$store.state.auth.user;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout", this.currentUser).then(() => {
        this.$router.push("/login");
      });
    },
  },
  mounted() {
    EventBus.on("logout", () => {
      this.logOut();
    });
  },
  beforeUnmount() {
    EventBus.remove("logout");
  },
};
</script>

<style lang="scss">
#app {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: fit-content;
  min-height: 100vh;
  padding-bottom: 10rem;
}
.viewContainer {
  max-width: 1500px;
  height: 100%;
  width: 100%;
  padding-top: 45px;
}
</style>
