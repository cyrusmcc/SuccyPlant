import { createStore } from "vuex";
import { auth } from "./auth.module";
import { comp } from "./comp.module";

const store = createStore({
  modules: {
    auth,
    comp,
  },
});

export default store;