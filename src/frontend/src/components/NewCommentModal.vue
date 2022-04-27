<template>
  <input type="checkbox" id="newComment" class="modal-toggle">
  <label for="newComment" class="modal cursor-pointer">
    <label class="modal-box relative">
      <Alert :alert="alert" />
      <h3 class="text-lg font-bold">Add New Comment</h3>
      <form class="text-center" @submit.prevent="createComment">
        <textarea v-model="comment" placeholder="Type here" class="input input-bordered w-full my-2" rows="10" style="height: 200px"></textarea>
        <button type="submit" class="btn btn-wide mt-1">Post</button>
      </form>
    </label>
  </label>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useAuthStore } from "../stores/auth";
import CommentService from "../services/comment.service";
import { delay } from "../services/util";
import Alert from "./Alert.vue";

const store = useAuthStore();
const route = useRoute();

let comment = ref("");

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

const createComment = async () => {
  if (route.name === "post" && comment.value.trim()) {
    await CommentService.createNewComment(store.auth.accessToken, {
      comment: comment.value.trim(),
      postId: route.params.postId,
    })
      .then(async () => {
        // TODO: need to add the new comment to top if the current post page
        comment.value = "";
        await setAlert(
          "Successfully added the new comment. Please Refresh the page.",
          "success"
        );
      })
      .catch(async (err) => {
        await setAlert(err.response.data.message, "error");
      });
  } else {
    if (comment.value.trim()) {
      await setAlert("Comment can not be submitted empty.", "error");
    } else {
      await setAlert("There was an unexpected error.", "error");
    }
  }
};
</script>
