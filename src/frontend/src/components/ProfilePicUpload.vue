<template>
  <div class="picUploadContainer">
    <div id="profilePicContainer">
      <profile-pic>
        <img id="userPic" alt="profile picture" src="../assets/user.svg" />
      </profile-pic>
    </div>
    <div class="fileContainer">
      <Form
        ref="form"
        id="fileInputForm"
        :validation-schema="schema"
        @submit="handleProfilePictureUpload"
        class="labelButton"
      >
        <Field
          id="fileInput"
          name="file"
          type="file"
          rules="dimensions:120,120"
          @change="previewPic('profPicUpload')"
        />
        <label for="fileInput" v-html="fileName" />
        <ErrorMessage name="file" class="error-feedback" />

        <modal v-show="showModal" @closeModal="toggleModal">
          <div id="modalContent">
            <div id="previewContainer">
              <div id="previewBackgroundWrap">
                <img id="previewBackground" src="" alt="your img" />
              </div>
              <div id="previewImgWrap">
                <img id="previewImgCircle" src="" alt="your img" />
              </div>
            </div>
            <button id="uploadButton" class="button-secondary">
              Update Profile Picture
            </button>
          </div>

          <div v-if="message" class="alert" role="alert">
            {{ message }}
          </div>
        </modal>
      </Form>
    </div>
  </div>
</template>

<script>
import { Form, Field, defineRule, ErrorMessage } from "vee-validate";
import { dimensions } from "@vee-validate/rules";
import ProfilePic from "./ProfilePic.vue";
import Modal from "./Modal.vue";
import UserService from "../service/user.service";

import * as yup from "yup";

defineRule("dimensions", dimensions);

export default {
  components: { Form, Field, ErrorMessage, ProfilePic, Modal },
  name: "FileUploadButton",
  data() {
    const schema = yup.object().shape({
      file: yup.mixed().required("File is required"),
    });

    return {
      fileName: "Choose File",
      schema,
      showModal: false,
      message: "",
    };
  },
  methods: {
    previewPic() {
      // access name of file upload
      var picName = document.getElementById("fileInput");
      this.fileName = picName.files.item(0).name;

      // toggle image preview modal
      document.getElementById("previewBackground").src =
        window.URL.createObjectURL(picName.files[0]);
      document.getElementById("previewImgCircle").src =
        window.URL.createObjectURL(picName.files[0]);

      this.showModal = !this.showModal;
    },
    handleProfilePictureUpload(values) {
      this.message = "";
      this.$store.dispatch("settings/handleProfilePictureUpload", values).then(
        (data) => {
          this.message = data.message;
          this.$router.push("/settings");
        },
        (error) => {
          console.log(error);
          this.message = "File must be an image under 5Mb in size";
        }
      );
    },
    toggleModal() {
      this.showModal = !this.showModal;
    },
  },
  mounted() {
    UserService.getUserProfilePic(this.$root.currentUser.username).then(
      (response) => {
        let imageNode = document.getElementById("userPic");
        let imgUrl = URL.createObjectURL(response.data);
        imageNode.src = imgUrl;
        console.log(this.content);
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
};
</script>

<style scoped lang="scss">
label {
  cursor: pointer;
}

.picUploadContainer {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-evenly;
  width: 100%;
}

.fileContainer {
  width: fit-content;
  max-width: 9rem;
}

#fileInputForm {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

#fileInput {
  display: none;
}

#modalContent {
  margin: 30px 0 10px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

#previewContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 250px;
  width: 250px;
  overflow: hidden;
  border: thin solid $accentOne;
}

#previewImgWrap {
  position: absolute;
  box-shadow: $shadow;
  width: 250px;
  height: 250px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border-radius: 100%;
}

#previewBackground,
#previewImgCircle {
  display: block;
  width: 250px;
  min-height: 250px;
}

#previewBackground {
  opacity: 50%;
}

#previewImgCircle {
  position: absolute;
}

#uploadButton {
  padding: 5px;
  margin-top: 15px;
}
</style>
