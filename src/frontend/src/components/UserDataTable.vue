<template>
  <div v-if="rows" class="overflow-x-auto w-full">
    <h1 class="text-2xl text-center">{{ type }}</h1>
    <table class="table w-full" style="table-layout: fixed">
      <!-- head -->
      <thead>
        <tr>
          <th style="width: 300px">Title</th>
          <th>Category</th>
          <th>Likes</th>
          <th>Created</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <!-- row 1 -->
        <tr v-for="row in rows" :key="row.id" @click="redirect(row.user.username, row.id)">
          <td style="width: 300px; padding-right: 4px">
            <div style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden;">
              {{ row.title }}
            </div>
          </td>
          <td>{{ row.category }}</td>
          <td>{{ row.likes }}</td>
          <td>{{ dateTimeDiff(row.created) }}</td>
          <td>
            <button class="btn btn-error btn-xs">Delete</button>
          </td>
        </tr>
      </tbody>
      <!-- foot -->
      <tfoot>
        <tr>
          <th>Title</th>
          <th>Category</th>
          <th>Likes</th>
          <th>Created</th>
          <th></th>
        </tr>
      </tfoot>
    </table>
    <div class="btn-group my-4 justify-center">
      <button class="btn">1</button>
      <button class="btn">2</button>
      <button class="btn btn-disabled">...</button>
      <button class="btn">99</button>
      <button class="btn">100</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import router from "../router";
import { useAuthStore } from "../stores/auth";
import UserService from "../services/user.service";
import { dateTimeDiff } from "../services/util";

const store = useAuthStore();
const props = defineProps(["type"]);
let apiData;

let rows = ref([]);
let currentPage = ref(0);
let lastPage = ref(true);
let totalPages = ref(1);

function redirect(username, postId) {
  router.push({ name: "post", params: { username: username, postId: postId } });
}

function setData(data) {
  currentPage.value = data.currentPage + 1;
  lastPage.value = data.lastPage;
  totalPages.value = data.totalPages;
}

async function fetchPosts() {
  await UserService.getUserPosts(
    store.auth.accessToken,
    currentPage.value
  ).then((data) => {
    rows.value.push(...data.posts);
    setData(data);
  });
}

async function fetchComments() {
  await UserService.getUserComments(
    store.auth.accessToken,
    currentPage.value
  ).then((data) => {
    rows.value.push(...data.comments);
    setData(data);
  });
}

onMounted(async () => {
  switch (props.type) {
    case "Posts":
      apiData = fetchPosts;
      break;
    case "Comments":
      apiData = fetchComments;
      break;
  }

  await apiData();
});
</script>

<style>
tr:hover {
  background-color: lightgray;
  cursor: pointer;
}
tr:hover td {
  background-color: lightgray;
}
</style>
