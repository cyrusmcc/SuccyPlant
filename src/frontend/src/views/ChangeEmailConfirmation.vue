<template>
  <div class="container">
    <div class="card">
      <div v-if="message" class="alert" role="alert">
        {{ message }}
      </div>
      <router-link class="pageLink" to="/login">Return to login</router-link>
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
    };
  },
  created() {
    this.message = "";
    this.emailChangeToken = this.$route.params.emailToken;
    this.$store
      .dispatch("settings/handleEmailChange", this.emailChangeToken)
      .then(
        (data) => {
          this.message = data.message;
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
  },
};
</script>

<style style="scoped" lang="scss">
.card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  align-items: center;
  height: 5rem;
  padding: 15px;
  margin: 150px auto;
  color: $lightShade;
}
.alert {
  background: none;
}

.pageLink {
  margin-top: 10px;
  font-size: 0.85rem;
  color: $accentOne;
}

.pageLink a {
  color: $lightShade;
}

</style>