<template>
  <div class="plantPageContainer" v-if="plant">
    <title>{{ plantPost.title }}</title>

    <div class="boxContainer">
      <div class="mediaBox">
        <div class="carouselContainer">
          <!--
          <carousel :images="images" :arrows="true"></carousel>
          -->
          <img class="plantImg" :src="imgUrl" alt="" />
        </div>
        <div class="plantListButtons" v-if="currentUser">
          <button :class="[
            hasPlantInUserPlants
              ? 'removeButton'
              : 'button-primaryDark-noBorder',
          ]" @click="updatePlantList('userPlants')">
            {{
                hasPlantInUserPlants
                  ? "Remove from my plants"
                  : "Add to my plants"
            }}
          </button>
          <button :class="[
            hasPlantInWishList
              ? 'removeButton'
              : 'button-primaryDark-noBorder',
          ]" @click="updatePlantList('wishList')">
            {{
                hasPlantInWishList ? "Remove from wishlist" : "Add to wishlist"
            }}
          </button>
        </div>
        <div class="desktopContainer" v-if="width >= 1115">
          <side-scroll-gallery :length="sideScrollLength" :items="relatedPlants" v-if="relatedPlants.length > 0" />
          <comment-box />
        </div>
      </div>
      <div class="infoBox">
        <div class="titleContainer">
          <h1>{{ plant.scientificName }}</h1>
          <h3>
            {{ plant.commonName }}
          </h3>
        </div>
        <div class="descGuideContainer">
          <div class="descContainer">
            <div class="plantDescription">
              <p>{{ plant.description }}</p>
            </div>
          </div>
          <div class="careGuide">
            <div class="careGuideHead">
              <h2>Quick Care Guide</h2>
            </div>
            <div class="careGuideSize">
              <h4 class="careGuideCategoryHead">How big?</h4>
              <span>
                {{ plant.sizeDesc }}
              </span>
            </div>
            <div class="careGuideDifficulty">
              <h4 class="careGuideCategoryHead">How much water?</h4>
              <span>
                {{ plant.waterDesc }}
              </span>
            </div>
            <div class="careGuideLight">
              <h4 class="careGuideCategoryHead">How much light?</h4>
              <span>
                {{ plant.lightDesc }}
              </span>
            </div>
            <div class="careGuidePet">
              <h4 class="careGuideCategoryHead">
                Are {{ plantPost.title }} pet safe?
              </h4>
              <span>
                {{ plant.petDesc }}
              </span>
            </div>
          </div>
        </div>
        <div class="mobileContainer" v-if="width < 1115">
          <side-scroll-gallery :length="sideScrollLength" :items="relatedPlants" v-if="relatedPlants.length > 0">
          </side-scroll-gallery>
          <comment-box />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//import Carousel from "../components/Carousel.vue";
import SideScrollGallery from "../components/SideScrollGallery.vue";
import CommentBox from "../components/CommentBox.vue";
import plantService from "../service/plant.service";
import userService from "../service/user.service";

