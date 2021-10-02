<template>
  <div class="container">
    <hamburger v-show="!$route.meta.hideNav">
      <router-link to="/"> Home </router-link>
      <router-link :to="'/p/' + currentUser.username" v-if="currentUser"> profile </router-link>
      <router-link class="nav-link" to="/settings" v-if="currentUser">User Settings</router-link>
      <div class="nav-link" v-if="!currentUser">
        <router-link to="/login">Login</router-link>
      </div>
      <div v-if="currentUser">
        <a class="nav-link" @click.prevent="logOut">Logout</a>  
      </div>
    </hamburger>

    <blog-feed />

    <router-view />
  
  
  </div>
</template>

<script>
import TokenService from "./service/token.service";
import EventBus from "./EventBus";
import Hamburger from "./components/Hamburger.vue";
import BlogFeed from "./components/BlogFeed.vue";

export default {
  components: {
    Hamburger,
    BlogFeed,
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
      this.$store.dispatch("auth/logout", this.currentUser).then(
        () => {
          this.$router.push("/login");
        }
      );
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
  height: 100%;
}
</style>
