import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import axios from "axios";
import vuex from "vuex";
import "./assets/styles/global.css";

window.axios = axios;
axios.defaults.baseURL = "http://127.0.0.1:8080";

createApp(App).use(router).use(vuex).mount("#app");
