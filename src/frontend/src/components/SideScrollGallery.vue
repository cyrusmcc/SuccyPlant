<template>
  <div class="sideScrollContainer">
    <h4 class="sideScrollLabel">Similar SuccyPlants</h4>
    <div class="itemList">
      <div class="directionArrowLeft" @click="handleLeftClick">&lt;</div>
      <div class="directionArrowRight" @click="handleRightClick">&gt;</div>
      <div class="sideScrollItem" v-for="(item, i) in itemsSubArr" :key="i">
        <img class="itemImg" :src="item.image" alt="carousel-image" />
        <span class="itemTitle">{{ item.title }}</span>
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
      itemList: this.items,
      /*
      items: [
        {
          title: "test1",
          image: require("@/assets/imgs/house.jpg"),
        },
        {
          title: "test2",
          image: require("@/assets/imgs/house.jpg"),
        },
        {
          title: "test3",
          image: require("@/assets/imgs/house.jpg"),
        },
        {
          title: "test4",
          image: require("@/assets/imgs/house.jpg"),
        },
        {
          title: "test5",
          image: require("@/assets/imgs/house.jpg"),
        },
        {
          title: "test6",
          image: require("@/assets/imgs/house.jpg"),
        },
      ],
      */
    };
  },
  mounted() {
    console.log(this.items);
    this.itemsList = this.items;
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
      //this.itemList = this.items;
      return this.items.slice(0, this.length);
    },
  },
};
</script>

<style scoped lang="scss">
.sideScrollContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: fit-content;
  max-width: 100%;
}
.sideScrollLabel {
  width: 100%;
  margin: 25px 0 0 0;
  padding-left: 5px;
  font-size: 1.1rem;
  font-weight: normal;
}
.directionArrowLeft {
  z-index: 1;
  position: absolute;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 2.5rem;
  height: 2.5rem;
  margin-left: 10px;
  background: $primaryLight;
  border-radius: 50%;
  outline: $outline;
  color: $primaryDark;
  box-shadow: $shadowLight;
  font-size: 2rem;
  cursor: pointer;
}
.directionArrowRight {
  z-index: 1;
  position: absolute;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 2.5rem;
  height: 2.5rem;
  margin-right: 10px;
  background: $primaryLight;
  border-radius: 50%;
  outline: $outline;
  color: $primaryDark;
  box-shadow: $shadowLight;
  font-size: 2rem;
  cursor: pointer;
}
.itemList {
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  column-gap: 10px;
  width: fit-content;
  max-width: 100%;
}
.sideScrollItem {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 20rem;
  height: fit-content;
  margin: 1rem 0;
  border: 1px solid $outline;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: $shadowLight;
}
.itemImg {
  width: 100%;
  height: 6rem;
  object-fit: cover;
}

.itemTitle {
  height: 100%;
  width: fit-content;
  margin: 10px 0;
  font-size: 1.2rem;
  font-style: italic;
  border-bottom: 4px solid $highlightOne;
}
</style>
