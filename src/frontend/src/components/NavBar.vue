<template>
  <div class="navBarContainer">
    <div
      class="modalBackground"
      v-if="!displayHamburger"
      @click.self="toggleHamburger()"
    ></div>
    <div class="navBar">
      <div
        class="hamburgerLineContainer"
        v-if="displayHamburger"
        @click="toggleHamburger"
      >
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
      </div>
      <router-link to="/" id="navBarText">icdtea</router-link>
      <div
        class="closeHamburgerButton"
        v-if="!displayHamburger"
        @click="toggleHamburger"
      >
        +
      </div>
    </div>
    <div
      class="hamburgerContent"
      v-if="!displayHamburger"
      @click="toggleHamburger"
    >
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
      this.displayHamburger = !this.displayHamburger;
    },
  },
};
</script>

<style lang="scss" scoped>
.navBarContainer {
  z-index: 4;
  width: 100%;
  height: fit-content;
  position: sticky;
  top: 0;
  max-height: 100%;
  background: $darkShade;
}

.navBar {
  position: relative;
  z-index: 5;
  height: 25px;
  max-width: 100%;
  font-family: $lobster;
  color: $lightShade;
  font-size: 1.3rem;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 10px 0 10px 10px;
  background: $darkShade;
}

.hamburgerLineContainer {
  position: absolute;
  top: 10px;
  left: 10px;
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
  color: $lightShade;
  user-select: none;
  font-size: 45px;
  cursor: pointer;
}

.hamburgerContent {
  position: relative;
  z-index: 5;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 15px;
  row-gap: 10px;
  background: $darkShade;
  height: fit-content;
  max-width: 100%;
}
</style>
