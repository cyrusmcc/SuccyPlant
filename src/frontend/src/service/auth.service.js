import api from "./api";
import TokenService from "./token.service";

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
  login({ username, password }) {
    return api
      .post(API_URL + 'login', {
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
      .post(API_URL + 'logout', {
        id
      });
  }
  resetPasswordRequest({ email }) {
    return api
      .post(API_URL + 'resetPasswordRequest', {
        email
      });
  }
  handlePasswordReset({ password }, { token }) {
    return api
    .post(API_URL + 'handlePasswordReset', {
      token,
      password
    });
  }
  changeEmail({ id }, { email }) {
    return api
      .post(API_URL + 'changeEmail', {
        id,
        email
      });
  }
  register({ username, email, password }) {
    return api
      .post(API_URL + 'register', {
        username,
        email,
        password
      });
  }
}

export default new AuthService();