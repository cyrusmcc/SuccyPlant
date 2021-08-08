import { createApp } from "vue";
import App from "./App.vue";
import router from "./router/router";
import store from "./store";
//import { FontAwesomeIcon } from './plugins/font-awesome';
import "./assets/styles/global.css";

createApp(App)
    .use(router)
    .use(store)
    //.component("font-awesome-icon", FontAwesomeIcon)
    .mount("#app");
