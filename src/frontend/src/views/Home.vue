<template>
  <carousel :images="images" :autoplay="true" :arrows="true"></carousel>
  <div class="parent">
    <square-grid></square-grid>
    <blog-feed />
  </div>
</template>

<script>
import Carousel from "../components/Carousel.vue";
import BlogFeed from "../components/BlogFeed.vue";
import SquareGrid from "../components/SquareGrid.vue";

export default {
  components: {
    BlogFeed,
    SquareGrid,
    Carousel,
  },
  name: "Home",
  data() {
    return {
      content: "",
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
.parent {
  margin: 0 auto;
  height: fit-content;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.parent > * {
  width: 95%;
  margin: 10px 0;
}

@include screen-md() {
  .parent {
    width: 80%;
    max-width: 1500px;
  }
  .parent > * {
    width: 100%;
  }
}
</style>
