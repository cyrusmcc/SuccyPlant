<template>
  <div class="container">
    <search-bar></search-bar>
    <gallery-grid></gallery-grid>
  </div>
</template>

<script>
import galleryGrid from "../components/GalleryGrid";
import SearchBar from "../components/SearchBar.vue";

export default {
  name: "Gallery",
  components: {
    galleryGrid,
    SearchBar,
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
