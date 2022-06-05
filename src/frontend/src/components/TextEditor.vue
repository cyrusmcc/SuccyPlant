<template>
  <div id="editor">
    <div id="editorToolBar">
      <img id="previewIcon" src="../assets/imgs/previewLight.svg" alt="preview icon" @click="togglePreview" />
    </div>
    <div id="bodyContainer">
      <textarea id="body" :value="bodyText" @input="update"></textarea>
      <div id="previewBodyText" class="scrollContainer" v-html="compiledMarkdown" v-if="displayPreview"></div>
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
      return DOMPurify.sanitize(marked.parseInline(this.bodyText));
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

    // When user types in the text area, if text area is full, increase height
    // of the text area and text area container elements.
    // TODO: Need to decrease height of text area/container when user removes
    adjustTextArea() {
      let textArea = document.getElementById("body");
      let textAreaContainer = document.getElementById("bodyContainer");

      if (textArea.scrollHeight > textArea.clientHeight) {
        textArea.style.height = "1px";
        textArea.style.height = (25 + textArea.scrollHeight) + "px";

        textAreaContainer.style.height = "1px";
        textAreaContainer.style.height = (25 + textAreaContainer.scrollHeight) + "px";
      }
    },
    update: function (e) {
      this.adjustTextArea();
      this.bodyText = e.target.value;
      this.$emit("bodyText", DOMPurify.sanitize(marked(this.bodyText)));
    },
  },
};
</script>

<style scoped lang="scss">
#editor {
  box-sizing: border-box;
  display: inline-block;
  height: fit-content;
  position: relative;
  vertical-align: top;
  width: 100%;
}

#editor>* {
  background-color: $primaryLight;
}

#editorToolBar {
  align-items: center;
  background-color: $primaryDark;
  border-radius: 4px 4px 0 0;
  display: flex;
  flex-direction: row;
  height: 2rem;
  width: 100%;
}

#bodyContainer {
  background-color: $accentDark;
  border-radius: 0 0 4px 4px;
  box-sizing: border-box;
  height: 70px;
  overflow: hidden;
  position: relative;
}

#body {
  background-color: $accentDark;
  border-radius: 0 0 4px 4px;
  border: none;
  color: $primaryLight;
  height: 98%;
  outline: none;
  padding: 1px;
  position: absolute;
  resize: none;
  width: 100%;
}

#previewIcon {
  height: 0.8rem;
  margin-left: 10px;
}

#previewIcon:hover {
  opacity: 0.8;
}

#previewBodyText {
  background-color: $primaryLight;
  border-radius: 0 0 4px 4px;
  border: none;
  color: $primaryDark;
  height: 100%;
  line-break: anywhere;
  overflow-x: hidden;
  overflow: scroll;
  position: absolute;
  width: 100%;
}

#previewBodyText>p {
  margin: 0;
}
</style>
