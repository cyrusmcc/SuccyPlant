<template>
  <div id="modalBackground" v-if="!displayModal" @click="toggleModal()">
    <div class="card" id="modalContent">
      <div id="modalClose" @click="toggleModal()">+</div>
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "Modal",
  data() {
    return {
      displayModal: false,
    };
  },
  methods: {
    toggleModal() {
      if (this.displayModal) {
        this.displayModal = false;
      } else {
        this.displayModal = true;
      }
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
  z-index: inherit;
  color: $lightShade;
  background-color: $accentShade;
  border-radius: 4px;
  text-align: center;
  position: relative;
}
#modalClose {
  position: absolute;
  z-index: inherit;
  color: $lightShade;
  user-select: none;
  bottom: -10px;
  left: 10px;
  font-size: 2em;
  transform: rotate(45deg);
  cursor: pointer;
}
</style>