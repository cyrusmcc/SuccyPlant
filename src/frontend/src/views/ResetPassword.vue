<template>
  <div class="container">
    <div class="card">
      <Form
        @submit="handleResetPassword"
        :validation-schema="schema"
        :initial-values="formValues"
      >
        <p class="form-title">Reset password</p>

        <div class="form-in">
          <label for="password" class="form-label">Enter new password</label>
          <Field name="password" class="form-control" type="email" />
          <ErrorMessage name="password" class="error-feedback" />
        </div>

        <div class="form-submit">
          <button class="button-primary" id="resetButton">
            Change password
          </button>
        </div>

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
  components: { Form, Field, ErrorMessage },
  name: "ResetPassword",
  data() {
    const schema = yup.object().shape({
      password: yup
        .string()
        .required("You must provide a new password")
        .min(8)
        .max(256),
      token: yup.string().required(),
    });

    const formValues = {
      password: "",
      token: this.$route.params.passToken,
    };

    return {
      message: "",
      formValues,
      schema,
    };
  },
  methods: {
    handleResetPassword(values) {
      this.message = "";
      this.$store.dispatch("settings/handlePasswordReset", values).then(
        (data) => {
          this.message = data.message;
          this.$router.push("/login");
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

.alert {
  margin-top: 5px;
}

#resetButton {
  margin-top: 5px;
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
  color: $lightShade;
  column-gap: 6px;
}

.pageLink a {
  color: $accentOne;
}
</style>
