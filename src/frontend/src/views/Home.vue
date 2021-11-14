<template>
  <div class="container">
    <search-bar></search-bar>
    <square-grid></square-grid>
    <blog-feed />
  </div>
</template>

<script>
import BlogFeed from "../components/BlogFeed.vue";
import SearchBar from "../components/SearchBar.vue";
import SquareGrid from "../components/SquareGrid.vue";

export default {
  components: {
    BlogFeed,
    SearchBar,
    SquareGrid,
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
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
