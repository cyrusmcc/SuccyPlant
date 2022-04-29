<template>
  <div class="sideScrollContainer">
    <div class="itemList">
      <div class="sideScrollItem" v-for="(item, i) in items" :key="i">
        <img class="itemImg" :src="item.image" alt="carousel-image" />
        <span class="itemTitle">{{ item.title }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SideScrollGallery",
  data() {
    return {
      items: {
        p1: {
          title: "test",
          image: require("@/assets/imgs/house.jpg"),
        },
        p2: {
          title: "test",
          image: require("@/assets/imgs/house.jpg"),
        },
        p3: {
          title: "test",
          image: require("@/assets/imgs/house.jpg"),
        },
      },
    };
  },
};
</script>

<style scoped lang="scss">
.sideScrollContainer {
  width: 95%;
  overflow-x: scroll;
}
.itemList {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: fit-content;
}
.sideScrollItem {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 20rem;
  height: fit-content;
  margin: 1rem 1rem 1rem 0;
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
