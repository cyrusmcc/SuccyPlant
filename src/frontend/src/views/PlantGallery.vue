<template>
  <div class="container">
    <div class="galleryContainer">
      <filter-bar @sortPostsByTags="getPosts"></filter-bar>
      <gallery-grid :galleryPosts="posts"></gallery-grid>
    </div>
  </div>
</template>

<script>
import filterBar from "../components/FilterBar.vue";
import galleryGrid from "../components/GalleryGrid";
import plantService from "../service/plant.service";

export default {
  name: "Gallery",
  components: {
    galleryGrid,
    filterBar,
  },
  data() {
    return {};
  },
  computed: {
    posts() {
      return this.$store.getters["gallery/getPosts"];
    },
  },
  mounted() {
    this.getPosts();
  },
  methods: {
    getPosts(tags, searchTerm) {
      let tagString = "";
      if (tags) {
        for (let i = 0; i < tags.length; i++) {
          tagString += tags[i].label + "=" + tags[i].selected + "&";
        }
      }

      const arr = async () => {
        const arr = await plantService.getPlants(tagString, searchTerm);
        this.$store.commit("gallery/setPosts", arr);
      };
      arr();
    },
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
  row-gap: 10px;
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
