<template>
  <div class="gridContainer">
    <ol class="galPostList">
      <li v-for="(galPost, index) in galleryPosts" :key="index">
        <div id="galleryImgContainer">
          <img src="../assets/blogplacehold.webp" class="galPostImg" alt="" />
          <!--
          <img
            class="blogImg"
            :src="getImgFromBlogId(index, blog.id)"
            alt="blog post cover image"
            onload="this.style.display='block'"
          />
          -->
        </div>
        <div id="galPostInfoContainer">
          <div id="galPostTitle">
            <router-link :to="'/g/' + galPost.id">{{
              galPost.post.title
            }}</router-link>
          </div>
          <ol id="galPostTagList">
            <li v-for="(tag, index) in galPost.post.tags" :key="index">
              <div
                class="galPostTag"
                :style="{ background: getColor(tag.category) }"
              >
                {{ tag.value }}
              </div>
            </li>
          </ol>
        </div>
      </li>
    </ol>
  </div>
</template>

<script>
export default {
  name: "GalleryGrid",
  components: {},
  props: ["galleryPosts"],
  data() {
    return {};
  },
  mounted() {
    this.sortGalleryPostTags;
  },
  computed: {
    sortGalleryPostTags() {
      return this.$store.getters["gallery/getTags"];
    },
  },
  methods: {
    getColor(label) {
      switch (label) {
        case "Genus":
          return "#86c2b6";
        case "Type":
          return "#8caed3";
        case "Size":
          return "#bdb2ff";
        case "Difficulty":
          return "#ffb2b2";
        case "Light":
          return "#f5c881";
        case "Pet Safe":
          return "#cbb7ac";
        default:
          return "#86c2b6";
      }
    },
  },
};
</script>

<style scoped lang="scss">
.gridContainer {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: fit-content;
  grid-area: 1 / 2 / 5 / 4;
}
.galPostList {
  box-sizing: border-box;
  width: 100%;
  margin-top: 0;
  padding: 0;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  gap: 10px;
}
.galPostList > li {
  display: flex;
  flex-direction: column;
  align-items: center;
  list-style-type: none;
  height: fit-content;
  width: 95%;
  box-sizing: border-box;
  padding: 10px;
  border: 1px solid $outline;
  border-radius: 4px;
  overflow: hidden;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin: 10px auto;
}

#galleryImgContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  height: 170px;
  width: 170px;
}

.galPostImg {
  height: 170px;
  width: fit-content;
  object-fit: cover;
}

#galPostInfoContainer {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  row-gap: 10px;
  margin-top: 10px;
  height: fit-content;
  width: 95%;
  padding-bottom: 5px;
}

#galPostTagList {
  width: 95%;
  padding: 0;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: flex-start;
  column-gap: 5px;
}

#galPostTagList > li {
  list-style-type: none;
}

.galPostTag {
  height: fit-content;
  width: fit-content;
  cursor: default;
  background-color: $accentTwo;
  padding: 3px;
  font-size: 0.8rem;
  border-radius: 8px;
  margin-top: 10px;
}

#galPostTitle {
  @include flexCenter();
  text-align: center;
  font-style: italic;
  color: $primaryLight;
  font-style: italic;
  height: 40px;
  width: 100%;
}

#galPostTitle > * {
  display: block;
  width: 100%;
  white-space: normal;
  text-overflow: ellipsis;
  overflow: hidden;
}

#galPostDate {
  display: flex;
  flex-direction: row;
  font-size: small;
  color: $accentOne;
}

@include screen-md() {
  .galPostList {
    justify-content: flex-start;
  }
  .galPostList > li {
    width: 200px;
  }
}
</style>
