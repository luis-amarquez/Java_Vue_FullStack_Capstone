import { createRouter, createWebHistory } from "vue-router";
import PostsView from "../views/PostsView.vue";
import ProfileView from "../views/ProfileView.vue";
import PostView from "../views/PostView.vue";
import DashboardView from "../views/DashboardView.vue";
import MessagesView from "../views/MessagesView.vue";
import MessageView from "../views/MessageView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: PostsView,
      meta: {
        public: true,
        page: "home",
        anonHeader: {
          page: "Home",
          desc: "Developer Posts and Tutorials",
        },
      },
    },
    {
      path: "/explore/articles",
      name: "exploreArticles",
      component: PostsView,
      meta: {
        public: true,
        page: "explore",
        category: "article",
        anonHeader: {
          page: "Articles",
          desc: "Developer Articles and Tutorials",
        },
      },
    },
    {
      path: "/explore/fun",
      name: "exploreFun",
      component: PostsView,
      meta: {
        public: true,
        page: "explore",
        category: "fun",
        anonHeader: {
          page: "Fun",
          desc: "Memes, comedy, and everything in between.",
        },
      },
    },
    {
      path: "/explore/questions",
      name: "exploreQuestions",
      component: PostsView,
      meta: {
        public: true,
        page: "explore",
        category: "question",
        anonHeader: {
          page: "Questions",
          desc: "Coding questions for beginners to advanced.",
        },
      },
    },
    {
      path: "/explore/snippets",
      name: "exploreSnippets",
      component: PostsView,
      meta: {
        public: true,
        page: "explore",
        category: "snippet",
        anonHeader: {
          page: "Snippets",
          desc: "Code snippets, samples, and more.",
        },
      },
    },
    {
      path: "/account/dashboard",
      name: "dashboard",
      component: DashboardView,
      meta: {
        public: false,
      },
    },
    {
      path: "/messages",
      name: "messages",
      component: MessagesView,
      meta: {
        public: false,
      },
    },
    {
      path: "/messages/:id",
      name: "message",
      component: MessageView,
      meta: {
        public: false,
      },
    },
    {
      path: "/:username",
      name: "profile",
      component: ProfileView,
      meta: {
        page: "user",
        public: true,
      },
    },
    {
      path: "/:username/status/:postId",
      name: "post",
      component: PostView,
      meta: {
        public: true,
      },
    },
  ],
});

router.beforeEach((to, from, next) => {
  const publicPage = to.meta["public"] || false;
  const authData = JSON.parse(localStorage.getItem("auth"));
  if (!publicPage && !authData.authenticated) {
    next("/");
  } else {
    next();
  }
});

export default router;
