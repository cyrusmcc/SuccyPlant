<template>
  <div class="container">
    <ol>
      <!--
      <div id="blogFeedHead">Recent articles</div>
      -->
      <li v-for="(blog, index) in blogArr" :key="index">
        <div id="blogImgContainer">
          <img
            class="blogImg"
            :src="getImgFromBlogId(index, blog.id)"
            alt="blog post cover image"
            onload="this.style.display='block'"
          />
        </div>
        <div id="blogTextContainer">
          <router-link class="blogTitle" :to="'/blog/' + blog.id">{{
            blog.post.title
          }}</router-link>
          <div id="blogPostDate">
            <span id="postedOn">Posted on&nbsp;</span>
            <formatted-date
              :date="blog.postDate"
              :format="'full'"
              class="formattedDate"
            />
          </div>
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
  width: 60%;
  max-width: 800px;
  padding: 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-weight: 500;
  margin-top: 0;
  row-gap: 20px;
}
li {
  display: flex;
  flex-direction: row;
  align-items: center;
  list-style-type: none;
  height: fit-content;
  overflow: hidden;
  box-sizing: border-box;
  width: 31rem;
}

img {
  display: none;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  margin: 10px auto;
}

#blogFeedHead {
  align-self: flex-start;
  color: $primaryDark;
  font-size: 1.2rem;
  margin-bottom: 20px;
}

#blogImgContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 150px;
  width: 75%;
}

.blogImg {
  height: inherit;
  width: 100%;
  object-fit: cover;
}

#blogTextContainer {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  row-gap: 15px;
  height: fit-content;
  width: 100%;
  box-sizing: border-box;
  margin-left: 15px;
  padding: 10px 0 10px 10px;
}

.blogTitle {
  font-size: 1.2rem;
  color: $primaryDark;
  font-style: italic;
  text-align: center;
}

#blogPostDate {
  margin-top: 5px;
  display: flex;
  flex-direction: row;
  font-size: small;
}

#postedOn {
  color: $primaryDark;
}

.formattedDate {
  color: $accentTwo;
  font-weight: bold;
}
</style>
