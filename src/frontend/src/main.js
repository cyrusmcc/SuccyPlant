import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { FontAwesomeIcon } from './plugins/font-awesome';
import axios from "axios";
import "./assets/styles/global.css";

window.axios = axios;
axios.defaults.baseURL = "http://127.0.0.1:8080";

createApp(App)
    .use(router)
    .use(store)
    .component("font-awesome-icon", FontAwesomeIcon)
    .mount("#app");
