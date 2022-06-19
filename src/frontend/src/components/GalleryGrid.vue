<template>
  <div class="gridContainer">
    <ol class="galPostList">
      <li v-for="(galPost, index) in galleryPosts" :key="index">
        <div id="galleryImgContainer">
          <img :src="getImg(galPost.thumbnailUrl)" class="galPostImg" :alt="'image of ' + galPost.scientificName" />
        </div>
        <div id="galPostInfoContainer">
          <div id="galPostTitle">
            <router-link :to="'/plant/' + galPost.id">{{
                galPost.scientificName
            }}</router-link>
          </div>
          <div class="tagRowContainer" v-for="(row, index) in getTagRows(galPost.post.tags)" :key="index">
            <ol id="galPostTagList">
              <li v-for="(tag, index) in row" :key="index" :style="{ order: getOrder(tag.category) }">
                <div class="galPostTag" :style="{ background: getColor(tag.category) }">
                  {{ tag.value }}
                </div>
              </li>
            </ol>
          </div>
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
      return this.$store.getters["plants/getTags"];
    },
  },
  methods: {
    getColor(label) {
      switch (label) {
        case "Genus":
          return "#86c2b6";
        case "Type":
          return "#95d991";
        case "Size":
          return "#bdb2ff";
        case "Difficulty":
          return "#ffb2b2";
        case "Light":
          return "#f5c881";
        case "Water":
          return "#93c3f5";
        case "Pet Safe":
          return "#cbb7ac";
        default:
          return "#86c2b6";
      }
    },
    getOrder(Label) {
      switch (Label) {
        case "Genus":
          return 1;
        case "Type":
          return 2;
        case "Size":
          return 3;
        case "Difficulty":
          return 4;
        case "Light":
          return 5;
        case "Water":
          return 6;
        case "Pet Safe":
          return 7;
        default:
          return 1;
      }
    },
    getTagRows(tags) {
      let tagRows = {
        tagRow1: [],
        tagRow2: [],
        tagRow3: [],
        tagRow4: [],
      };

      for (let i = 0; i < tags.length; i++) {
        switch (tags[i].category) {
          case "Genus":
            tagRows.tagRow1.push(tags[i]);
            break;
          case "Type":
            tagRows.tagRow2.push(tags[i]);
            break;
          case "Size":
            tagRows.tagRow2.push(tags[i]);
            break;
          case "Difficulty":
            tagRows.tagRow3.push(tags[i]);
            break;
          case "Light":
            tagRows.tagRow3.push(tags[i]);
            break;
          case "Water":
            tagRows.tagRow4.push(tags[i]);
            break;
          case "Pet Safe":
            tagRows.tagRow4.push(tags[i]);
            break;
          default:
            break;
        }
      }
      return tagRows;
    },
    getImg(thumbnailUrl) {
      return URL.createObjectURL(thumbnailUrl);
    },
  }
}
</script>

<style scoped lang="scss">
.gridContainer {
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  grid-area: 1 / 2 / 5 / 6;
  width: fit-content;
}

a {
  color: $primaryDark;
}

.galPostList {
  align-items: center;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 5px;
  justify-content: center;
  margin-top: 0;
  padding: 0;
  width: 100%;
}

.galPostList>li {
  align-items: center;
  border-radius: 4px;
  border: 1px solid $outline;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  height: 20rem;
  list-style-type: none;
  overflow: hidden;
  width: 45%;
}

.container {
  align-items: center;
  display: flex;
  flex-direction: column;
  margin: 10px auto;
  width: 100%;
}

#galleryImgContainer {
  align-items: center;
  display: flex;
  height: 170px;
  justify-content: center;
  overflow: hidden;
  width: 100%;
}

.galPostImg {
  height: 100%;
  object-fit: cover;
  width: 100%;
}

#galPostInfoContainer {
  align-items: center;
  display: flex;
  flex-direction: column;
  height: fit-content;
  justify-content: flex-start;
  margin-top: 10px;
  padding-bottom: 5px;
  row-gap: 3px;
  width: 95%;
}

.tagRowContainer {
  margin-top: 5px;
}

#galPostTagList {
  //flex-wrap: wrap;
  align-content: flex-start;
  column-gap: 5px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 0;
  width: 100%;
}

#galPostTagList>li {
  list-style-type: none;
}

.galPostTag {
  background-color: $accentTwo;
  border-radius: 4px;
  cursor: default;
  font-size: 0.8rem;
  height: fit-content;
  padding: 3px;
  width: fit-content;
}

#galPostTitle {
  @include flexCenter();
  color: $primaryLight;
  font-style: italic;
  font-style: italic;
  height: 40px;
  text-align: center;
  width: 100%;
}

#galPostTitle>* {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  width: 100%;
}

#galPostDate {
  color: $accentOne;
  display: flex;
  flex-direction: row;
  font-size: small;
}

@media screen and (min-width: 750px) {

  .galPostList {
    justify-content: flex-start;
    min-width: 30rem;
  }

  .galPostList>li {
    width: 200px;
  }
}
</style>
