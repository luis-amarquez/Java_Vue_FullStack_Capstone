<template>
  <input type="checkbox" id="auth-modal" class="modal-toggle">
  <label for="auth-modal" class="modal cursor-pointer" id="authModal">
    <label class="modal-box relative">
      <div class="tabs">
        <button @click="modalView = 'login'" class="tab tab-lifted tab-lg" :class="((modalView ? modalView: authView) === 'login') ? 'tab-active': ''">Login</button>
        <button @click="modalView = 'signup'" class="tab tab-lifted tab-lg" :class="((modalView ? modalView: authView) === 'signup') ? 'tab-active': ''">Signup</button>
      </div>
      <div v-if="(modalView ? modalView: authView) === 'login'" class="modal-content">
        <LoginForm />
      </div>
      <div v-else-if="(modalView ? modalView: authView) === 'signup'" class="modal-content">
        <SignupForm />
      </div>
    </label>
  </label>
</template>

<script setup>
import { ref } from "vue";
import LoginForm from "../auth/LoginForm.vue";
import SignupForm from "../auth/SignupForm.vue";

defineProps(["authView"]);

let modalView = ref("");
</script>
