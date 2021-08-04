<template>
  <div>
    <LoginForm />
    <button @click="callHelloApi()">Call</button>
    <h3>{{ response }}</h3>
  </div>
</template>

<script>
import LoginForm from '../components/LoginForm.vue';
//import api from '../api/backend-api';
//import axios, {AxiosError} from 'axios';
import axios from 'axios';

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
    callHelloApi() {
      axios.get('api/hello')
        .then(response => {
          this.response = response.data;
        })
        .catch(e => {
          this.errors.push(e);
        })
    }
  }
}
</script>

<style scoped lang="scss">

</style>