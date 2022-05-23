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
  align-items: center;
  display: flex;
  flex-direction: column;
  row-gap: 10px;
  width: 100%;
}

@include screen-md {
  .galleryContainer {
    align-items: flex-start;
    column-gap: 10px;
    display: flex;
    flex-direction: row;
    margin-left: 10px;
  }
}
</style>
