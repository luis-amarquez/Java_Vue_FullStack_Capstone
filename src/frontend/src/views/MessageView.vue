<template>
  <div v-for="message in messages" class="relative pl-6 pr-12 pt-3 pb-6  theme-bg" style="border-top-width: 1px">
    <div class="flex">
      <div class="flex-shrink-0 select-none hidden w-12 h-12 mr-5 sm:flex">
        <div><img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png" class="w-12 h-12 min-w-full rounded-full"></div>
      </div>
      <div class="flex flex-col flex-1 w-auto min-w-0 ml-0">
        <div class="relative flex items-center text-sm select-none mb-1">
          <p class="font-bold text-gray-800">
            {{
              message.sender.id === store.auth.user.id
                ? message.sender.fullName
                : message.sender.fullName
            }}
          </p>
          <span class="text-gray-400 ">・</span>
          <p class="text-gray-500 text-xs">Apr 14th, 2022 03:29 AM</p>
        </div>
        <div class="w-full mb-5 overflow-hidden prose answer question-block md:prose-md">
          <p>{{ message.message }}</p>
        </div>
      </div>
    </div>
  </div>

  <div class="w-full pl-5 pr-3 lg:pr-6 py-6 bg-white border-t border-gray-100">
    <div class="relative flex w-full space-x-3">
      <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png" class="w-11 h-11 rounded-full">

      <form class="w-full text-right" @submit.prevent="submitMessage">
        <textarea v-model="message" placeholder="Type here" class="input input-bordered w-full my-2" rows="10" style="height: 100px"></textarea>
        <button type="submit" class="btn btn-primary mt-1">Send</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import { useAuthStore } from "../stores/auth";

const store = useAuthStore();
const route = useRoute();

let messages = ref([]);
let message = ref("");

const fetchMessages = async () => {
  await axios
    .get(`/api/message/${route.params.id}/messages`, {
      headers: {
        Authorization: `Bearer ${store.auth.accessToken}`,
      },
      params: {
        page: 0,
        count: 100,
      },
    })
    .then(async (response) => {
      messages.value.push(...response.data.content);
    });
};

const submitMessage = async () => {
  await axios
    .post(
      "/api/message/",
      {
        roomId: route.params.id,
        message: message.value,
      },
      {
        headers: {
          Authorization: `Bearer ${store.auth.accessToken}`,
        },
      }
    )
    .then((response) => {
      message.value = "";
      messages.value.push(response.data);
    });
};

onMounted(async () => {
  await fetchMessages();
});
</script>
