<template>
  <div>
    <keep-alive>
      <blog-feed />
    </keep-alive>
  </div>
</template>

<script>
import BlogFeed from "../components/BlogFeed.vue";

export default {
  components: {
    BlogFeed,
  },
  name: "Home",
  data() {
    return {
      content: "",
    };
  },
};
</script>
<style scoped lang="scss"></style>
