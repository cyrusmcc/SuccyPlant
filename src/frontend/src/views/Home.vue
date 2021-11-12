<template>
  <div class="container">
    <search-bar></search-bar>
    <blog-feed />
  </div>
</template>

<script>
import BlogFeed from "../components/BlogFeed.vue";
import SearchBar from "../components/SearchBar.vue";

export default {
  components: {
    BlogFeed,
    SearchBar,
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
