<template>
  <div class="plantContainer" v-if="plants.length > 0">
    <h3 class="plantListTitle">{{ username }}'s succyplants</h3>
    <div class="plantList">
      <div v-for="(plant, p) in plants" :key="p" class="plant">
        <img class="plantImage" :src="url" alt="plant image" />
        <div class="nameContainer">
          <router-link :to="'/plant/' + plant.id">
            <span class="plantName">{{ plant.post.title }}</span>
          </router-link>
        </div>
      </div>
    </div>
  </div>
  <div class="notFoundContainer" v-if="plants.length == 0">
    <span class="noPlantFoundLabel">No Succyplants found for {{ username }}</span>
  </div>
</template>

<script>
export default {
  name: "UserPlantList",
  props: ["plants"],
  data() {
    return {
      username: this.$route.params.username,
      url: "https://via.placeholder.com/150",
    };
  },
};
</script>

<style lang="scss" scoped>
.plantContainer {
  width: 95%;
  border-radius: 4px;
  height: fit-content;
}

.plantListTitle {
  font-size: 1.2rem;
  font-weight: normal;
  margin: 1rem;
  text-align: center;
}

.plantList {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px 0;
}

.plantList>div {
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

.notFoundContainer {
  align-items: center;
  border-radius: 4px;
  border: 1px solid $outline;
  box-shadow: $shadowLight;
  display: flex;
  flex-direction: column;
  height: 10rem;
  justify-content: center;
  width: 100%;
}

@include screen-md() {
  .plantList {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    overflow: hidden;
    justify-items: center;
  }

  .plant {
    flex-direction: column;
    align-items: center;
    row-gap: 10px;
    height: fit-content;
    width: 150px;
  }

  .plantContainer {
    width: 100%;
  }
}
</style>
