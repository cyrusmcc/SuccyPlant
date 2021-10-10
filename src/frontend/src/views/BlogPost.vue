<template>
  <div class="container">
    <div id="blogContainer">
      <header>
        <h2 id="blogTitle">{{ title }}</h2>
        <div id="blogInfo">
          <span id="blogAuthor"> {{ author }}</span>
          <span class="dot"></span>
          <span id="postDate"> {{ date }}</span>
        </div>
      </header>
      <div id="imgContainer" v-if="imgUrl">
        <img id="blogImg" :src="imgUrl" alt="" />
        <div id="imgInfo"></div>
      </div>
      <div id="blogBody" v-html="bodyText"></div>
    </div>
  </div>
</template>

<script>
import blogService from "../service/blog.service";

export default {
  name: "BlogPost",
  data() {
    return {
      blogPost: "",
      title: "",
      author: "",
      date: "",
      imgUrl: "",
      bodyText: "",
    };
  },
  computed: {
    blog() {
      return this.$store.getters["blogs/getBlogById"](this.$route.params.id);
    },
  },
  created() {
    blogService.getBlogPostById(this.$route.params.id).then((response) => {
      this.blogPost = response.data;
      this.title = response.data.post.title;
      this.author = response.data.post.authorUsername;
      this.date = response.data.postDate;
      console.log(response.data);
    });

    blogService.getBlogImageById(this.$route.params.id).then((response) => {
      this.imgUrl = URL.createObjectURL(response.data);
    });

    blogService.getBlogTextById(this.$route.params.id).then((response) => {
      this.bodyText = response.data;
    });

    if (this.date != "") {
      const blogDate = new Date(this.date),
        locale = "en-us",
        day = blogDate.toLocaleString(locale, { day: "numeric" }),
        month = blogDate.toLocaleString(locale, { month: "long" }),
        year = blogDate.toLocaleString(locale, { year: "numeric" });
      this.date = month + " " + day + " " + year;
    }
  },
};
</script>

<style lang="scss" scoped>
header {
  margin-bottom: 20px;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: $primaryLight;
}

.dot {
  height: 5px;
  width: 5px;
  background-color: $primaryLight;
  border-radius: 50%;
  display: inline-block;
}

#blogContainer {
  margin-top: 30px;
  width: 85%;
}

#blogInfo {
  color: $accentTwo;
  display: flex;
  align-items: center;
  flex-direction: row;
  column-gap: 20px;
}

#blogTitle {
  font-style: italic;
  text-decoration: underline;
  margin-bottom: 30px;
}

#imgContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 400px;
  width: 100%;
  margin-bottom: 30px;
}

#blogImg {
  height: inherit;
  object-fit: cover;
  width: inherit;
}

@include screen-md() {
  #blogContainer {
    width: 45rem;
  }
}
</style>
