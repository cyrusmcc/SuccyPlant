<template>
  <div class="container">
    <router-link to="/new-blog" v-if="$root.currentUser.roles[0] == 'ROLE_USER'"
      >new blog</router-link
    >
    <div id="blogFeedHead">Recent articles</div>
    <li v-for="blog in blogArr" :key="blog.id">
      <div id="blogImgContainer">
        <img id="blogImg" src="../assets/blogplacehold.webp" alt="" />
      </div>
      <div id="blogTextContainer">
        <router-link :to="'/blog/' + blog.id"
          >title here {{ blog.id }}</router-link
        >
        <div id="blogPostDate">Posted on {{ blog.postDate }}</div>
      </div>
    </li>
    <button @click="getBlogs" id="loadBlogButton" class="button-primary">
      Load More
    </button>
  </div>
</template>

<script>
import blogService from "../service/blog.service";

export default {
  data() {
    return {
      initialLoad: false,
    };
  },
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
          this.$store.state.blogs.blogArr.push(arr[i]);
        }
      };
      blogs();
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
  font-size: 1.5rem;
}

#blogImgContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 160px;
  width: 100%;
}

#blogImg {
  height: inherit;
  object-fit: cover;
  width: inherit;
}

#blogTextContainer {
  width: 100%;
  padding: 10px;
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
  margin-bottom: 15px;
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
