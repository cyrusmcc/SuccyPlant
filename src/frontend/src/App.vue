<template>
  <div class="container">
    <div id="nav" v-if="!$route.meta.hideNav">
      <!-- @click="toggleModal"-->
      <hamburger/>
      <router-link to="/"> Home </router-link>
      <router-link to="/profile"> profile </router-link>
      <router-link class="nav-link" to="/about">About</router-link>
      <div class="nav-link" v-if="!currentUser">
        <router-link to="/login">Login</router-link>
      </div>
      <div v-if="currentUser">
        <a class="nav-link" @click.prevent="logOut">Logout</a>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>
import TokenService from "./service/token.service";
import EventBus from "./EventBus";
import Hamburger from "./components/Hamburger.vue";
//import Modal from "./components/Modal.vue";

export default {
  components: {
    Hamburger
 //   Modal
  },
  computed: {
    currentUser() {
      // readd user to local storage after refresh
      if (!TokenService.getUser()) {
        TokenService.setUser(this.$store.state.auth.user);
      }

      return this.$store.state.auth.user;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
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
#nav {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: 2rem;
  background: $accentOne;
  box-shadow: inset 0 0 100px 100px rgba(255, 255, 255, 0.1);
}

#nav a {
  padding-right: 0.5em;
}

#nav a:hover,
#nav a:focus {
  font-weight: bold;
}
</style>
