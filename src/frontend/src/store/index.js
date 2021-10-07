import { createStore } from "vuex";
import { auth } from "./auth.module";
import { settings } from "./settings.module";
import { blogs } from "./blog.module";

const store = createStore({
  modules: {
    auth,
    settings,
    blogs,
  },
});

export default store;
