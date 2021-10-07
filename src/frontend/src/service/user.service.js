import api from "./api";

const USER_API_URL = "http://localhost:8080/api/user/";

class UserService {
  getUserProfilePic(username) {
    return api.get(USER_API_URL + "profPic/" + username, {
      responseType: "blob",
    });
  }

  getUserProfileInfo(username) {
    return api.get(USER_API_URL + "profile/" + username);
  }
}

export default new UserService();
