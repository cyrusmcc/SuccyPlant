import { createStore } from "vuex";
import { auth } from "./auth.module";
import { settings } from "./settings.module"

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };
  

const store = createStore({
  state: initialState,
  modules: {
    auth,
    settings
  },
});

export default store;