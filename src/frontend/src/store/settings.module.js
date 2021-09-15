import SettingsService from '../service/settings.service';
import TokenService from '../service/token.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };
  
export const settings = {
  namespaced: true,
  state: initialState,
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
    requestSettingPasswordReset({ commit }, values) {
      return SettingsService.requestSettingPasswordReset(user, values, values).then(
        response => {
          commit('requestSettingPasswordResetSuccess');
          TokenService.removeUser(user);
          $router.push("/login");
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
      return SettingsService.changeEmailRequest(user, values, values).then(
        response => {
          commit('changeEmailRequestSuccess');
          TokenService.removeUser(user);
          $router.push("/login");
          return Promise.resolve(response.data);
        },
        error => {
          commit('changeEmailRequestFailure');
          return Promise.reject(error);
        }
      );
    },
    handleEmailChange({ commit }, token) {
      return SettingsService.handleEmailChange(token).then(
        response => {
          commit('handleEmailChangeSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('handleEmailChangeFailure');
          return Promise.reject(error);
        }
      )
    },
  },
  mutations: {
    requestLostPasswordResetSuccess() {
    },
    requestLostPasswordResetFailure() {
    },
    requestSettingPasswordResetSuccess(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    requestSettingPasswordResetFailure() {
    },
    handlePasswordResetSuccess(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    handlePasswordResetFailure(state) {
      state.status.loggedIn = true;
    },
    changeEmailRequestSuccess(state) {
      state.status.loggedIn = true;
    },
    changeEmailRequestFailure(state) {
      state.status.loggedIn = true;
    },
    handleEmailChangeSuccess(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    handleEmailChangeFailure() {
    },
  }
};