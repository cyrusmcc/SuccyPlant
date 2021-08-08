<template>
  <div class="card" id="loginCard">
    <!-- only works with "handleLogin", not "handeLogin()"-->
    <Form @submit="handleLogin" :validation-schema="schema">
        <p>Sign in</p>

        <div class="form-in">
          <label for="username" class="form-label">Username</label>
          <Field name="username" class="form-control" type="username" />
          <ErrorMessage name ="username" class="error-feedback" />
        </div>
        
        <div class="form-in">
          <label class="form-label" for="password">Password</label>
          <Field name="password" class="form-control" type="password" />
          <ErrorMessage name ="password" class="error-feedback" />
        </div>

        <button class="button-primary" id="loginButton">Log in</button>

        <div>
          <div v-if="message" class = "alert" role="alert">
            {{ message }}
          </div>
        </div>
    </Form>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "Login",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup.string().required("Username is required!"),
      password: yup.string().required("Password is required!"),
    });

    return {
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/");
    }
  },
  methods: {
    handleLogin(user) {
      this.loading = true;

      this.$store.dispatch("auth/login", user).then(
        () => {
          this.$router.push("/");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },
  },
};
</script>
<style scoped lang="scss">
  .card {
    color: $lightShade;
    text-align: center;
    padding: 15px;
  }

  p {
    font-size: 1.5rem;
  }

  Form {
    height: 100%;
  }

  fieldset{
    position: relative;
    display: flex;
    align-items: center;
    flex-direction: column;
  }
  .form-in {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
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
    line-height: 2rem;
    width: 100%;
    border-radius: 3px;
  }

  #loginButton {
    margin-top: 5px;
    align-self: flex-start;
  } 

  #registerButton {
    margin-top: 15px;
    padding: 7px 0;
  }

  /*
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
    */

</style>