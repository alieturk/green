import { createApp } from 'vue'
import App from './App.vue'
import Router from "@/routes/router";
import store from './store/modules/index'
import VueQrcodeReader from "vue-qrcode-reader";
import "vue-toastification/dist/index.css";
import TOAST from "vue-toastification";
import "./assets/tailwind.css"

/**
 * Initialises a new Vue application
 * with the given dependencies.
 */
createApp(App).use(Router).use(store)
    .use(VueQrcodeReader).use(TOAST)
    .mount('#app');