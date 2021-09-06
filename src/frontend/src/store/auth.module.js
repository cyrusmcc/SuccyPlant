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
    resetPasswordRequest({ commit }, email) {      
      return AuthService.resetPasswordRequest(email).then(
        response => {
          commit('resetPasswordRequestSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('resetPasswordRequestSuccessFailure');
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
    changeEmail({ commit }, values) {      
      return AuthService.changeEmail(user, values).then(
        response => {
          commit('changeEmailSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('changeEmailFailure');
          return Promise.reject(error);
        }
      );
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
    handlePasswordResetSuccess(state) {
      state.status.loggedIn = false;
    },
    handlePasswordResetFailure(state) {
      state.status.loggedIn = false;
    },
    changeEmailSuccess(state) {
      state.status.loggedIn = true;
    },
    changeEmailFailure(state) {
      state.status.loggedIn = true;
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