<template>
  <search-carousel-card></search-carousel-card>
  <div class="parent">
    <square-grid></square-grid>
    <blog-feed />
  </div>
</template>

<script>
import SearchCarouselCard from "../components/SearchCarouselCard.vue";
import BlogFeed from "../components/BlogFeed.vue";
import SquareGrid from "../components/SquareGrid.vue";

export default {
  components: {
    BlogFeed,
    SquareGrid,
    SearchCarouselCard,
  },
  name: "Home",
  data() {
    return {
      content: "",
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
