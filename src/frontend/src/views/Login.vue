<template>
  <div>
    <LoginForm />
  </div>
</template>

<script>
import LoginForm from '../components/LoginForm.vue';
//import api from '../api/backend-api';
//import axios, {AxiosError} from 'axios';
//import axios from 'axios';

export default {
  name: "Login.vue",
  components: { LoginForm },
  data() {
    return {
      response: [],
      errors: []
    }
  },
  methods: {
  }
}
</script>

<style scoped lang="scss">

</style>