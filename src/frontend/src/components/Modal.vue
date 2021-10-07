<template>
  <div class="modalBackground" @mousedown.self.prevent="closeModal">
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
    },
  },
};
</script>

<style lang="scss" scoped>
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
