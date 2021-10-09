<template>
  <div class="container">
    <router-link to="/new-blog">new blog</router-link>
    <div id="blogFeedHead">Recent articles</div>
    <ol>
      <li v-for="(blog, index) in blogArr" :key="index">
        <div id="blogImgContainer">
          <img
            class="blogImg"
            :src="getImgFromBlogId(index, blog.id)"
            alt="blog post cover image"
          />
        </div>
        <div id="blogTextContainer">
          <router-link :to="'/blog/' + blog.id">{{
            blog.post.title
          }}</router-link>
          <div id="blogPostDate">Posted on {{ blog.postDate }}</div>
        </div>
      </li>
    </ol>
    <button @click="getBlogs" id="loadBlogButton" class="button-primary">
      Load More
    </button>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import blogService from "../service/blog.service";

const imgStore = reactive({
  imgArray: [
    {
      id: 1,
    },
  ],
});

export default {
  computed: {
    blogArr() {
      return this.$store.getters["blogs/getBlogs"];
    },
  },
  // if blogArr store does not contain any posts, perform initial get request
  mounted() {
    if (!this.blogArr) {
      const blogs = async () => {
        const arr = await blogService.getBlogPosts();

        this.$store.state.blogs.blogArr = arr;
      };
      blogs();
    }
  },
  methods: {
    getBlogs() {
      const blogs = async () => {
        const arr = await blogService.getBlogPosts();

        for (let i = 0; i < arr.length; i++) {
          this.getBlogImgById(arr[i].id);
          this.$store.state.blogs.blogArr.push(arr[i]);
        }
      };
      blogs();
    },
    getImgFromBlogId(index, blogId) {
      let temp = imgStore.imgArray[index];
      if (!temp.img) {
        temp.img = null;
        blogService.getBlogImageById(blogId).then((response) => {
          temp.img = URL.createObjectURL(response.data);
        });
      }
      return imgStore.imgArray[index].img;
    },
  },
};
</script>

<style lang="scss" scoped>
li {
  display: flex;
  flex-direction: column;
  align-items: center;
  list-style-type: none;
  margin-bottom: 20px;
  height: fit-content;
  border-radius: 3px;
  width: 85%;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin: 10px auto;
}

#blogFeedHead {
  color: $lightShade;
  font-size: 1.2rem;
  margin-bottom: 25px;
}

#blogImgContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 160px;
  width: 100%;
}

.blogImg {
  height: inherit;
  width: inherit;
  object-fit: cover;
}

#blogTextContainer {
  height: fit-content;
  width: 100%;
  box-shadow: $shadow;
  box-sizing: border-box;
  padding: 10px 0 10px 5px;
}

#blogTextContainer > * {
  margin-bottom: 2px;
}

#blogTitle {
  font-size: 1.2rem;
  color: $lightShade;
  font-style: italic;
}

#blogPostDate {
  font-size: small;
  color: $accentTwo;
}

#blogBody {
  color: $lightShade;
  font-size: 0.8rem;
}

#loadBlogButton {
  width: fit-content;
  margin-bottom: 30px;
  background-color: $darkShade;
  border: thin solid $accentTwo;
}
</style>
>
