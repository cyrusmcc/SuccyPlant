<template>
  <div class="gridContainer">
    <router-link class="link" to="/plants" @click="setPreselectedTags('House Plant')">
      <img src="../assets/imgs/plant.jpg" class="contentBanner" alt="" />
      <div class="textContainer">
        <span class="text">Browse</span>
        <span class="text">House Plants</span>
      </div>
    </router-link>
    <router-link class="link" to="/plants" @click="setPreselectedTags('Succ')"><img src="../assets/imgs/succulent.jpg"
        class="contentBanner" alt="" />
      <div class="textContainer">
        <span class="text">Browse</span>
        <span class="text">Succulents</span>
      </div>
    </router-link>
    <router-link class="link" to="/plants" @click="setPreselectedTags('Cactus')">
      <img src="../assets/imgs/cactus.jpg" class="contentBanner" alt="" />
      <div class="textContainer">
        <span class="text">Browse</span>
        <span class="text">Cactuses</span>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "squareGrid",
  methods: {
    setPreselectedTags(option) {
      let preselectedTags = [{
        label: "Type",
        option: option,
      }];
      this.$store.commit("plants/setPreselectedTags", preselectedTags);
    },
  }
};
</script>

<style scoped lang="scss">
.gridContainer {
  height: fit-content;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(195px, 1fr));
  grid-template-rows: repeat(auto-fit, minmax(10px, 1fr));
  gap: 10px;
}

.gridContainer>* {
  width: 30rem;
  max-width: 100%;
  min-height: 85px;
  height: 5rem;
  overflow: hidden;
  border-radius: 4px;
  box-shadow: $shadowLight;
  position: relative;
}

.link {
  position: relative;
}

.textContainer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.3);
  color: white;
  font-size: 1.8rem;
  font-weight: bold;
  font-family: $amatic;
  text-shadow: -1px 2px 5px black;
  text-transform: uppercase;
  text-align: center;
}

.textContainer:hover {
  background-color: rgba(0, 0, 0, 0);
  font-size: 1.6rem;
  box-shadow: $shadow;
}

.contentBanner {
  width: 100%;
  height: auto;
}
</style>
