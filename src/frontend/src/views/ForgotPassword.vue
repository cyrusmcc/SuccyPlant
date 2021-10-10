<template>
  <div class="container">
    <img src="../assets/user.svg" alt="user-icon" />

    <div class="card">
      <!-- only works with "handleX", not "handleX()"-->
      <Form @submit="handlePasswordResetRequest" :validation-schema="schema">
        <p class="form-title">Reset password</p>

        <div class="form-in">
          <label for="email" class="form-label">Enter your Email</label>
          <Field name="email" class="form-control" type="email" />
          <ErrorMessage name="email" class="error-feedback" />
        </div>

        <div class="form-submit">
          <button class="button-accentThree-primaryLight" id="resetButton">
            Reset password
          </button>
        </div>

        <div>
          <div v-if="message" class="alert" role="alert">
            {{ message }}
          </div>
        </div>
      </Form>
    </div>

    <div id="pageLinks">
      <div class="pageLink">
        <span>Return to </span>
        <router-link to="/login">Log in</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "ForgotPassword",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      email: yup.string().required("You must provide a valid email"),
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
      this.$router.push("/p/" + this.$store.state.auth.user.username);
    }
  },
  methods: {
    handlePasswordResetRequest(values) {
      this.message = "";
      this.$store.dispatch("settings/requestLostPasswordReset", values).then(
        (data) => {
          this.message = data.message;
          //this.$router.push("/settings");
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
  },
};
</script>
<style scoped lang="scss">
img {
  max-height: auto;
  width: 4.5rem;
  margin: 100px auto 15px auto;
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
  color: $primaryLight;
  text-align: center;
  padding: 15px;
}

.container {
  display: flex;
  flex-direction: column;
  align-content: center;
}

.alert {
  margin-top: 5px;
}

#resetButton {
  margin-bottom: 5px;
  width:100%;
  align-self: flex-start;
}

#pageLinks {
  align-self: center;
  display: flex;
  row-gap: 15px;
  flex-direction: column;
}

.pageLink {
  font-size: 0.85rem;
  color: $primaryLight;
  column-gap: 6px;
}

.pageLink a {
  color: $accentOne;
}
</style>
