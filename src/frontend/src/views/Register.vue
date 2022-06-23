<template>
  <div class="container">
    <img src="../assets/imgs/potSuccZigDark.svg" alt="user-icon" />

    <div class="card" id="registerCard">
      <!-- only works with "handleX", not "handeX()"-->
      <Form @submit="handleRegister" :validation-schema="schema">
        <p class="form-title">Register new account</p>

        <div class="form-in">
          <label for="username" class="form-label">Username</label>
          <Field name="username" class="form-control" type="username" />
          <ErrorMessage name="username" class="error-feedback" />
        </div>

        <div class="form-in">
          <label class="form-label" for="password">Password</label>
          <Field name="password" class="form-control" type="password" />
          <ErrorMessage name="password" class="error-feedback" />
        </div>

        <div class="form-in">
          <div id="email-label">
            <label for="email" class="form-label">Email</label>
            <span>(Optional)</span>
          </div>
          <Field name="email" class="form-control" type="email" />
          <ErrorMessage name="email" class="error-feedback" />
        </div>

        <terms-and-policy></terms-and-policy>

        <button class="button-primaryDark-noBorder" id="registerButton">
          Register
        </button>

        <div>
          <div v-if="message" class="alert" role="alert">
            {{ message }}
          </div>
        </div>
      </Form>
    </div>

    <div id="login-link">
      <span>Already have an account?</span>
      <router-link to="/login"> Login </router-link>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import TermsAndPolicy from '../components/termsAndPolicy.vue';

export default {
  name: "Register",
  components: {
    Form,
    Field,
    ErrorMessage,
    TermsAndPolicy,
  },
  data() {
    const schema = yup.object().shape({
      username: yup
        .string()
        .required("Username is required!")
        .min(3)
        .max(16, "Username cannot be greater than 16 characters long."),
      email: yup.string().email("Email is invalid."),
      password: yup.string().required("Password is required!").min(8).max(256),
    });

    return {
      successful: false,
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
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/");
    }
  },
  methods: {
    handleRegister(user) {
      this.successful = false;
      this.loading = true;
      this.message = "";

      this.$store.dispatch("auth/register", user, this.email).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
          this.$router.push("/login");
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
img {
  margin: 0 auto 15px auto;
  max-height: auto;
  width: 7.5rem;
}

Form {
  height: 100%;
}

fieldset {
  align-items: center;
  display: flex;
  flex-direction: column;
  position: relative;
}

.card {
  text-align: center;
  padding: 15px;
}

.container {
  align-content: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

#registerButton {
  align-self: flex-start;
  margin: 5px 0 5px 0;
  width: 100%;
}

#login-link {
  color: $primaryDark;
  column-gap: 6px;
  display: flex;
  font-size: 0.85rem;
  justify-content: center;
}

#login-link a {
  color: $highlightOne;
}

#email-label {
  align-content: center;
  column-gap: 5px;
  display: flex;
}

#email-label span {
  align-self: center;
  font-size: 0.75em;
  padding-bottom: 5px;
}
</style>
