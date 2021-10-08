<template>
  <div class="container">
    <div id="newBlog">
      <Form @submit="handleNewBlog" :validation-schema="schema">
        <div class="form-in">
          <label for="title" class="form-label">Blog Title</label>
          <Field name="title" type="text" class="form-control" />
          <ErrorMessage name="title" class="error-feedback" />

          <Field
            id="fileInput"
            name="image"
            type="file"
            rules="image"
            @change="previewPic()"
          />
          <label
            id="imageUploadButton"
            class="labelButton"
            for="fileInput"
            v-html="fileName"
          />
        </div>

        <div id="imgContainer">
          <span id="imgContainerText">Cover image</span>
          <img id="imagePreview" src="" alt="blog image preview" />
        </div>

        <ErrorMessage name="image" class="error-feedback" />

        <text-editor name="bodyText" @bodyText="setBodyText" />

        <ErrorMessage name="bodyText" class="error-feedback" />

        <button id="submitBlogButton" class="button-primary">
          Submit blog
        </button>

        <div v-if="message" class="alert" role="alert">
          {{ message }}
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import textEditor from "../components/TextEditor.vue";
import blogService from "../service/blog.service";

export default {
  name: "NewBlogPost",
  components: {
    Form,
    Field,
    ErrorMessage,
    textEditor,
  },
  data() {
    const schema = yup.object().shape({
      title: yup.string().required("Title is required").min(3),
      image: yup.mixed().required("Image is required"),
    });

    return {
      schema,
      fileName: "Choose image",
      // validate bodytext
      bodyText: "",
      authorUsername: this.$root.currentUser.username,
      message: "",
    };
  },
  methods: {
    handleNewBlog(values) {
      this.message = "";
      blogService.newBlogPost(values, this.authorUsername, this.bodyText).then(
        (data) => {
          this.message = data.message;
          this.$router.push("/?create-blog-success");
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
    previewPic() {
      // access name of file upload
      var picName = document.getElementById("fileInput");
      this.fileName = picName.files.item(0).name;

      document.getElementById("imgContainer").style.background = "#151516";

      // toggle image preview modal
      document.getElementById("imagePreview").src = window.URL.createObjectURL(
        picName.files[0]
      );
      document.getElementById("imagePreview").style.display = "block";
    },
    setBodyText(body) {
      this.bodyText = body;
    },
  },
};
</script>

<style lang="scss" scoped>
form {
  width: 100%;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.form-control {
  background-color: $accentShade;
  overflow: hidden;
}
.error-feedback {
  background-color: $accentShade;
}

.form-in {
  margin-bottom: 10px;
}

#fileInput {
  display: none;
}

#newBlog {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 95%;
}

#imageUploadButton {
  margin-top: 10px;
}

#imgContainer {
  background-color: $lightShade;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  font-size: 1.2rem;
  position: relative;
  height: 160px;
  width: 98%;
  margin-bottom: 15px;
  border-radius: 3px;
}

#imgContainerText {
  color: $darkShade;
  opacity: 0.8;
  text-align: center;
}

#imagePreview {
  border-radius: 3px;
  position: absolute;
  top: 0;
  height: inherit;
  width: inherit;
  object-fit: cover;
  display: none;
}

#submitBlogButton {
  margin-top: 20px;
  margin-bottom: 10px;
}
</style>
