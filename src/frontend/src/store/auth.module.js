import AuthService from '../service/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          commit('loginSuccess', user);
          return Promise.resolve(user);
        },
        error => {
          commit('loginFailure');
          return Promise.reject(error);
        }
      );
    },
    logout({ commit }, user) {
      commit('logout');
      AuthService.logout(user);
    },
    requestLostPasswordReset({ commit }, email) {      
      return AuthService.requestLostPasswordReset(email).then(
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
      return AuthService.requestSettingPasswordReset(user, values, values).then(
        response => {
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
      return AuthService.handlePasswordReset(values, values).then(
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
      console.log(user.username);
      return AuthService.changeEmailRequest(user, values, values).then(
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
    handleEmailChange({ commit }, token) {
      return AuthService.handleEmailChange(token).then(
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
    register({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('registerFailure');
          return Promise.reject(error);
        }
      );
    },
    refreshToken({ commit }, accessToken) {
      commit('refreshToken', accessToken);
    }
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
    },
    logoutFailure() {
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
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
    },
    handlePasswordResetFailure(state) {
      state.status.loggedIn = false;
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
    registerFailure(state) {
      state.status.loggedIn = false;
    },
    refreshToken(state, accessToken) {
      state.status.loggedIn = true;
      state.user = { ...state.user, accessToken: accessToken }
    },

  }
};