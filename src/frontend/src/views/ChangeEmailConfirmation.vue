<template>
  <div class="container">
    <div class="card">
        <div v-if="message" class="alert" role="alert">
          {{ message }}
        </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChangeEmailConfirmation",
  data() {

    return {
      message: "",
      emailChangeToken: "",

    }
  },
  created() {
      this.message = "";
      this.emailChangeToken = this.$route.params.emailToken;
      console.log(this.emailChangeToken);
      this.$store.dispatch('auth/handleEmailChange', this.emailChangeToken).then(
        (data) => {
          this.message = data.message;
          //this.$router.push("/login");
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

}
</script>

<style style="scoped" lang="scss">
  .card {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 5rem;
    padding: 15px;
    margin: 150px auto;
    color: $lightShade;
  }
  .alert {
    background:none;
  }
</style>