import { defineStore } from "pinia";
import { useLocalStorage } from "@vueuse/core";

export const useAuthStore = defineStore({
  id: "auth",
  state: () => ({
    auth: useLocalStorage("auth", {
      authenticated: false,
      accessToken: null,
      refreshToken: null,
      user: null,
    }),
  }),
});
