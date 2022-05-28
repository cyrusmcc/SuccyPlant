import { createStore } from "vuex";
import { auth } from "./auth.module";
import { settings } from "./settings.module";
import { blogs } from "./blog.module";
import { plants } from "./plant.module";

const store = createStore({
  modules: {
    auth,
    settings,
    blogs,
    plants,
  },
});

export default store;
