<template>
  <div class="container">
      <h2>{{currentUser.username}}'s Profile Page</h2>
  </div>
</template>

<script>
export default {
    name: 'Profile',
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        }
    },
    mounted() {
        if (!this.currentUser) {
            this.$router.push('/login');
        }
    }
};
</script>

<style>

</style>