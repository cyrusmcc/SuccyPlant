<template>
  <div class="sideScrollContainer">
    <h4 class="sideScrollLabel">Similar SuccyPlants</h4>
    <div class="itemList">
      <div
        class="directionArrowLeft"
        @click="handleLeftClick"
        v-if="items.length > length"
      >
        &lt;
      </div>
      <div
        class="directionArrowRight"
        @click="handleRightClick"
        v-if="items.length > length"
      >
        &gt;
      </div>
      <div class="sideScrollItem" v-for="(item, i) in itemsSubArr" :key="i">
        <img
          class="itemImg"
          :src="require('@/assets/imgs/house.jpg')"
          alt="carousel-image"
        />
        <router-link class="itemTitle" :to="'/plant/' + item.id">{{
          item.scientificName
        }}</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SideScrollGallery",
  props: ["length", "items"],
  data() {
    return {
      len: 3,
      itemList: [],
    };
  },
  mounted() {
    this.itemList = this.items;
  },
  methods: {
    handleRightClick() {
      this.itemList.push(this.itemList.shift());
    },
    handleLeftClick() {
      this.itemList.unshift(this.itemList.pop());
    },
  },
  computed: {
    // Returns a subarray of the items array with the length of the len prop
    itemsSubArr() {
      return this.itemList.slice(0, this.length);
    },
  },
  watch: {
    items() {
      this.itemList = this.items;
    },
  },
};
</script>

<style scoped lang="scss">
.sideScrollContainer {
  align-items: center;
  display: flex;
  flex-direction: column;
  max-width: 100%;
  width: fit-content;
}

.sideScrollLabel {
  font-size: 1.1rem;
  font-weight: normal;
  margin: 25px 0 0 0;
  padding-left: 5px;
  width: 100%;
}

.directionArrowLeft {
  align-items: center;
  background: $primaryLight;
  border-radius: 50%;
  box-shadow: $shadowLight;
  color: $primaryDark;
  cursor: pointer;
  display: flex;
  font-size: 2rem;
  height: 2.5rem;
  justify-content: center;
  left: 0;
  margin-bottom: 25px;
  margin-left: 10px;
  outline: $outline;
  position: absolute;
  width: 2.5rem;
  z-index: 1;
}

.directionArrowRight {
  align-items: center;
  background: $primaryLight;
  border-radius: 50%;
  box-shadow: $shadowLight;
  color: $primaryDark;
  cursor: pointer;
  display: flex;
  font-size: 2rem;
  height: 2.5rem;
  justify-content: center;
  margin-bottom: 25px;
  margin-right: 10px;
  outline: $outline;
  position: absolute;
  right: 0;
  width: 2.5rem;
  z-index: 1;
}

.itemList {
  align-items: center;
  column-gap: 10px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  max-width: 100%;
  position: relative;
  width: fit-content;
}

.sideScrollItem {
  align-items: center;
  border-radius: 10px;
  border: 1px solid $outline;
  box-shadow: $shadowLight;
  display: flex;
  flex-direction: column;
  height: fit-content;
  justify-content: flex-start;
  margin: 1rem 0;
  overflow: hidden;
  width: 20rem;
}

.itemImg {
  height: 6rem;
  object-fit: cover;
  width: 100%;
}

.itemTitle {
  color: $primaryDark;
  font-size: 0.9rem;
  height: 100%;
  margin: 10px 0;
  text-align: center;
  width: fit-content;
}
</style>
