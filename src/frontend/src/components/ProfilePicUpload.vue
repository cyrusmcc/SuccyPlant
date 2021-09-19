<template>
  <div class="picUploadContainer">
    <modal>
      <div v-show="getModalType == 'profPicUpload'" id="modalContent">
        <div id="previewImgWrap">
          <img id="previewImgBackground" src="" alt="your img" />
          <img id="previewImgCircle" src="" alt="your img" />
        </div>
      </div>
    </modal>

    <div id="profilePicContainer">
      <profile-pic>
        <img id="userPic" src="../assets/user.svg" alt="profile picture" />
      </profile-pic>
    </div>
    <div class="fileContainer">
      <Form id="fileInputForm" :validation-schema="schema" class="labelButton">
        <Field
          id="fileInput"
          name="file"
          type="file"
          rules="dimensions:120,120"
          @change="previewPic('profPicUpload')"
        />
        <label for="fileInput" v-html="fileName" />
        <ErrorMessage name="file" class="error-feedback" />
      </Form>
    </div>
  </div>
</template>

<script>
import { Form, Field, defineRule, ErrorMessage } from "vee-validate";
import { dimensions } from "@vee-validate/rules";
import { modalState } from "../store/comp.store";
import ProfilePic from "./ProfilePic.vue";
import Modal from "./Modal.vue";

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
      modalType: "none",
      schema,
    };
  },
  methods: {
    previewPic(modalType) {
      // access name of file upload
      var picName = document.getElementById("fileInput");
      this.fileName = picName.files.item(0).name;

      // toggle image preview modal
      document.getElementById("previewImgBackground").src = window.URL.createObjectURL(
        picName.files[0]
      );
      document.getElementById("previewImgCircle").src = window.URL.createObjectURL(
        picName.files[0]
      );
      modalState.modalType = modalType;
    },
  },
  computed: {
    getModalType() {
      return modalState.modalType;
    },
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
  margin: 30px 0 30px 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

#previewImgWrap {
  position: relative;
  border: thin solid $accentOne;
  box-shadow: $shadow;
  width: 300px;
  height: 300px;
}

#previewImgBackground, #previewImgCircle {
  display: block;
  width: 100%;
  height: 100%;
}

#previewImgBackground {
  opacity: 50%;
}

#previewImgCircle {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  border-radius: 10000px;
}

</style>