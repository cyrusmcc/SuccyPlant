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
    pushRoute(route) {
      try {
        this.$router.push(route);
      } catch (error) {
        if (error.message === "NavigationDuplicated") {
          this.$router.replace(route);
        } else {
          throw error;
        }
      }
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
  font-size: 1rem;
  font-style: italic;
  color: $primaryDark;
  text-align: center;
}
</style>
