<template>
  <div class="container">
    <li v-for="blog in blogArr" :key="blog.id">
      <div id="blogImgContainer">
        <img id="blogImg" src="../assets/blogplacehold.jpg" alt="" />
      </div>
      <div id="blogTextContainer">
        <router-link :to="'/blog/' + blog.id">title here {{ blog.id }}</router-link>
        <div id="blogPostDate">Posted on {{ blog.postDate }}</div>
        <div id="blogBody">
          this is the blog body summary {{ blog.bodyText }}
        </div>
      </div>
    </li>
    <button @click="getBlogs" id="loadBlogButton" class="button-primary">Load More</button>
  </div>
</template>

<script>
import blogService from "../service/blog.service";

export default {
  data() {
    return {
      blogArr: [],
    };
  },

  methods: {
    getBlogs() {
      const blogs = async () => {
        const arr = await blogService.getBlogPosts();

        for (let i = 0; i < arr.length; i++) {
          this.blogArr.push(arr[i]);
        }
      };
      blogs();
    },
    getBlogById(id) {
      console.log(id);
    }
  },
  mounted() {
      const initialBlogs = async () => {
        const arr = await blogService.getBlogPosts();

        for (let i = 0; i < arr.length; i++) {
          this.blogArr.push(arr[i]);
        }
      };
      initialBlogs();
  },
};
</script>

<style lang="scss" scoped>
li {
  display: flex;
  flex-direction: column;
  align-items: center;
  list-style-type: none;
  margin-bottom: 50px;
  height: fit-content;
  border-radius: 3px;
  font-style: $rubik;
  width: 85%;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin: 10px auto;
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
  border: thin solid $accentOne;
}
</style>>