<template>
  <input type="checkbox" id="newPostForm" class="modal-toggle">
  <label for="newPostForm" class="modal cursor-pointer">
    <label class="modal-box relative">
      <Alert :alert="alert" />
      <h3 class="text-lg font-bold">Create New post</h3>
      <form class="text-center" @submit.prevent="createPost">
        <input class="input input-bordered w-full my-2" type="text" placeholder="Title (Optional)" v-model="title">
        <textarea v-model="post" placeholder="Type here" class="input input-bordered w-full my-2" rows="10" style="height: 200px"></textarea>
        <button type="submit" class="btn btn-wide mt-1">Post</button>
      </form>
    </label>
  </label>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useAuthStore } from "../stores/auth";
import PostService from "../services/post.service";

import { delay } from "../services/util";
import Alert from "./Alert.vue";

const store = useAuthStore();
const route = useRoute();

let title = ref("");
let post = ref("");
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

const createPost = async () => {
  if (post.value.trim() && ["home", "explore"].includes(route.meta["page"])) {
    let category;
    if (route.meta["page"] === "home") {
      category = "post";
    } else {
      category = route.meta["category"];
    }

    await PostService.createNewPost(store.auth.accessToken, {
      title: title.value,
      content: post.value,
      category: category,
    })
      .then(async (res) => {
        // TODO: need to add the new post to the top of the current page's posts
        title.value = "";
        post.value = "";
        await setAlert("Successfully created the new post.", "success");
      })
      .catch(async (err) => {
        await setAlert(err.response.data.message, "error");
      });
  } else {
    if (!post.value.trim()) {
      await setAlert("Post body can not be empty.", "error");
    } else {
      await setAlert("There was an unexpected error.", "error");
    }
  }
};
</script>
