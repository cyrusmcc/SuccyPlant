<template>
  <div class="container">
    <ol>
      <!--
      <div class="blogFeedHead">Recent articles</div>
      -->
      <li v-for="(blog, index) in blogArr" :key="index">
        <div class="blogImgContainer">
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
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  font-weight: 500;
  margin-top: 0;
  max-width: 800px;
  padding: 0;
  row-gap: 20px;
  width: 100%;
}
li {
  align-items: center;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  height: fit-content;
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
  display: flex;
  height: 115px;
  justify-content: center;
  width: 350px;
}

.blogImg {
  height: inherit;
  object-fit: cover;
  width: 100%;
}

#blogTextContainer {
  align-items: flex-start;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  height: fit-content;
  margin-left: 15px;
  padding: 10px 0 10px 10px;
  row-gap: 15px;
  width: 100%;
}

.blogTitle {
  color: $primaryDark;
  font-size: 1.2rem;
  font-style: italic;
  text-align: start;
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

@include screen-md {
  .blogImgContainer {
    height: 150px;
    width: 550px;
  }
}
</style>
