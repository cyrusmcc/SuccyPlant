import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/Login.vue"
import Register from "../views/Register.vue"
import ForgotPassword from "../views/ForgotPassword.vue";


const Profile = () => import("../views/ProfilePage.vue");
const Settings = () => import("../views/Settings.vue");
const ResetPassword = () => import("../views/ResetPassword.vue");

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
    path: "/forgot-password",
    name: "forgotPassword",
    meta: {
      hideNav: true,
    },

    component: ForgotPassword,
  },  
  {
    path: '/reset-password-token-:token(.*)',
    name: "resetPassword",
    meta: {
      hideNav: true,
    },

    component: ResetPassword,
  },
  {
    path: "/profile",
    name: "profile",
    meta: {
      hideNav: true,
    },
    
    component: Profile,
  },
  {
    path: "/settings",
    name: "settings",

    component: Settings,
  },  // new here
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;