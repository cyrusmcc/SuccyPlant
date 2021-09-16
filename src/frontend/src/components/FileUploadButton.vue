<template>
  <div class="fileContainer">
    <Form id="fileInputForm">
      <Field id="fileInput" name="field" type="file" rules="dimensions:120,120" @change="getFileName"/>
      <label for="fileInput" v-html="fileName"> </label>
    </Form>
  </div>
</template>

<script>
import { Form, Field, defineRule } from "vee-validate";
import { dimensions } from "@vee-validate/rules";

defineRule('dimensions', dimensions);

export default {
components: { Form, Field },
  name: "FileUploadButton",
  data() {
    //const schema = yup.object().shape({

    //});

    return {
      fileName: "Choose File",
    };
  },
  methods: {
    getFileName() {
      var name = document.getElementById('fileInput');
      this.fileName =  name.files.item(0).name;
    },
  }
}
</script>

<style scoped lang="scss">
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
</style>