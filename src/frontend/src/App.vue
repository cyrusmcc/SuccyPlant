<template>
<div>
    <div id="nav" v-if="!$route.meta.hideNav">
    <router-link to="/">
      Home
    </router-link>
        <router-link to="/profile">
      profile
    </router-link>
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
import TokenService from "./service/token.service"
import EventBus from "./EventBus";

export default {
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
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  },
  mounted() {
    EventBus.on("logout", () => {
      this.logOut();
    });
  },
  beforeUnmount() {
    EventBus.remove("logout");
  }
};
</script>

<style lang="scss">
#nav {
  display: flex;
  justify-content: flex-end;
  margin: 0.5em;
}

#nav a {
  padding-right: 0.5em;
}

#nav a:hover, #nav a:focus {
  font-weight: bold;
}

</style>
