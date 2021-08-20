<template>
  <div class="home">
    <img id="vueLogo" alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import UserService from "../service/user.service";

export default {
  name: "Home",
  data() {
    return {
      content: "",
    };
  },
  components: {
    HelloWorld,
  },
  mounted() {
    UserService.getPublicContent().then(
      (response) => {
        this.content = response.data;
      },
      (error) => {
        this.content = 
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
<style scoped lang="scss">
  .home {
    margin: 100px auto;
    display: flex;
    align-items: center;
    flex-direction: column;
  }
  #vueLogo {
    max-width: 20rem;
  }
</style>
