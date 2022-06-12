import api from "./api";
import plantService from "./plant.service";

const USER_API_URL = "http://localhost:8080/api/user/";

class UserService {
  getDoesUserExist(userId) {
    return api.get(USER_API_URL + "does-user-exist", {
      params: { userId },
    }).then((response) => {
      console.log(response.data)
      return response.data;
    })
  }
  getUserProfilePic(username) {
    return api.get(USER_API_URL + "get-image/" + username, {
      responseType: "blob",
    });
  }
  getUserProfileInfo(username) {
    return api.get(USER_API_URL + "profile/" + username);
  }

  hasPlantInList(userId, plantId, listType) {
    return api
      .get(USER_API_URL + "has-plant-in-list", {
        params: { userId, plantId, listType },
      })
      .then((response) => {
        return response.data;
      });
  }

  getUserPlants(username, listType) {
    return api
      .get(USER_API_URL + "user-plants", {
        params: { username, listType },
      })
      .then((response) => {
        let plants = response.data;

        const getUserPlantsInfo = async () => {
          for (let i = 0; i < plants.length; i++) {
            let thumbnailUrl = await plantService.getPlantImgById(
              plants[i].id,
              "thumbnail"
            );
            plants[i].thumbnailUrl = thumbnailUrl.data;
          }

          return plants;
        };

        return getUserPlantsInfo();
      });
  }

  removePlantFromList(userId, plantId, listType) {
    return api
      .post(USER_API_URL + "remove-plant-from-list", {
        userId,
        plantId,
        listType,
      })
      .then((response) => {
        return response.data;
      });
  }

  addPlantToList(userId, plantId, listType) {
    return api
      .post(USER_API_URL + "add-plant-to-list", {
        userId,
        plantId,
        listType,
      })
      .then((response) => {
        return response.data;
      });
  }
}

export default new UserService();
