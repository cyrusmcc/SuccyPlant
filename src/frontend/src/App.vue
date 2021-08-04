<template>
<div>
    <div id="nav" v-show="$route.name !== 'Login'">
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
    <router-link to="/login">Login</router-link>
  </div>
  <router-view />
</div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  methods: {
  },
}
</script>

<style lang="scss">
//#app {
//}

#nav {
  display: flex;
  justify-content: flex-end;
  margin: 0.5em;
}

#nav a {
  padding-right: 0.5em;
}

</style>
