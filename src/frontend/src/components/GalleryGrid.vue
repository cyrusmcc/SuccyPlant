<template>
  <div class="gridContainer">
    <ol>
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
        <div id="galPostTextContainer">
          <div id="galPostTitle">
            <router-link :to="'/g/' + galPost.id">{{
              galPost.post.title
            }}</router-link>
          </div>
        </div>
      </li>
    </ol>
  </div>
</template>

<script>
import galleryService from "../service/gallery.service";

export default {
  name: "GalleryGrid",
  data() {
    return {};
  },
  computed: {
    galleryArr() {
      return this.$store.getters["gallery/getGalPosts"];
    },
  },
  mounted() {
    if (!this.galleryArr) {
      const galPosts = async () => {
        const arr = await galleryService.getGalleryPosts();

        this.$store.state.gallery.galleryArr = arr;
      };
      galPosts();
    }
  },
  getGalPosts() {
    const galPosts = async () => {
      const arr = await galleryService.getGalleryPosts();
      for (let i = 0; i < arr.length; i++) {
        this.$store.state.gallery.galleryArr.push(arr[i]);
      }
    };
    galPosts();
  },
};
</script>

<style scoped lang="scss">
.gridContainer {
  margin-top: 50px;
}

ol {
  width: 100%;
  margin-top: 20px;
  padding: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
li {
  display: flex;
  flex-direction: row;
  align-items: center;
  list-style-type: none;
  margin-bottom: 10px;
  height: 150px;
  width: 95%;
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

#galPostTextContainer {
  height: 100%;
  width: 100%;
  align-items: center;
  justify-content: flex-start;
}

#galPostTitle {
  text-align: center;
  height: 100%;
  width: 100%;
  box-sizing: border-box;
  padding: 10px 0 10px 5px;
}



#galPostTitle {
  font-size: 1.2rem;
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