export default {
  name: "PlantPage",
  components: {
    //Carousel,
    SideScrollGallery,
    CommentBox,
  },
  props: [],
  data() {
    return {
      plant: {},
      plantPost: {},
      imgUrl: "",
      relatedPlants: [],
      message: "",
      width: 0,
      images: [
        {
          //url: require("@/assets/imgs/house.jpg"),
        },
      ],
      hasPlantInUserPlants: false,
      hasPlantInWishList: false,
      sideScrollLength: 3,
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    getPlantFromStore() {
      return this.$store.getters["plants/getPlantById"](this.$route.params.id);
    },
  },
  created() {
    // allows redirects to same path ('/plant/:id'), does not need
    // to redraw page
    window.addEventListener("resize", this.updateWidth);
    this.$watch(
      () => this.$route.params.id,
      () => {
        this.getPlant();
        this.getRelatedPlants();
      }
    );
  },
  mounted() {
    this.getPlant();
    this.getRelatedPlants();
    this.updateWidth();
  },
  unmounted() {
    window.removeEventListener("resize", this.updateWidth);
  },
  methods: {
    getPlant() {
      // avoid having call server to get plant if already in store
      this.plant = this.getPlantFromStore;
      if (this.plant && this.plant.imgUrl) {
        this.plantPost = this.plant.post;
        this.imgUrl = URL.createObjectURL(this.plant.imgUrl);
      } else if (this.$route.params.id) {
        const getPlant = async () => {
          let temp = await plantService.getPlantAndImgById(
            this.$route.params.id,
            "full"
          );
          if (temp) {
            this.plant = temp.info;
            this.plantPost = this.plant.post;
            this.imgUrl = URL.createObjectURL(temp.imgUrl.data);
          }
        };
        /*
        this.images.push({
        url: URL.createObjectURL(response.data),
        });
        */
        getPlant();
      }
    },
    getRelatedPlants() {
      //length is equal to number of plants in initial view, so * 2 fetches two full slides of plants
      const numPlantsToRetrieve = this.sideScrollLength * 2;
      const getRelatedPlants = async () => {
        const relatedPlants = await plantService.getRelatedPlantsAndImgs(
          numPlantsToRetrieve,
          this.$route.params.id
        );
        return relatedPlants;
      };
      getRelatedPlants().then((relatedPlants) => {
        this.relatedPlants = relatedPlants;
      });
    },
    updateWidth() {
      this.width = window.innerWidth;
    },
    addPlantToList(listName) {
      if (this.currentUser) {
        userService
          .addPlantToList(this.currentUser.id, this.$route.params.id, listName)
          .then(
            (res) => {
              if (listName === "userPlants") {
                this.hasPlantInUserPlants = res;
              } else if (listName === "wishList") {
                this.hasPlantInWishList = res;
              }
            },
            (error) => {
              this.message =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();
            }
          );
      }
    },
    removePlantFromList(listName) {
      userService
        .removePlantFromList(
          this.currentUser.id,
          this.$route.params.id,
          listName
        )
        .then(
          () => {
            if (listName === "userPlants") {
              this.hasPlantInUserPlants = false;
            } else if (listName === "wishList") {
              this.hasPlantInWishList = false;
            }
          },
          (error) => {
            this.message =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();
          }
        );
    },
    updatePlantList(listName) {
      if (this.currentUser) {
        if (listName === "userPlants") {
          if (this.hasPlantInUserPlants) {
            this.removePlantFromList(listName);
          } else {
            this.addPlantToList(listName);
          }
        } else if (listName === "wishList") {
          if (this.hasPlantInWishList) {
            this.removePlantFromList(listName);
          } else {
            this.addPlantToList(listName);
          }
        }
      }
    },
    hasPlantInList(listName) {
      if (this.currentUser) {
        userService
          .hasPlantInList(this.currentUser.id, this.$route.params.id, listName)
          .then((res) => {
            if (listName === "userPlants") {
              this.hasPlantInUserPlants = res;
            } else if (listName === "wishList") {
              this.hasPlantInWishList = res;
            }
          });
      }
    },
  },
};
</script>

<style scoped lang="scss">
h1 {
  font-family: $raleway;
  font-size: 1.8rem;
  font-weight: normal;
  margin-bottom: 0;
}

h2 {
  font-weight: normal;
}

h3 {
  font-family: $raleway;
  font-size: 1.5rem;
  font-style: italic;
  font-weight: 300;
  margin: 10px 0 0 0;
}

.plantListButtons {
  column-gap: 5px;
  display: flex;
  justify-content: flex-start;
  margin: 20px 0 10px 0;
  width: 100%;
}

.button-primaryDark-noBorder {
  font-family: $inter;
  font-size: 0.8rem;
  padding: 1px 10px;
}

.titleContainer {
  align-items: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  width: 100%;
}

.plantPageContainer {
  align-items: center;
  display: flex;
  flex-direction: column;
  height: fit-content;
  justify-content: center;
  padding-top: 10px;
  width: 100%;
}

.boxContainer {
  height: 100%;
  max-width: 100rem;
  width: 95%;
}

.mediaBox {
  align-items: center;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.carouselContainer {
  border-radius: 5px;
  border: 1px solid $outline;
  box-shadow: $shadowLight;
  height: 17rem;
  overflow: hidden;
  width: 100%;
}

.careGuideCategoryHead {
  margin-bottom: 5px;
}

.descGuideContainer {
  align-items: center;
  display: flex;
  flex-direction: column;
  font-family: $raleway;
  justify-content: flex-start;
  margin-top: 25px;
  text-align: center;
  width: 100%;
}

.descContainer {
  align-content: flex-start;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.descContainer>h2 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 20px;
  text-align: center;
}

.careGuide {
  border-radius: 5px;
  margin-bottom: 10px;
  margin-top: 20px;
  overflow: hidden;
  width: 100%;
}

.careGuide>div {
  align-items: center;
  display: flex;
  flex-direction: column;
  height: fit-content;
  justify-content: flex-start;
  overflow: hidden;
  width: 100%;
}

.careGuide>div:nth-child(n + 2)>* {
  text-align: center;
  width: 95%;
}

.careGuideHead {
  // TODO do I like this style?
  align-items: center;
  background: $primaryDark;
  border-bottom: 0px;
  border-radius: 4px 4px 0px 0px;
  color: $primaryLight;
  display: flex;
  flex-direction: column;
  height: 4rem;
  justify-content: center;
  width: 100%;
}

.careGuideSize {
  border-left: 2px solid $accentFive;
  border-right: 2px solid $accentFive;
  box-sizing: border-box;
}

.careGuideDifficulty {
  border-left: 2px solid $accentFour;
  border-right: 2px solid $accentFour;
  box-sizing: border-box;
}

.careGuideLight {
  border-left: 2px solid $accentThree;
  border-right: 2px solid $accentThree;
  box-sizing: border-box;
}

.careGuidePet {
  //background: $accentSix;
  border-radius: 0 0 4px 4px;
  border: 2px solid $accentSix;
  border-top: 0px;
  box-sizing: border-box;
}

.careGuidePet>span {
  margin-bottom: 10px;
}

// TEMP?
.plantImg {
  height: 100%;
  object-fit: cover;
  width: 100%;
}

.desktopContainer {
  width: 100%;
}

@include screen-lg() {
  .descGuideContainer {
    align-items: center;
    flex-direction: column;
    justify-content: flex-start;
    width: 100%;
  }

  .descContainer {
    text-align: center;
  }

  .descContainer>h2 {
    height: 6rem;
    margin: 0;
  }

  .boxContainer {
    column-gap: 15px;
    display: flex;
    flex-direction: row;
  }

  .mediaBox {
    align-items: center;
    display: flex;
    flex-direction: column;
    width: 65%;
  }

  .infoBox {
    align-items: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 35%;
  }

  .carouselContainer {
    height: 30rem;
  }
}
</style>
