<template>
  <div id="modalBackground" @mousedown.self.prevent="closeModal">
    <div class="card" id="modalContent">
      <div id="modalClose" @click.prevent="closeModal">+</div>
      <slot></slot>
    </div>
  </div>
</template>

<script>
//import { modalState } from "../store/comp.store";

export default {
  name: "Modal",
  methods: {
    closeModal() {
      this.$emit("closeModal");
    }
  },
};
</script>

<style lang="scss" scoped>
#modalBackground {
  z-index: 4;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
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