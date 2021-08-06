<template>
  <div class="card" id="loginCard">
    <form @submit.prevent="callLogin()">
      <fieldset>
        <p>Sign in</p>
        <div class="form-in">
          <label class="form-label" for="usernameInput">Username</label>
          <input aria-describedby="usernameHelp" class="form-control" id="usernameInput" name="username"
            type="text" required v-model="username">
        </div>
        <div class="form-in">
          <label class="form-label" for="passwordInput">Password</label>
          <input class="form-control" id="passwordInput" name="password"
            type="text" required v-model="password">
        </div>

        <button class="button-primary" type="submit" id="loginButton">Log in</button>

        <router-link to="/register" class="button-primary" id="registerButton">
          Register</router-link>

      </fieldset>
    </form>

  </div>

</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
      error: false,
    }
  },
  methods: {
    callLogin() {
      axios.post('/api/auth/login', {
        username: this.username,
        password: this.password
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      }) 
    }  
  }
}
</script>

<style scoped lang="scss">
  #loginCard {
    color: $lightShade;
    text-align: center;
    padding: 15px;
  }

  form {
    height: 100%;
  }

  fieldset{
    position: relative;
    display: flex;
    align-items: center;
    flex-direction: column;
  }
  h2 {
    font-weight: 0;
  }
  .form-in {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    font-size: 0.8rem;
    width: 100%;
    padding: 10px 0 10px 0;
  }

  .form-label {
    padding-bottom: 5px;
  }

  .form-control{
    background-color: $darkShade;
    color: $lightShade;
    border: none;
    height: 2.5em;
    width: 100%;
    border-radius: 3px;
  }

  .button-primary {
    width: 100%;
    font-size: 0.75rem;
  }

  #loginButton {
    margin-top: 5px;
    align-self: flex-start;
  } 

  #registerButton {
    margin-top: 15px;
    padding: 7px 0;
  }

  @include screen-md() {
    #loginCard {
      padding: 30px;
    }
    .form-in {
      padding: 30px 0 30px 0;
    }
    .form-label {
      padding-bottom: 15px;
    }
    .form-control {
      height: 5em;
    }
    .button-primary {
      padding: 15px;
      font-size: 0.9rem;
    }
    #loginButton {
      margin-top: 30px;
      margin-bottom: 15px;
      align-self: flex-start;
      width: 100%;
    } 
  }

    @include screen-lg() {
      #loginCard {
        font-size: 0.25rem;
        padding: 15px;
      }
      .button-primary {
        font-size: inherit;
        margin-bottom: 0;
      }
      .form-label {
        padding-bottom: 10px;
      }
      .form-in {
        padding: 10px 0 10px 0;
        font-size: inherit;
      }
      .form-control {
        height: 3.5em;
      }
      #loginButton {
        margin-bottom: 0;
      }
      #registerButton {
        padding: 10px 0;
      }
    }

</style>