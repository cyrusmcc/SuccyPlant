<template>
  <div class="container">
    <!--
    <div class="blogFeedHead">Recent articles</div>
    -->
    <ol>
      <li v-for="(blog, index) in blogArr" :key="index">
        <div class="blogPostDate">
          <span class="postedOn">Posted on&nbsp;</span>
          <formatted-date :date="blog.postDate" :format="'full'" class="formattedDate" />
        </div>
        <router-link class="blogTitle" :to="'/blog/' + blog.id">{{
            blog.post.title
        }}</router-link>
        <div class="blogImgContainer">
          <img class="blogImg" :src="getImgFromBlogId(index, blog.id)" alt="blog post cover image"
            onload="this.style.display='block'" />
        </div>
      </li>
    </ol>
    <button @click="getBlogs" class="button-primaryDark-noBorder">
      Load More
    </button>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import blogService from "../service/blog.service";
import formattedDate from "../components/FormattedDate.vue";

const imgStore = reactive({});

export default {
  data() {
    return {};
  },
  components: {
    formattedDate,
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
    // calls twice because of imgstore assignment reactivity, figure out fix
    getImgFromBlogId(index, blogId) {
      if (imgStore[index] === undefined) {
        imgStore[index] = {
          img: "",
        };
      }
      let temp = imgStore[index];

      if (!temp.img) {
        blogService.getBlogImageById(blogId).then((response) => {
          temp.img = URL.createObjectURL(response.data);
        });
      }
      return imgStore[index].img;
    },
  },
};
</script>

<style lang="scss" scoped>
ol {
  display: grid;
  font-weight: 500;
  gap: 10px;
  grid-template-columns: repeat(auto-fill, minmax(MAX(200px, 100%/4), 1fr));
  grid-template-rows: repeat(auto-fit, minmax(10px, 1fr));
  margin-top: 30px;
  padding: 0;
  row-gap: 20px;
  width: 100%;
}

li {
  align-items: center;
  border-radius: 4px;
  border: 1px solid $outline;
  box-shadow: $shadowLight;
  box-sizing: border-box;
  display: flex;
  flex-direction: column-reverse;
  height: 25rem;
  height: fit-content;
  justify-content: flex-start;
  list-style-type: none;
  overflow: hidden;
  width: 100%;
}

img {
  display: none;
}

.container {
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  margin: 10px auto;
  width: 100%;
}

.blogFeedHead {
  align-self: flex-start;
  color: $primaryDark;
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.blogImgContainer {
  align-items: center;
  border-radius: 4px;
  border: 1px solid $outline;
  display: flex;
  height: 14rem;
  justify-content: center;
  margin-top: 10px;
  order: 3;
  overflow: hidden;
  width: 95%;
}

.blogImg {
  height: inherit;
  object-fit: cover;
  transition: transform .3s ease-in-out;
  width: 100%;
}

.blogTitle {
  color: $primaryDark;
  font-family: $inter;
  font-size: 1rem;
  font-weight: bold;
  margin-bottom: 10px;
  order: 1;
  text-align: center;
  transition: color .2s ease-in-out;
  width: 90%;
}

.blogTitle:hover {
  color: $highlightTwo;
}

.blogTitle:hover+.blogImgContainer>img {
  transform: scale(1.1);
  color: $highlightTwo;
}

.blogPostDate {
  display: flex;
  flex-direction: row;
  font-size: small;
  margin: 10px 0 10px 0;
  order: 2;
}

.postedOn {
  color: $primaryDark;
}

.formattedDate {
  color: $accentTwo;
}
</style>
