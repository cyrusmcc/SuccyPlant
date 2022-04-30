import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import PlantGallery from "../views/PlantGallery.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import ForgotPassword from "../views/ForgotPassword.vue";

const Profile = () => import("../views/ProfilePage.vue");
const Settings = () => import("../views/Settings.vue");
const ResetPassword = () => import("../views/ResetPassword.vue");
const ChangeEmail = () => import("../views/ChangeEmailConfirmation.vue");
const BlogPost = () => import("../views/BlogPost.vue");
const NewBlogPost = () => import("../views/NewBlogPost.vue");
const Admin = () => import("../views/Admin.vue");
const PlantPage = () => import("../views/PlantPage.vue");

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,

    meta: {
      //hideNav: true
    },
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
    path: "/login:loginFlash(.*)",
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
    path: "/reset-password-token-:passToken(.*)",
    name: "resetPassword",
    meta: {
      hideNav: true,
    },

    component: ResetPassword,
  },
  {
    path: "/email-change-confirmation-:emailToken(.*)",
    name: "emailChangeConfirmation",
    meta: {
      hideNav: true,
    },

    component: ChangeEmail,
  },
  {
    path: "/p/:username(.*)",
    name: "profile",
    meta: {
      //hideNav: true,
    },

    component: Profile,
  },
  {
    path: "/settings",
    name: "settings",

    component: Settings,
  },
  {
    path: "/blog/:id(.*)",
    name: "blog",

    component: BlogPost,
  },
  {
    path: "/new-blog",
    name: "newBlogPost",

    component: NewBlogPost,
  },
  {
    path: "/plants",
    name: "plantGallery",
    component: PlantGallery,
  },
  {
    path: "/admin",
    name: "admin",
    component: Admin,
  },
  {
    path: "/plant/:id(.*)",
    name: "PlantPage",
    component: PlantPage,
  }, // new here
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
