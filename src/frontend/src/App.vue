<script setup>
import { useRoute } from "vue-router";
import { useAuthStore } from "./stores/auth";
import TopNav from "./components/base/TopNav.vue";
import SideNav from "./components/base/SideNav.vue";
import NewPostModal from "./components/NewPostModal.vue";
import NewCommentModal from "./components/NewCommentModal.vue";

const route = useRoute();
const store = useAuthStore();
</script>

<template>
  <TopNav />
  <div class="main-container">
    <div class="container-layout drawer-mobile drawer">
      <input id="side-drawer" type="checkbox" class="drawer-toggle" />
      <div class="main-content drawer-content" id="content">
        <!-- Page content here -->
        <div>
          <RouterView :key="route.path" />
        </div>
      </div>
      <SideNav />
    </div>
  </div>

  <NewPostModal v-if="store.auth.authenticated" />
  <NewCommentModal v-if="store.auth.authenticated" />
</template>
