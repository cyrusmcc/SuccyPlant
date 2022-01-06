<template>
  <div class="container">
    <h2>Find your next succyplant</h2>
    <carousel :images="images" :autoplay="true"></carousel>
    <search-bar></search-bar>
  </div>
</template>

<script>
import Carousel from "./Carousel.vue";
import SearchBar from "./SearchBar.vue";

export default {
  name: "SearchCarouselCard",
  components: {
    Carousel,
    SearchBar,
  },
  data() {
    return {
      images: [
        {
          url: require("@/assets/imgs/house.jpg"),
        },
        {
          url: require("@/assets/imgs/house2.jpg"),
        },
        {
          url: require("@/assets/imgs/house3.jpg"),
        },
      ],
    };
  },
};
</script>

<style scoped lang="scss">
h2 {
  position: absolute;
  text-align: center;
  height: fit-content;
  margin: auto;
  margin-bottom: 8rem;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  font-size: 2rem;
  font-weight: bold;
  color: $primaryLight;
  z-index: 1;
  text-shadow: -1px 2px 2px black;
}
.container {
  position: relative;
  margin-bottom: 10px;
}
.searchBar {
  margin: auto;
  position: absolute;
  height: 1.5rem;
  top: 40%;
  left: 0;
  bottom: 0;
  right: 0;
  box-shadow: $shadow;
  border: none;
}
</style>