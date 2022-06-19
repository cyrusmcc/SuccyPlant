<template>
  <div class="textEditor">
    <div id="editorToolBar">
      <div class="previewTool">
        <span class="topToolTip">Preview comment</span>
        <img id="previewIcon" src="../assets/imgs/previewLight.svg" alt="preview icon" @click="togglePreview" />
      </div>
    </div>
    <div id="bodyContainer">
      <textarea id="textEditorBody" :value="bodyText" @input="update" placeholder="Enter text here"></textarea>
      <div id="previewBodyText" class="" v-html="textType == 'md' ? compiledMarkdown : bodyText" v-if="displayPreview">
      </div>
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
  // Text types: "plain", "html", "md"
  props: ["textType"],
  computed: {
    compiledMarkdown() {
      return DOMPurify.sanitize(marked(this.bodyText));
    },
  },
  methods: {
    togglePreview() {
      this.displayPreview = !this.displayPreview;

      if (this.displayPreview) {
        document.getElementById("textEditorBody").style.display = "none";
      } else {
        document.getElementById("textEditorBody").style.display = "block";
      }
    },

    // When user types in the text area, if text area is full, increase height
    // of the text area and text area container elements.
    // TODO: Need to decrease height of text area/container when user removes
    adjustTextArea() {
      let textArea = document.getElementById("textEditorBody");
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
      if (this.textType == "md") {
        this.$emit("bodyText", this.compiledMarkdown);
      } else {
        this.$emit("bodyText", this.bodyText);
      }
    },
  },
};
</script>

<style scoped lang="scss">
.textEditor {
  box-sizing: border-box;
  display: inline-block;
  height: fit-content;
  position: relative;
  vertical-align: top;
  width: 100%;
}

.textEditor>* {
  background-color: $primaryLight;
}

#editorToolBar {
  align-items: center;
  background-color: $primaryLight;
  border: 1px solid $primaryDark;
  border-radius: 4px 4px 0 0;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  height: 2rem;
  width: 100%;
}

#bodyContainer {
  background-color: $primaryLight;
  border-radius: 0 0 4px 4px;
  border: 1px solid $primaryDark;
  border-top: none;
  box-sizing: border-box;
  height: 70px;
  overflow: hidden;
  position: relative;
}

#textEditorBody {
  background-color: $primaryLight;
  border-radius: 0 0 4px 4px;
  border: none;
  color: $primaryDark;
  height: 98%;
  outline: none;
  padding: 1px;
  position: absolute;
  resize: none;
  width: 100%;
}

.previewTool {
  position: relative;
}

.previewTool .topToolTip {
  visibility: hidden;
}

.previewTool:hover .topToolTip {
  visibility: visible;
}

#previewIcon {
  cursor: pointer;
  filter: invert(100%) sepia(93%) saturate(1352%) hue-rotate(87deg) brightness(119%) contrast(119%);
  height: 0.8rem;
  margin-left: 10px;
}

#previewIcon:hover {
  opacity: 0.8;
}

.topToolTip {
  background: $primaryDark;
  color: $primaryLight;
  font-size: 0.8rem;
  position: absolute;
  top: -20px;
  white-space: nowrap;
  padding: 3px;
  border-radius: 4px;
}

#previewBodyText {
  background-color: $primaryLight;
  border-radius: 0 0 4px 4px;
  border: none;
  color: $primaryDark;
  font-family: $raleway;
  font-size: 0.9rem;
  height: 100%;
  line-break: anywhere;
  overflow: hidden;
  position: absolute;
  width: 100%;
}

#previewBodyText>p {
  margin: 0;
}
</style>
