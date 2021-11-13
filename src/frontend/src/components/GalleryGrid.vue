<template>
  <div class="gridContainer">
    <tag-sort @sort-posts-by-tag="getSortedGalPosts"></tag-sort>
    <ol id="galPostList">
      <li v-for="(galPost, index) in galleryArr" :key="index">
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
              <div class="galPostTag">{{ tag.value }}</div>
            </li>
          </ol>
        </div>
      </li>
    </ol>
  </div>
</template>

<script>
import galleryService from "../service/gallery.service";
import tagSort from "../components/TagSort.vue";

export default {
  name: "GalleryGrid",
  components: {
    tagSort,
  },
  data() {
    return {
      sortedArr: [],
    };
  },
  computed: {
    galleryArr() {
      return this.$store.getters["gallery/getGalPosts"];
    },
  },
  mounted() {
    // load query if user directly visits queried url
    if (window.location.search) {
      let params = new URLSearchParams(this.$router.currentRoute.value.query);
      this.getSortedGalPosts(params);
    }
    // else populate with default posts
    else if (!this.galleryArr) {
      const galPosts = async () => {
        const arr = await galleryService.getPosts();

        this.$store.state.gallery.galleryArr = arr;
      };
      galPosts();
    }
  },
  getGalPosts() {
    const galPosts = async () => {
      const arr = await galleryService.getPosts();
      for (let i = 0; i < arr.length; i++) {
        this.$store.state.gallery.galleryArr.push(arr[i]);
      }
    };
    galPosts();
  },
  methods: {
    getSortedGalPosts(params) {
      const sortedGalPosts = async () => {
        const arr = await galleryService.getSortedPosts(params);
        this.$store.state.gallery.galleryArr = arr;
      };
      sortedGalPosts();
    },
  },
};
</script>

<style scoped lang="scss">
.gridContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

#galPostList {
  width: 100%;
  margin-top: 20px;
  padding: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#galPostList > li {
  display: flex;
  flex-direction: row;
  align-items: center;
  list-style-type: none;
  margin-bottom: 10px;
  height: 150px;
  width: 95%;
  box-shadow: $shadowLight;
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
  height: 150px;
  width: 150px;
}

.galPostImg {
  height: inherit;
  width: inherit;
  object-fit: cover;
}

#galPostInfoContainer {
  height: 90%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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
  background-color: $accentTwo;
  padding: 3px;
  font-size: 0.8rem;
  border-radius: 8px;
  margin-top: 10px;
}

#galPostTitle {
  text-align: center;
  font-style: italic;
  color: $primaryLight;
  font-style: italic;
}

#galPostDate {
  display: flex;
  flex-direction: row;
  font-size: small;
  color: $accentOne;
}
</style>
