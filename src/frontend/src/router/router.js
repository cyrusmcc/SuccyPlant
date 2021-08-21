import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "../components/Login.vue"
import Register from "../components/Register.vue"

const Profile = () => import("../components/ProfilePage.vue");

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import("../views/About.vue"),
  },
  {
    path: "/login",
    name: "Login",
    meta: {
      hideNav: true,
    },

    component: Login,
  },
  {
    path: "/register",
    name: "register",
    meta: {
      hideNav: true,
    },

    component: Register,
  },
  {
    path: "/profile",
    name: "profile",

    component: Profile,
  },
  // new here
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;