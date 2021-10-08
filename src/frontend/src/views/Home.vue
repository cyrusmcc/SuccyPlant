<template>
  <div>
    <blog-feed />
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
