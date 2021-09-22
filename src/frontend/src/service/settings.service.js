import api from "./api";

const SETTING_API_URL = 'http://localhost:8080/api/setting/'

class SettingsService {
  requestLostPasswordReset({ email }) {
    return api
      .post(SETTING_API_URL + 'requestLostPasswordReset', {
        email
      });
  }
  handlePasswordReset({ password }, { token }) {
    return api
    .post(SETTING_API_URL + 'handlePasswordReset', {
      token,
      password
    });
  }
  requestSettingPasswordReset({ id }, { currentPassword }, { newPassword }) {
    console.log(id + " " + currentPassword + " " + newPassword);
    return api
      .post(SETTING_API_URL + 'requestSettingPasswordReset', {
        id,
        currentPassword,
        newPassword
      });
  }
  changeEmailRequest({ id }, { email }, { password }) {
    return api
      .post(SETTING_API_URL + 'changeEmailRequest', {
        id,
        email,
        password
      });
  }
  handleEmailChange(token) {
    return api
      .post(SETTING_API_URL + 'handleEmailChange', {
        token
      });
  }
  handleProfilePictureUpload({file}) {
    const formData = new FormData();
    formData.append('file', file[0]);
    return api
    .post(SETTING_API_URL + 'handleProfilePictureUpload', formData);
  }
}

export default new SettingsService();