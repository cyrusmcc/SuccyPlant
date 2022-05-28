import api from "./api";

const PLANT_API_URL = "http://localhost:8080/api/plants/";

var pageNum = 0;
var sortedPageNum = 0;

class PlantService {
  getPlantById(id) {
    if (id) {
      return api.get(PLANT_API_URL + "get-by-id/" + id).then((response) => {
        return response.data;
      });
    }
  }
  getPlants(tags, searchTerm) {
    return api
      .get(PLANT_API_URL + "get-all", {
        headers: {
          pageNum,
        },
        params: {
          tags,
          searchTerm,
        },
      })
      .then((response) => {
        //pageNum++;
        return response.data;
      });
  }
  getPlantsAndImgs(tags, searchTerm) {
    let plants = [];

    const getPlantsInfo = async () => {
      plants = await this.getPlants(tags, searchTerm);

      for (let i = 0; i < plants.length; i++) {
        let imgUrl = await this.getPlantImgById(plants[i].id);
        plants[i].imgUrl = imgUrl.data;
      }

      return plants;
    }

    return getPlantsInfo();
  }
  getRelatedPlants(pageSize, plantId) {
    if (plantId) {
      return api
        .get(PLANT_API_URL + "get-related/" + plantId, {
          headers: {
            pageSize,
          },
        })
        .then((response) => {
          return response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }
  getRelatedPlantsAndImgs(pageSize, plantId) {
    let plants = [];

    const getPlantsInfo = async () => {
      plants = await this.getRelatedPlants(pageSize, plantId);

      for (let i = 0; i < plants.length; i++) {
        let imgUrl = await this.getPlantImgById(plants[i].id);
        plants[i].imgUrl = imgUrl.data;
      }

      return plants;
    }

    return getPlantsInfo();
  }
  getPlantImgById(id) {
    return api.get(PLANT_API_URL + "get-image/" + id, {
      responseType: "blob",
    });
  }
  getPlantAndImgById(id) {
    let plant = {
      info: {},
      imgUrl: {},
    };

    const getPlantInfo = async () => {
      plant.info = await this.getPlantById(id);
      plant.imgUrl = await this.getPlantImgById(id);
      return plant;
    }

    return getPlantInfo();
  }
  getSortedPlants(params) {
    return api
      .get(PLANT_API_URL + "plants" + "?" + params, {
        sortedPageNum,
      })
      .then((response) => {
        return response.data;
      });
  }
  getTagsByCategory(category) {
    return api
      .get(PLANT_API_URL + "get-tags-by-category/" + category)
      .then((response) => {
        return response.data;
      });
  }
}

export default new PlantService();
