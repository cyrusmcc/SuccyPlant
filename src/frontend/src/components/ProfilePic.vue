<template>
  <div class="container" id="userPicImgCont">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "ProfilePic"
}
</script>

<style lang="scss">

#userPic{
  width: 5.5rem;
}

#userPicImgCont {
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 3;
  border-radius: 50%;
  height: 4.5rem;
  width: 4.5rem;
  overflow: hidden;
  box-shadow: -1px 1px 18px -2px rgba(15, 15, 15, 0.08);}
</style>