import api from "./api";
import TokenService from "./token.service";

const AUTH_API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
  login({ username, password }) {
    return api
      .post(AUTH_API_URL + 'login', {
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
  logout({ id }) {
    localStorage.removeItem('user');
    return api
      .post(AUTH_API_URL + 'logout', {
        id
      });
  }
  register({ username, email, password }) {
    return api
      .post(AUTH_API_URL + 'register', {
        username,
        email,
        password
      });
  }
}

export default new AuthService();