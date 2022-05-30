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
    return {
    };
  },
  computed: {
    posts() {
      return this.$store.getters["plants/getPlants"];
    },
    preselectedTags() {
      return this.$store.getters["plants/getPreselectedTags"];
    },
  },
  mounted() {
    // only want to retrieve plants once, if there are preselected tags in store then sortPostsByTags will be called
    // which calls getPosts
    if (!this.preselectedTags || this.preselectedTags.length == 0) this.getPosts();
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
        const arr = await plantService.getPlantsAndImgs(tagString, searchTerm);
        this.$store.commit("plants/setPlants", arr);
      };
      arr();

      if (this.preselectedTags) {
        this.$store.commit("plants/setPreselectedTags", []);
        this.$store.commit("plants/setPlants", []);
      }
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
