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
          <ErrorMessage name="file" class="error-feedback" />
        </div>

        <div id="imgContainer">
          <span id="imgContainerText">Cover image</span>
          <img id="imagePreview" src="" alt="blog image preview" />
        </div>
        <text-editor />

        <button id="submitBlogButton" class="button-primary">
          Submit blog
        </button>
      </Form>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import textEditor from "../components/TextEditor.vue";

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
      title: yup.string().required("Title is required"),
      bodyText: yup.string().required("Blog body text is required"),
      image: yup.mixed().required("Image is required"),
      //authorUsername: this.$root.currentUser.username,
      // image: yup.mixed().required("Image required"),
    });

    return {
      schema,
      fileName: "Choose image",
    };
  },
  methods: {
    handleNewBlog() {},
    previewPic() {
      // access name of file upload
      var picName = document.getElementById("fileInput");
      this.fileName = picName.files.item(0).name;

      document.getElementById("imgContainer").style.border = "none";

      // toggle image preview modal
      document.getElementById("imagePreview").src = window.URL.createObjectURL(
        picName.files[0]
      );
      document.getElementById("imagePreview").style.display = "block";
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
  margin-top: 20px;
  margin-bottom: 10px;
}

#imgContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  font-size: 1.2rem;
  position: relative;
  height: 160px;
  width: 98%;
  margin-bottom: 20px;
  border-radius: 3px;
  border: medium dashed $accentOne;
}

#imgContainerText {
  color: $accentOne;
  opacity: 0.8;
  text-align: center;
}

#imagePreview {
  border: medium dashed $accentOne;
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
}
</style>
