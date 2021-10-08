<template>
  <div id="editor">
    <div id="editorToolBar">
      <img
        id="previewIcon"
        src="../assets/preview.svg"
        alt="preview icon"
        @click="togglePreview"
      />
    </div>
    <div id="bodyContainer">
      <textarea id="body" :value="bodyText" @input="update"></textarea>
      <div
        id="previewBodyText"
        v-html="compiledMarkdown"
        v-if="displayPreview"
      ></div>
    </div>
  </div>
</template>

<script>
import marked from "marked";
import DOMPurify from "dompurify";

export default {
  data() {
    return {
      bodyText: "",
      displayPreview: false,
    };
  },
  computed: {
    compiledMarkdown() {
      return DOMPurify.sanitize(marked(this.bodyText));
    },
  },
  methods: {
    togglePreview() {
      this.displayPreview = !this.displayPreview;

      if (this.displayPreview) {
        document.getElementById("body").style.display = "none";
      } else {
        document.getElementById("body").style.display = "block";
      }
    },
    update: function (e) {
      this.bodyText = e.target.value;
      this.$emit("bodyText", DOMPurify.sanitize(marked(this.bodyText)));
    },
  },
};
</script>

<style scoped lang="scss">
#editor {
  position: relative;
  display: inline-block;
  width: 100%;
  height: fit-content;
  vertical-align: top;
  box-sizing: border-box;
}

#editor > * {
  background-color: $lightShade;
}

#editorToolBar {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 2rem;
  border-radius: 4px 4px 0 0;
  width: 100%;
  border-bottom: thick solid $darkShade;
  background-color: $accentShade;
}

#bodyContainer {
  background-color: $accentShade;
  position: relative;
  height: 15rem;
}

#body {
  background-color: $accentShade;
  color: $lightShade;
  width: 100%;
  height: 100%;
  position: absolute;
  resize: none;
  border: none;
  outline: none;
  border-radius: 4px;
  padding: 1px;
}

#previewIcon {
  height: 0.8rem;
  margin-left: 10px;
}

#previewIcon:hover {
  opacity: 0.8;
}

#previewBodyText {
  width: 100%;
  height: 100%;
  position: absolute;
  background-color: $darkShade;
  color: $lightShade;
  border: none;
  overflow: scroll;
}
</style>
