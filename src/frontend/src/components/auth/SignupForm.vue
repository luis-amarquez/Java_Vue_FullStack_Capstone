<template>
  <Alert :alert="alert" />
  <ul>
    <li>Username: minimum 6 characters</li>
    <li>Password: minimum 6 characters</li>
  </ul>
  <input v-model="user.username" type="text" placeholder="Username" class="auth-input">
  <input v-model="user.email" type="email" placeholder="Email" class="auth-input">
  <input v-model="user.firstName" type="text" placeholder="First Name" class="auth-input">
  <input v-model="user.lastName" type="text" placeholder="Last Name" class="auth-input">
  <input v-model="user.password" type="password" placeholder="Password" class="auth-input">
  <input v-model="user.confirmPassword" type="password" placeholder="Confirm Password" class="auth-input">
  <button class="auth-button" @click="register">Signup</button>
</template>

<script setup>
import { ref } from "vue";
import Alert from "../Alert.vue";
import axios from "axios";
import { delay } from "../../services/util";

let user = ref({
  username: "".trim(),
  email: "",
  firstName: "".trim(),
  lastName: "".trim(),
  password: "".trim(),
  confirmPassword: "".trim(),
});

let alert = ref({
  active: false,
  type: "",
  message: "",
});

const setAlert = async (message, type) => {
  alert.value.active = true;
  alert.value.message = message;
  alert.value.type = type;
  await delay(3000).then(() => (alert.value.active = false));
};

const register = async () => {
  if (
    user.value.password === user.value.confirmPassword &&
    user.value.username.length >= 6 &&
    user.value.password.length >= 6
  ) {
    await axios
      .post("/api/user/account", {
        username: user.value.username,
        email: user.value.email,
        firstName: user.value.firstName,
        lastName: user.value.lastName,
        password: user.value.password,
        confirmPassword: user.value.confirmPassword,
      })
      .then(async () => {
        user.value = {
          username: "".trim(),
          email: "",
          firstName: "".trim(),
          lastName: "".trim(),
          password: "".trim(),
          confirmPassword: "".trim(),
        };
        await setAlert("You have been successfully registered. Please Login", "success");
      })
      .catch(async () => {
        await setAlert("Please ensure fields are valid.", "error");
      });
  } else {
    if (user.value.password !== user.value.confirmPassword) {
      await setAlert("Passwords do not match.", "error");
    } else if (user.value.username.length < 6){
      await setAlert("Username is less than 6 characters long.", "error");
    } else if (user.value.password.length < 6) {
      await setAlert("Password is less than 6 characters long.", "error");
    }
  }
};
</script>
