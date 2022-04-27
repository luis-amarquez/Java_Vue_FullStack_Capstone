<template>
  <header class="nav-top bottom-divider lg:px-5">
    <div class="nav-top-content">
      <div class="nav-top-left">
        <RouterLink to="/">DevByte </RouterLink>
      </div>

      <div class="nav-top-middle-spacer"></div>

      <div class="nav-top-right">
        <div v-if="!store.auth.authenticated" class="nav-top-auth-buttons">
          <div>
            <label @click="authView = 'login'" for="auth-modal" class="auth-nav-button" id="login">Login</label>
          </div>
          <div>
            <label @click="authView = 'signup'" for="auth-modal" class="auth-nav-button" id="signup">Signup</label>
          </div>
        </div>

        <div v-else class="flex-none">
          <div class="dropdown-end dropdown text-black">
            <label tabindex="0" class="avatar btn btn-ghost btn-circle">
              <div class="w-10 rounded-full">
                <img
                  src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png"
                />
              </div>
            </label>
            <ul
              tabindex="0"
              class="dropdown-content menu rounded-box menu-compact mt-3 w-52 bg-base-100 p-2 shadow"
            >
              <li><RouterLink to="/">Customers</RouterLink></li>
              <li><RouterLink to="/add">Add Customers</RouterLink></li>
              <li><a @click="logout">Logout</a></li>
            </ul>
          </div>
        </div>

        <div class="nav-top-hamburger">
          <label for="side-drawer" :class="toggle ? 'swap swap-rotate swap-active' : 'swap swap-rotate'" @click="toggle = !toggle">
            <input type="checkbox" />
            <MenuHamburger class="swap-off" />
            <CloseMenu class="swap-on" />
          </label>
        </div>
      </div>
    </div>
  </header>
  <AuthModal v-if="!store.auth.authenticated" :auth-view="authView" />
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import router from "../../router";
import { useAuthStore } from "../../stores/auth";
import MenuHamburger from "../icons/MenuHamburger.vue";
import CloseMenu from "../icons/CloseMenu.vue";
import AuthModal from "../auth/AuthModal.vue";

const route = useRoute();

let store = useAuthStore();
let toggle = ref(false);
let authView = ref("");

function logout() {
  if (!route.meta["public"]) {
    router.push({ name: "home" });
  }
  store.auth.authenticated = false;
  store.auth.accessToken = null;
  store.auth.refreshToken = null;
  store.auth.user = null;
}
</script>
