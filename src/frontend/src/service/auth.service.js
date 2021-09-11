import api from "./api";
import TokenService from "./token.service";

const API_URL = 'http://localhost:8080/api/auth/';
const API_URL_SETTING = 'http://localhost:8080/api/setting/'

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
  requestLostPasswordReset({ email }) {
    return api
      .post(API_URL_SETTING + 'requestLostPasswordReset', {
        email
      });

  }
  requestSettingPasswordReset({ id }, { currentPassword }, { newPassword }) {
    return api
      .post(API_URL_SETTING + 'requestSettingPasswordReset', {
        id,
        currentPassword,
        newPassword
      });
  }
  handlePasswordReset({ password }, { token }) {
    return api
    .post(API_URL_SETTING + 'handlePasswordReset', {
      token,
      password
    });
  }
  changeEmailRequest({ id }, { email }, { password }) {
    return api
      .post(API_URL_SETTING + 'changeEmailRequest', {
        id,
        email,
        password
      });
  }
  handleEmailChange(token) {
    return api
      .post(API_URL_SETTING + 'handleEmailChange', {
        token
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