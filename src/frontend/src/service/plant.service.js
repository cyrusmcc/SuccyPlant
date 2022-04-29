import api from "./api";

const PLANT_API_URL = "http://localhost:8080/api/plants/";

var pageNum = 0;
var sortedPageNum = 0;

class PlantService {
  getPlantById(id) {
    return api.get(PLANT_API_URL + "get-by-id/" + id).then((response) => {
      console.log(response.data);
      return response.data;
    });
  }
  getPlants(tags, searchTerm) {
    console.log(tags);
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
