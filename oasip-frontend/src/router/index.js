import { createRouter, createWebHistory } from "vue-router";
import Contents from "../views/Contents.vue";
import CategoryContent from "../views/CategoryContent.vue";

const history = createWebHistory(import.meta.env.VITE_BASE_URL);
const routes = [{
        path: "/",
        name: "scheduleContents",
        component: Contents,
    },
    {
        path: "/category",
        name: "categoryContents",
        component: CategoryContent,
    },
];

const router = createRouter({
    history,
    routes,
});

export default router;