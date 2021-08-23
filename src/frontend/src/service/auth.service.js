import api from "./api";
import TokenService from "./token.service";

const API_URL = 'http://127.0.0.1:8080/api/auth/';

class AuthService {
    async login({ username, password }) {
      const response = await api
        .post(API_URL + 'login', {
          username,
          password
        });
      if (response.data.accessToken) {
        TokenService.setUser(response.data);
      }
      return response.data;
    }

    logout() {
      TokenService.removeUser();
    }

    register({ username, email, password }) {
        return axios.post(API_URL + 'register', {
            username,
            email,
            password
        });
    }
}

export default new AuthService();