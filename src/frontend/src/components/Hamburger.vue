<template>
  <div class="hamburger" :style="[displayHamburger ? {'height': 'fit-content'} : {'height': '100%'}]" @click.self="toggleHamburger">
    <div class="hamburgerIcon">
      <div class="hamburgerLineContainer" v-if="displayHamburger" @click="toggleHamburger">
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
      </div>
      <router-link to="/" id="navBarText">icdtea</router-link>
      <div
        class="closeHamburgerButton"
        v-if="!displayHamburger"
        @click="toggleHamburger">
        +
      </div>
    </div>
    <div class="hamburgerContent" v-if="!displayHamburger" @click="toggleHamburger">
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
      } else {
        this.displayHamburger = true;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.hamburger {
  position: fixed;
  z-index: 4;
  width: 100%;
  max-height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
}

.hamburgerIcon {
  display: relative;
  height: 25px;
  max-width: 100%;
  font-family: $lobster;
  color: $lightShade;
  font-size: 1.3rem;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-content: center;
  background: $accentOne;
  padding: 5px 0 5px 10px;
}

.hamburgerLineContainer {
  position: absolute;
  top: 6px;
  left: 6px;
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
  height: fit-content;
  width: auto;
  max-width: 100%;
}

</style>