<template>
  <div class="container">
    <div class="galleryContainer">
      <filter-bar></filter-bar>
      <gallery-grid></gallery-grid>
    </div>
  </div>
</template>

<script>
import FilterBar from "../components/FilterBar.vue";
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
  justify-content: center;
}

.galleryContainer {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

@include screen-md {
  .galleryContainer {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(4, 1fr);
    grid-column-gap: 15px;
    grid-row-gap: 0px;
    width: 100%;
    align-items: start;
    margin-left: 10px;
  }
}
</style>
