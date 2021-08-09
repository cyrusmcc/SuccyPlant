<template>
  <div class="container">
    <img src="../assets/users.svg" alt="user-icon" />

    <div class="card" id="registerCard">
      <!-- only works with "handleX", not "handeX()"-->
      <Form @submit="handleRegister" :validation-schema="schema">
        <p>Register new account</p>

        <div class="form-in">
          <label for="username" class="form-label">Username</label>
          <Field name="username" class="form-control" type="username" />
          <ErrorMessage name="username" class="error-feedback" />
        </div>

        <div class="form-in">
          <label for="email" class="form-label">Email(Optional)</label>
          <Field name="email" class="form-control" type="email" />
          <ErrorMessage name="email" class="error-feedback" />
        </div>

        <div class="form-in">
          <label class="form-label" for="password">Password</label>
          <Field name="password" class="form-control" type="password" />
          <ErrorMessage name="password" class="error-feedback" />
        </div>

        <button class="button-primary" id="registerButton">Register</button>

        <div>
          <div v-if="message" class="alert" role="alert">
            {{ message }}
          </div>
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "Register",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup.string().required("Username is required!").min(3),
      email: yup.string().email(),
      password: yup.string().required("Password is required!").min(8),
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

      this.$store.dispatch("auth/register", user).then(
        () => {
          this.message = data.message;
          this.successful = true;
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
  max-height: auto;
  width: 4.5rem;
  margin: 100px auto 15px auto;
}

p {
  font-size: 1.5rem;
}

Form {
  height: 100%;
}

fieldset {
  position: relative;
  display: flex;
  align-items: center;
  flex-direction: column;
}

.card {
  color: $lightShade;
  text-align: center;
  padding: 15px;
}

.container {
  display: flex;
  flex-direction: column;
  align-content: center;

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

.form-control {
  background-color: $darkShade;
  color: $lightShade;
  border: none;
  line-height: 2rem;
  width: 100%;
  border-radius: 3px;
}

#registerButton {
  margin-top: 5px;
  align-self: flex-start;
}
</style>