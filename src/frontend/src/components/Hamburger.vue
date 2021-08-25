<template>
  <div class="container" @click="toggleHamburger">
    <div class="hamburger" :style="[displayHamburger ? {'display': 'flex'} : {'display': 'none'}]">
      <div class="hamburgerLineContainer">
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
        <div class="hamburgerLine"></div>
      </div>
    </div>
    <div class="closeModalButton" :style="[displayHamburger ? {'display': 'none'} : {'display': 'block'}]">x</div>
  </div>
</template>

<script>
//import Modal from "./Modal.vue";

export default {
  name: "Hamburger",
  compontents: {
    //Modal,
  },
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
.hamburger {
  cursor: pointer;
  height: 25px;
  width: auto;
  display: flex;
  justify-content: space-evenly;
  flex-direction: column;
  border-radius: 4px;
  padding: 5px;
  //background-color: $accentShade;
}

.hamburgerLine {
  width: 20px;
  margin: 4px;
  height: 3px;
  border-radius: 4px;
  background-color: #f5f5f5;
}

.closeModalButton {
  display: none;
  font-family: $libreFranklin;
  color: $lightShade;
  user-select: none;
  padding: 0 0 5px 10px;
  font-size: 29px;
  cursor: pointer;
}

</style>