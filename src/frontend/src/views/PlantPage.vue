<template>
  <div class="plantPageContainer" v-if="plant">
    <title>{{ plantPost.title }}</title>

    <div class="boxContainer">
      <div class="mediaBox">
        <div class="carouselContainer">
          <carousel :images="images" :arrows="true"></carousel>
        </div>
        <div class="plantListButtons" v-if="currentUser">
          <button
            :class="[
              hasPlantInUserPlants
                ? 'removeButton'
                : 'button-primaryDark-noBorder',
            ]"
            @click="updatePlantList('userPlants')"
          >
            {{
              hasPlantInUserPlants
                ? 'Remove from my plants'
                : 'Add to my plants'
            }}
          </button>
          <button
            :class="[
              hasPlantInWishList
                ? 'removeButton'
                : 'button-primaryDark-noBorder',
            ]"
            @click="updatePlantList('wishList')"
          >
            {{
              hasPlantInWishList ? 'Remove from wishlist' : 'Add to wishlist'
            }}
          </button>
        </div>
        <side-scroll-gallery></side-scroll-gallery>
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
              <h4 class="careGuideCategoryHead">How hard?</h4>
              <span>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Quam
                ratione, tenetur iusto veniam illum dignissimos aperiam dolor
                totam veritatis quas pariatur reprehenderit maiores aut deleniti
                doloremque laboriosam! Fugiat, facere maxime.
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
      </div>
    </div>
  </div>
</template>

<script>
import Carousel from '../components/Carousel.vue'
import SideScrollGallery from '../components/SideScrollGallery.vue'
import plantService from '../service/plant.service'
import userService from '../service/user.service'

export default {
  name: 'PlantPage',
  components: { Carousel, SideScrollGallery },
  props: [],
  data() {
    return {
      plant: {},
      plantPost: {},
      message: '',
      images: [
        {
          url: require('@/assets/imgs/house.jpg'),
        },
      ],
      hasPlantInUserPlants: false,
      hasPlantInWishList: false,
    }
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user
    },
  },
  mounted() {
    const getPlant = async () => {
      this.plant = await plantService.getPlantById(this.$route.params.id)
      this.plantPost = this.plant.post
      console.log(this.plant)
    }
    getPlant().then(() => {
      this.hasPlantInList('userPlants')
      this.hasPlantInList('wishList')
    })
  },
  methods: {
    addPlantToList(listName) {
      if (this.currentUser) {
        userService
          .addPlantToList(this.currentUser.id, this.$route.params.id, listName)
          .then(
            (res) => {
              if (listName === 'userPlants') {
                this.hasPlantInUserPlants = res
              } else if (listName === 'wishList') {
                this.hasPlantInWishList = res
              }
            },
            (error) => {
              this.message =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString()
            },
          )
      }
    },
    removePlantFromList(listName) {
      userService
        .removePlantFromList(
          this.currentUser.id,
          this.$route.params.id,
          listName,
        )
        .then(
          () => {
            if (listName === 'userPlants') {
              this.hasPlantInUserPlants = false
            } else if (listName === 'wishList') {
              this.hasPlantInWishList = false
            }
          },
          (error) => {
            this.message =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString()
          },
        )
    },
    updatePlantList(listName) {
      if (this.currentUser) {
        if (listName === 'userPlants') {
          if (this.hasPlantInUserPlants) {
            this.removePlantFromList(listName)
          } else {
            this.addPlantToList(listName)
          }
        } else if (listName === 'wishList') {
          if (this.hasPlantInWishList) {
            this.removePlantFromList(listName)
          } else {
            this.addPlantToList(listName)
          }
        }
      }
    },
    hasPlantInList(listName) {
      if (this.currentUser) {
        userService
          .hasPlantInList(this.currentUser.id, this.$route.params.id, listName)
          .then((res) => {
            if (listName === 'userPlants') {
              this.hasPlantInUserPlants = res
            } else if (listName === 'wishList') {
              this.hasPlantInWishList = res
            }
          })
      }
    },
  },
}
</script>

<style scoped lang="scss">
h1 {
  font-family: $raleway;
  font-size: 1.8rem;
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
.plantListButtons {
  display: flex;
  justify-content: flex-start;
  width: 95%;
  column-gap: 5px;
  margin: 20px 0 10px 0;
}
.button-primaryDark-noBorder {
  font-size: 0.8rem;
  font-family: $inter;
  padding: 1px 10px;
}

.titleContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  text-align: center;
}
.plantPageContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: fit-content;
  width: 100%;
}
.boxContainer {
  height: 100%;
  width: 95%;
}
.mediaBox {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
.carouselContainer {
  width: 95%;
  height: 17rem;
  border-radius: 5px;
  border: 1px solid $outline;
  box-shadow: $shadowLight;
  overflow: hidden;
}
.descGuideContainer {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  margin-top: 25px;
  text-align: center;
  font-family: $raleway;
}

.descContainer {
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  justify-content: center;
}

.descContainer > h2 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  margin-bottom: 20px;
}

.careGuide {
  width: 100%;
  margin-top: 35px;
  margin-bottom: 10px;
  border: 1px solid $outline;
  border-radius: 5px;
  overflow: hidden;
}
.careGuide > div:nth-child(n + 2) {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  height: 9rem;
  overflow: hidden;
}

.careGuide > div:nth-child(n + 2) > * {
  width: 95%;
  text-align: center;
}

.careGuideHead {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 6rem;
  //background: $accentTwo;
  box-sizing: border-box;
  border: 2px solid $accentTwo;
  border-bottom: 0px;
}
.careGuideSize {
  box-sizing: border-box;
  border-left: 2px solid $accentFive;
  border-right: 2px solid $accentFive;
}
.careGuideDifficulty {
  box-sizing: border-box;
  border-left: 2px solid $accentFour;
  border-right: 2px solid $accentFour;
}
.careGuideLight {
  box-sizing: border-box;
  border-left: 2px solid $accentThree;
  border-right: 2px solid $accentThree;
}
.careGuidePet {
  box-sizing: border-box;
  border: 2px solid $accentSix;
  border-top: 0px;
  //background: $accentSix;
}

@include screen-lg() {
  .descGuideContainer {
    flex-direction: column;
    width: 100%;
    justify-content: flex-start;
    align-items: center;
  }
  .descContainer {
    width: 95%;
    text-align: center;
  }
  .descContainer > h2 {
    height: 6rem;
    margin: 0;
  }
  .careGuide {
    width: 95%;
  }
  .boxContainer {
    display: flex;
    flex-direction: row;
  }
  .mediaBox {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 70%;
  }
  .infoBox {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
  .carouselContainer {
    height: 30rem;
  }
}
</style>
