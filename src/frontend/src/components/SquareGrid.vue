<template>
  <div class="gridContainer">
    <router-link class="link" to="/plants?type=house+plant"
      ><img
        src="../assets/imgs/greenPattern.svg"
        class="contentBanner"
        alt=""
      />
      <div class="textContainer">
        <span class="text">Browse</span>
        <span class="text">House Plants</span>
      </div>
    </router-link>
    <router-link class="link" to="/plants?type=succ"
      ><img src="../assets/imgs/bluePattern.svg" class="contentBanner" alt="" />
      <div class="textContainer">
        <span class="text">Browse</span>
        <span class="text">Succulents</span>
      </div>
    </router-link>
    <router-link class="link" to="/plants?type=cactus"
      ><img
        src="../assets/imgs/orangePattern.svg"
        class="contentBanner"
        alt=""
      />
      <div class="textContainer">
        <span class="text">Browse</span>
        <span class="text">Cactuses</span>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {};
</script>

<style scoped lang="scss">
.gridContainer {
  margin-top: 15px;
  width: fit-content;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 15px;
}

.link {
  height: fit-content;
  width: fit-content;
  position: relative;
}

.textContainer {
  display: flex;
  flex-direction: column;
  width: fit-content;
  text-align: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 1.5rem;
  font-family: $lobster;
  color: $primaryLight;
  text-shadow: -1px 2px 5px black;
}

.contentBanner {
  width: 10rem;
  height: auto;
  border-radius: 4px;
  box-shadow: -4px 4px 8px 0 RGB(0 0 0/10%), 0 8px 16px 0 RGB(0 0 0/17%);
}
</style>
