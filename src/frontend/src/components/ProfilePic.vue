<template>
  <div class="container" id="userPicImgCont">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "ProfilePic",
};
</script>

<style lang="scss" scoped>
img {
  object-fit: cover;
  float: left;
  width: 100%;
  height: 100%;
}

#userPicImgCont {
  display: flex;
  z-index: 3;
  border: 1px solid $outline;
  border-radius: 50%;
  height: 4.5rem;
  width: 4.5rem;
  overflow: hidden;
  box-shadow: -1px 1px 18px -2px rgba(15, 15, 15, 0.08);
}
</style>
