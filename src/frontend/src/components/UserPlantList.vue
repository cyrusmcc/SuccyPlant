<template>
  <div class="plantContainer" v-if="plants.length > 0">
    <h3 class="plantListTitle">{{ username }}'s succyplants</h3>
    <div class="plantList">
      <div v-for="(plant, p) in plants" :key="p" class="plant">
        <div class="plantImgContainer">
          <img
            class="plantImage"
            :src="getImg(plant.imgUrl)"
            alt="plant image"
          />
        </div>
        <div class="nameContainer">
          <router-link :to="'/plant/' + plant.id">
            <span class="plantName">{{ plant.post.title }}</span>
          </router-link>
        </div>
      </div>
    </div>
  </div>
  <div class="notFoundContainer" v-if="plants.length == 0">
    <span class="noPlantFoundLabel"
      >No Succyplants found for {{ username }}</span
    >
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
  methods: {
    getImg(imgUrl) {
      return URL.createObjectURL(imgUrl);
    },
  },
};
</script>

<style lang="scss" scoped>
.plantContainer {
  border-radius: 4px;
  height: fit-content;
  width: 95%;
}

.plantListTitle {
  font-size: 1.2rem;
  font-weight: normal;
  margin: 1rem;
  text-align: center;
}

.plantList {
  align-items: center;
  display: flex;
  flex-direction: column;
  margin: 10px 0;
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

.plantImgContainer {
  height: 100px;
  width: 150px;
}

.plantImage {
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.nameContainer {
  @include flexCenter();
  height: 100%;
  width: 100%;
}

.plantName {
  display: block;
  overflow: hidden;
  text-align: center;
  text-overflow: ellipsis;
  white-space: normal;
  width: 100%;
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
    justify-items: center;
    overflow: hidden;
  }

  .plant {
    align-items: center;
    flex-direction: column;
    height: fit-content;
    row-gap: 10px;
    width: 150px;
  }

  .plantContainer {
    border: 1px solid $outline;
  }

  .plantImgContainer {
    height: 100px;
    width: 100px;
  }
}
</style>
