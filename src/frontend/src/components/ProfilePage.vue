<template>
  <div class="container">
      <span>{{ currentUser.username }}</span>
  </div>
</template>

<script>
import TokenService from '../service/token.service'

export default {
  name: "Profile",
  computed: {
    currentUser() {
      return TokenService.getUser();
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }
  },
};
</script>