<template>
  <div class="container" :style="[displayHamburger ? {'height': 'fit-content'} : {'height': '100%'}]">
    <div class="hamburgerIcon">
      <div class="hamburgerLineContainer" v-if="displayHamburger" @click="toggleHamburger">
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
      </div>
      <div
        class="closeHamburgerButton"
        v-if="!displayHamburger"
        @click="toggleHamburger">
        +
      </div>
    </div>
    <div class="hamburgerContent" v-if="!displayHamburger">
      <slot></slot>
    </div>
  </div>
</template>

<script>

export default {
  name: "Hamburger",
  data() {
    return {
      displayHamburger: true,
    };
  },
  methods: {
    toggleHamburger() {
      if (this.displayHamburger) {
        this.displayHamburger = false;
        console.log(this.displayHamburger);
      } else {
        this.displayHamburger = true;
        console.log(this.displayHamburger);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  z-index: 4;
  width: 100%;
  max-height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  position: absolute;
  top: 0;
}

.hamburgerIcon {
  display: relative;
  height: 25px;
  max-width: 100%;
  cursor: pointer;
  display: flex;
  background: $accentOne;
  padding: 5px 0 5px 10px;
}

.hamburgerLine {
  width: 20px;
  margin: 4px;
  height: 3px;
  border-radius: 4px;
  background-color: #f5f5f5;
}

.closeHamburgerButton {
  position: absolute;
  top: -7px;
  left: 10px;
  transform: rotate(45deg);
  font-family: $libreFranklin;
  color: $lightShade;
  user-select: none;
  font-size: 45px;
  cursor: pointer;
}

.hamburgerContent {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 15px;
  row-gap: 10px;
  background: $accentOne;
  height: 400px;
  width: auto;
  max-width: 100%;
}
</style>