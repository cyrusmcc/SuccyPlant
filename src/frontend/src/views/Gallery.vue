<template>
  <div class="container">
    <search-bar></search-bar>
    <tag-sort></tag-sort>
    <gallery-grid></gallery-grid>
  </div>
</template>

<script>
import galleryGrid from "../components/GalleryGrid";
import SearchBar from "../components/SearchBar.vue";
import TagSort from "../components/TagSort.vue"

export default {
  name: "Gallery",
  components: {
    galleryGrid,
    SearchBar,
    TagSort
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