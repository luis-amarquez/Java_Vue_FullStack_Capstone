<template>
  <div class="drawer-side">
    <label for="side-drawer" class="drawer-overlay"></label>
    <ul>
      <!-- Sidebar content here -->
      <!-- links -->
      <SideNavLink link="/"><Icon>🏠</Icon>Home</SideNavLink>
      <SideNavLink link="/explore/questions">
        <Icon>❓</Icon>Questions
      </SideNavLink>
      <SideNavLink link="/explore/snippets">
        <Icon>💻</Icon>Snippets
      </SideNavLink>
      <SideNavLink link="/explore/articles">
        <Icon>📖</Icon>Articles
      </SideNavLink>
      <SideNavLink link="/explore/fun">
        <Icon>😂</Icon>Fun
      </SideNavLink>
      <div v-if="store.auth.authenticated && store.auth.user">
        <SideNavLink link="/account/dashboard">
          <Icon>📊</Icon>Dashboard
        </SideNavLink>
        <SideNavLink link="/messages">
          <Icon>💬</Icon>Messages
        </SideNavLink>
        <SideNavLink :link="{ name: 'profile', params: { username: store.auth.user.username } }" >
          <Icon>👤</Icon>Profile
        </SideNavLink>
      </div>
    </ul>
  </div>
</template>

<script setup>
import { useAuthStore } from "../../stores/auth";
import SideNavLink from "./SideNavLink.vue";
import Icon from "./Icon.vue";

const store = useAuthStore();
</script>
