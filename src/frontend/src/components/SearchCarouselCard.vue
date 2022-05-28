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
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  margin-bottom: 8rem;
  width: 95%;
  font-family: $inter;
  font-size: 2rem;
  font-weight: bold;
  color: $primaryLight;
  z-index: 1;
  text-shadow: -1px 2px 2px black;
}

.container {
  position: relative;
  height: 17rem;
  margin-bottom: 10px;
}

.searchBar {
  position: absolute;
  top: 40%;
  left: 0;
  bottom: 0;
  right: 0;
  width: 95%;
  margin: auto;
  box-shadow: $shadow;
  border: none;
}
</style>
