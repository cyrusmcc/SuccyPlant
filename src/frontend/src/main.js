import { createApp } from "vue";
import App from "./App.vue";
import router from "./router/router";
import store from "./store";
import "./assets/styles/global.css";
import setupInterceptors from './service/setupInterceptors';

setupInterceptors(store);

createApp(App)
    .use(router)
    .use(store)
    .mount("#app");
