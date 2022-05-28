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
  getPlantImageById(id) {
    return api.get(PLANT_API_URL + "get-image/" + id, {
      responseType: "blob",
    });
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
