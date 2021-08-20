<template>
<div>
    <div id="nav" v-if="!$route.meta.hideNav">
    <router-link to="/">
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
export default {
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  },
}
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
