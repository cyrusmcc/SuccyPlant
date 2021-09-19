<template>
  <div id="modalBackground" v-show="getModalType != 'none'" @mousedown.self="toggleModal">
    <div class="card" id="modalContent">
      <div id="modalClose" @click="toggleModal">+</div>
      <slot></slot>
    </div>
  </div>
</template>

<script>
import { modalState } from "../store/comp.store";

export default {
  name: "Modal",
  computed: {
    getModalType() {
      return modalState.modalType;
    }
  },
  methods: {
    toggleModal() {
      document.getElementById("modalBackground").style.display = "none";
      modalState.modalType = "none"
    },
  },
};
</script>

<style lang="scss" scoped>
#modalBackground {
  z-index: 4;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  position: absolute;
  top: 0;
}
#modalContent {
  position: relative;
  margin: 150px auto;
  z-index: inherit;
  color: $lightShade;
  background-color: $accentShade;
  border-radius: 4px;
  text-align: center;
  position: relative;
  padding: 40px 10px 10px 10px;
}
#modalClose {
  position: absolute;
  z-index: inherit;
  color: $lightShade;
  user-select: none;
  top: 0;
  left: 10px;
  font-size: 2em;
  transform: rotate(45deg);
  cursor: pointer;
}
</style>