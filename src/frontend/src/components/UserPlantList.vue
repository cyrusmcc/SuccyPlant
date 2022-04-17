<template>
  <div class="plantContainer">
    <h3 class="plantListTitle">{{ username }}'s succyplants</h3>
    <div class="plantList">
      <div v-for="(plant, p) in plants" :key="p" class="plant">
        <img class="plantImage" :src="plant.url" alt="plant image" />
        <div class="nameContainer">
          <span class="plantName">{{ plant.name }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserPlantList",
  data() {
    return {
      username: this.$route.params.username,
      plants: [
        {
          name: "Succulent",
          url: "https://via.placeholder.com/150",
        },
        {
          name: "Succulent2",
          url: "https://via.placeholder.com/150",
        },
        {
          name: "Succulent3",
          url: "https://via.placeholder.com/150",
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.plantContainer {
  width: 95%;
  border: 1px solid $outline;
  border-radius: 4px;
  box-shadow: $shadowLight;
  height: fit-content;
}
.plantListTitle {
  font-size: 1rem;
  font-weight: bold;
  margin: 1rem;
  text-align: center;
}
.plantList {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
  max-height: 400px;
  overflow-y: scroll;
}
.plantList > div {
  margin: 5px 0;
}
.plant {
  display: flex;
  flex-direction: row;
  height: 100px;
  width: 95%;
}
.plantImage {
  height: 100px;
  width: 100px;
}
.nameContainer {
  height: 100%;
  width: 100%;
  @include flexCenter();
}
.plantName {
  display: block;
  width: 100%;
  white-space: normal;
  text-overflow: ellipsis;
  overflow: hidden;
  text-align: center;
}

@include screen-md() {
  .plantList {
    flex-direction: row;
    flex-wrap: wrap;
    row-gap: 10px;
    overflow-y: hidden;
  }
  .plant {
    flex-direction: column;
    align-items: center;
    row-gap: 10px;
    height: fit-content;
    width: 150px;
  }
}
</style>