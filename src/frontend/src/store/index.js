import { createStore } from "vuex";
import { auth } from "./auth.module";
import { settings } from "./settings.module";
import { blogs } from "./blog.module";
import { gallery } from "./gallery.module";

const store = createStore({
  modules: {
    auth,
    settings,
    blogs,
    gallery,
  },
});

export default store;
