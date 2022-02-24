<template>
  <title>{{ plant.name }}</title>
  <div class="plantPageContainer">
    <div class="carouselContainer">
      <carousel :images="images" :arrows="true"></carousel>
    </div>
    <div class="titleContainer">
      <h1>{{ plant.name }}</h1>
      <h3>{{ plant.scientificName }}</h3>
    </div>
    <div class="careGuide">
      <div class="careGuideHead">
        <h2>Care Guide</h2>
      </div>
      <div class="careGuideSize">
        <h4 class="careGuideCategoryHead">How big?</h4>
        <span
          >Lorem ipsum dolor sit amet consectetur adipisicing elit. Quam
          ratione, tenetur iusto veniam illum dignissimos aperiam dolor totam
          veritatis quas pariatur reprehenderit maiores aut deleniti doloremque
          laboriosam! Fugiat, facere maxime.</span
        >
      </div>
      <div class="careGuideDifficulty">
        <h4 class="careGuideCategoryHead">How hard?</h4>
        <span
          >Lorem ipsum dolor sit amet consectetur adipisicing elit. Quam
          ratione, tenetur iusto veniam illum dignissimos aperiam dolor totam
          veritatis quas pariatur reprehenderit maiores aut deleniti doloremque
          laboriosam! Fugiat, facere maxime.</span
        >
      </div>
      <div class="careGuideLight">
        <h4 class="careGuideCategoryHead">How much light?</h4>
        <span
          >Lorem ipsum dolor sit amet consectetur adipisicing elit. Quam
          ratione, tenetur iusto veniam illum dignissimos aperiam dolor totam
          veritatis quas pariatur reprehenderit maiores aut deleniti doloremque
          laboriosam! Fugiat, facere maxime.</span
        >
      </div>
      <div class="careGuidePet">
        <h4 class="careGuideCategoryHead">Are {{ plant.name }} pet safe?</h4>
        <span
          >Lorem ipsum dolor sit amet consectetur adipisicing elit. Quam
          ratione, tenetur iusto veniam illum dignissimos aperiam dolor totam
          veritatis quas pariatur reprehenderit maiores aut deleniti doloremque
          laboriosam! Fugiat, facere maxime.</span
        >
      </div>
    </div>
  </div>
</template>
  
  <script>
import Carousel from "../components/Carousel.vue";

export default {
  name: "PlantPage",
  components: { Carousel },
  props: [],
  data() {
    return {
      images: [
        {
          url: require("@/assets/imgs/house.jpg"),
        },
      ],
      plant: {
        name: "Bitter Aloe",
        scientificName: "A. ferox",
        description: "",
        id: "",
      },
    };
  },
};
</script>

<style scoped lang="scss">
h1 {
  font-family: $raleway;
  font-size: 2.5rem;
  font-weight: normal;
  margin-bottom: 0;
}
h3 {
  font-family: $raleway;
  font-size: 1.5rem;
  font-weight: 300;
  font-style: italic;
  margin: 10px 0 0 0;
}
.titleContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}
.plantPageContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.carouselContainer {
  width: 95%;
  height: fit-content;
  padding-top: 5px;
  border-radius: 5px;
  border: 1px solid $outline;
  box-shadow: $shadowLight;
  overflow: hidden;
}

.careGuide {
  width: 95%;
  margin-top: 50px;
  border: 1px solid $outline;
  border-radius: 5px;
  overflow: hidden;
}
.careGuide > div:nth-child(n + 2) {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  width: 100%;
  height: 6rem;
  overflow: hidden;
}

.careGuide > div:nth-child(n + 2) > * {
    margin-left: 5px;
}

.careGuideHead {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 6rem;
  background: $accentTwo;
}
.careGuideSize {
  background: $accentFive;
}
.careGuideDifficulty {
  background: $accentFour;
}
.careGuideLight {
  background: $accentThree;
}
.careGuidePet {
  background: $accentSix;
}
</style>