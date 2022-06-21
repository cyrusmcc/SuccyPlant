<template>
  <div class="container">
    <div class="searchCardContainer">
      <search-carousel-card class="searchCard"></search-carousel-card>
    </div>
    <div class="parent">
      <square-grid></square-grid>
      <blog-feed />
    </div>
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
.container {
  width: 100%;
  max-width: 1300px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.parent {
  margin: 0 auto;
  height: fit-content;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.parent>* {
  width: 95%;
  margin: 10px 0;
}

.searchCardContainer {
  width: 100%;
}

.searchCard {
  margin-top: -5px;
  border-radius: 4px;
  overflow: hidden;
}

@include screen-md() {
  .parent {
    width: 80%;
    max-width: 1500px;
  }

  .parent>* {
    width: 100%;
  }

  @include screen-lg() {
    .searchCardContainer {
      max-width: 95%;
      margin-top: 10px;
    }
  }
}
</style>
