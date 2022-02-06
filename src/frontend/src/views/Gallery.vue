<template>
  <div class="container">
    <filter-bar></filter-bar>
    <gallery-grid></gallery-grid>
  </div>
</template>

<script>
import FilterBar from '../components/FilterBar.vue';
import galleryGrid from "../components/GalleryGrid";

export default {
  name: "Gallery",
  components: {
    galleryGrid,
    FilterBar,
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
