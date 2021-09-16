import { createStore } from "vuex";
import { auth } from "./auth.module";
import { settings } from "./settings.module"

const store = createStore({
  modules: {
    auth,
    settings
  },
});

export default store;