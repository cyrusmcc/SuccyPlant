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
      <div id="imgContainer">
        <img id="blogImg" src="../assets/blogplacehold.webp" alt="" />
        <div id="imgInfo"></div>
      </div>
      <div id="blogBody">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Culpa possimus
        cum nesciunt. Ab voluptatibus necessitatibus, praesentium quibusdam
        explicabo illum nisi iure perspiciatis, nam, alias neque inventore sit
        dolore et fugit!
      </div>
    </div>
  </div>
</template>

<script>
import blogService from "../service/blog.service";

export default {
  name: "BlogPost",
  data() {
    return {
      title: "",
      author: "",
      date: "",
    };
  },
  computed: {
    blog() {
      return this.$store.getters["blogs/getBlogById"](this.$route.params.id);
    },
  },
  mounted() {
    if (this.blog) {
      this.title = this.blog.post.title;
      this.author = this.blog.post.authorUsername;
      this.date = this.blog.postDate;
    } else {
      blogService.getBlogPostById(this.$route.params.id).then((response) => {
        this.blog = response.data;
        this.title = response.data.post.title;
        this.author = response.data.post.authorUsername;
        this.date = response.data.postDate;
      });
    }

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
  color: $lightShade;
  font-family: $rubik;
}

.dot {
  height: 5px;
  width: 5px;
  background-color: $lightShade;
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
  height: 160px;
  width: 100%;
  margin-bottom: 30px;
}

#blogImg {
  height: inherit;
  object-fit: cover;
  width: inherit;
}
</style>