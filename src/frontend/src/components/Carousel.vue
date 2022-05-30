<template>
  <div class="homeContainer">
    <div class="carousel">
      <div class="directionArrows" v-if="arrows">
        <div class="directionArrowLeft" @click="handleLeftClick">&lt;</div>
        <div class="directionArrowRight" @click="handleRightClick">&gt;</div>
      </div>
      <div class="carouselDots" v-if="images.length <= 5">
        <div class="carouselDot" v-for="(image, i) in images" :key="i" @click="handleDotClick(i)"
          :style="[i == index ? { opacity: 1 } : { opacity: 0.5 }]"></div>
      </div>
      <img :src="images[index].url" alt="carousel-image" />
    </div>
  </div>
</template>

<script>
export default {
  name: "Carousel",
  props: ["images", "autoplay", "arrows"],
  data() {
    return {
      content: "",
      length: this.images.length,
      index: 0,
    };
  },
  mounted() {
    if (this.autoplay) {
      this.interval = setInterval(() => {
        this.index++;
        if (this.index === this.length) {
          this.index = 0;
        }
      }, 10000);
    }
  },
  methods: {
    handleRightClick() {
      this.index = (this.index + 1) % this.length;
    },
    handleLeftClick() {
      this.index = (this.index - 1 + this.length) % this.length;
    },
    handleDotClick(i) {
      this.index = i;
    },
  },
};
</script>

<style scoped style="scss">
img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.homeContainer {
  height: 100%;
}

.carousel {
  height: 100%;
  width: 100%;
  position: relative;
}

.directionArrowLeft {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  font-size: 3rem;
  margin-left: 10px;
  color: #fff;
  opacity: 0.7;
  cursor: pointer;
  z-index: 1;
}

.directionArrowRight {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 0;
  margin-right: 10px;
  font-size: 3rem;
  color: #fff;
  opacity: 0.7;
  cursor: pointer;
  z-index: 1;
}

.carouselDots {
  position: absolute;
  bottom: 10px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  z-index: 1;
}

.carouselDot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin: 0 5px;
  background-color: #fff;
  cursor: pointer;
}
</style>
