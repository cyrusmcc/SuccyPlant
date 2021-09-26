import SettingsService from '../service/settings.service';

export const settings = {
  namespaced: true,
  actions: {
    requestLostPasswordReset({ commit }, email) {      
      return SettingsService.requestLostPasswordReset(email).then(
        response => {
          commit('requestLostPasswordResetSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          
          commit('requestLostPasswordResetFailure');
          return Promise.reject(error);
        }
      );
    },
    requestSettingPasswordReset({ commit, rootState, dispatch }, values) { 
      return SettingsService.requestSettingPasswordReset(values, values).then(
        response => {
          dispatch('auth/logout', rootState.auth.user, { root : true });
          commit('requestSettingPasswordResetSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          
          commit('requestSettingPasswordResetFailure');
          return Promise.reject(error);
        }
      );
    },
    handlePasswordReset({ commit }, values) {
      return SettingsService.handlePasswordReset(values, values).then(
        response => {
          commit('handlePasswordResetSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('handlePasswordResetFailure');
          return Promise.reject(error);
        }
      )
    },
    changeEmailRequest({ commit }, values) {  
      return SettingsService.changeEmailRequest(values, values).then(
        response => {
          commit('changeEmailRequestSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('changeEmailRequestFailure');
          return Promise.reject(error);
        }
      );
    },
    handleEmailChange({ commit, rootState, dispatch }, token) {
      return SettingsService.handleEmailChange(token).then(
        response => {
          dispatch('auth/logout', rootState.auth.user, { root : true });
          commit('handleEmailChangeSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('handleEmailChangeFailure');
          return Promise.reject(error);
        }
      )
    },
    handleProfilePictureUpload({ commit }, values) {
      return SettingsService.handleProfilePictureUpload(values).then(
        response => {
          commit('handleProfilePictureUploadSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('handleProfilePictureUploadFailure');
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    requestLostPasswordResetSuccess() {
    },
    requestLostPasswordResetFailure() {
    },
    requestSettingPasswordResetSuccess() {
    },
    requestSettingPasswordResetFailure() {
    },
    handlePasswordResetSuccess() {
    },
    handlePasswordResetFailure() {
    },
    changeEmailRequestSuccess() {
    },
    changeEmailRequestFailure() {
    },
    handleEmailChangeSuccess() {
    },
    handleEmailChangeFailure() {
    },
    handleProfilePictureUploadSuccess() {
    },
    handleProfilePictureUploadFailure() {
    },
  }
};