import { createStore } from "vuex";
import { auth } from "./auth.module";
import { settings } from "./settings.module";
import { blogs } from "./blog.module";
import { plants } from "./plant.module";
import { commentReply } from "./commentReply.module";

const store = createStore({
  modules: {
    auth,
    settings,
    blogs,
    plants,
    commentReply
  },
});

export default store;
