<template>
  <div class="alert alert-error my-2 shadow-lg" v-if="error.failed">
    <div>
      <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
      <span>{{ error.message }}</span>
    </div>
  </div>
  <form id="loginForm" @submit.prevent="submit">
    <input type="text" placeholder="Username" v-model="username" class="auth-input">
    <input type="password" placeholder="Password" v-model="password" class="auth-input">
    <button type="submit" class="auth-button">Login</button>
  </form>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useAuthStore } from "../../stores/auth";

import AuthService from "../../services/auth.service";
import UserService from "../../services/user.service";

import { delay } from "../../services/util";

let store = useAuthStore();

const username = ref("");
const password = ref("");
let error = ref({
  failed: false,
  message: "Incorrect Username/Password",
});

const submit = async () => {
  await axios
    .post("/api/login", {
      username: username.value,
      password: password.value,
    })
    .then(async (res) => {
      await UserService.getUserAccountInfo(res.data.accessToken).then(
        (user) => {
          store.auth.user = user;
        }
      );

      store.auth.authenticated = true;
      store.auth.accessToken = res.data.accessToken;
      store.auth.refreshToken = res.data.refreshToken;
    })
    .catch((err) => {
      if (err.response.status === 403) {
        error.value.failed = true;
        delay(3000).then(() => (error.value.failed = false));
      }
    });
};
// TODO: finish implementing authenticated services. Login successfully works
</script>
