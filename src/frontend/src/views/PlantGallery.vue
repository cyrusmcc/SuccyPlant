<template>
  <div class="container">
    <div class="galleryContainer">
      <filter-bar @sortPostsByTags="getPosts"></filter-bar>
      <div class="gallery">
        <gallery-grid :galleryPosts="posts"></gallery-grid>
        <button class="loadPostsButton button-primaryDark-noBorder" v-if="posts.length > 0"
          @click="loadMore(tagString, searchTerm, false)">Load
          more</button>
      </div>
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
      tagString: "",
      searchTerm: "",
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

    if (!this.posts) {
      const plants = async () => {
        const arr = await plantService.getPlants();

        this.$store.state.plants.posts = arr;
      };
      plants();
    }
    else {
      // only want to retrieve plants once, if there are preselected tags in store then sortPostsByTags will be called
      // which calls getPosts
      if (!this.preselectedTags || this.preselectedTags.length == 0)
        this.getPosts(null, null, true);
    }

  },
  methods: {
    getPosts(tags, searchTerm, initialLoad) {
      let tagString = "";
      if (tags) {
        for (let i = 0; i < tags.length; i++) {
          tagString += tags[i].label + "=" + tags[i].selected + "&";
        }
        this.tagString = tagString;
      }

      this.searchTerm = searchTerm;

      const arr = async () => {
        const arr = await plantService.getPlantsAndImgs(
          tagString,
          searchTerm,
          initialLoad,
          "thumbnail"
        );
        this.$store.commit("plants/setPlants", arr);
      };
      arr();

      if (this.preselectedTags) {
        this.$store.commit("plants/setPreselectedTags", []);
        this.$store.commit("plants/setPlants", []);
        this.tagString = "";
        this.searchTerm = "";
      }
    },
    loadMore() {
      const arr = async () => {
        const arr = await plantService.getPlantsAndImgs(
          this.tagString,
          this.searchTerm,
          false,
          "thumbnail"
        );
        this.$store.commit("plants/addPlants", arr);
      };
      arr();
    }
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

.gallery {
  display: flex;
  flex-direction: column;
  max-width: 1040px;
  justify-content: center;
  width: 100%;
}

.loadPostsButton {
  align-self: center;
  margin: 10px 0;
}

@media screen and (min-width: 750px) {
  .galleryContainer {
    align-items: flex-start;
    column-gap: 10px;
    display: flex;
    flex-direction: row;
    margin-left: 10px;
  }

}
</style>
