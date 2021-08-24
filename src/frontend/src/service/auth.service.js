import api from "./api";
import TokenService from "./token.service";

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
  login({ username, password }) {
    return api
      .post("/auth/login", {
        username,
        password
      })
      .then((response) => {
        if (response.data.accessToken) {
          TokenService.setUser(response.data);
        }

        return response.data;
      });
  }
  logout() {
      TokenService.removeUser();
  }
  register({ username, email, password }) {
      return api.post(API_URL + 'register', {
          username,
          email,
          password
      });
  }
}

export default new AuthService();