<template>
  <div class="posts-container">
    <PostCard v-for="post in posts" :key="post.id" :post="post" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import PostService from "../services/post.service";
import UserService from "../services/user.service";
import PostCard from "./PostCard.vue";

const props = defineProps(["data"]);
let apiData;

let posts = ref([]);
let currentPage = ref(0);
let lastPage = ref(true);
let loading = true;

// function to eliminate repetitive code to set data
function setData(data) {
  posts.value.push(...data.posts);
  currentPage.value = data.currentPage + 1;
  lastPage.value = data.lastPage;
}

// function to fetch posts from user page
async function fetchUserPosts() {
  await UserService.getUsersPosts(props.data.username, currentPage.value).then(
    (response) => {
      setData(response);
    }
  );
}

// function to fetch posts for home
async function fetchHomePosts() {
  await PostService.getHomePosts(currentPage.value).then((response) => {
    setData(response);
  });
}

// function to fetch posts for explore categories
async function fetchExplorePosts() {
  await PostService.getCategoryPosts(
    currentPage.value,
    props.data.category
  ).then((response) => {
    setData(response);
  });
}

onMounted(async () => {
  // depending on what page you are on, assign the default fetch method
  switch (props.data.page) {
    case "home":
      apiData = fetchHomePosts;
      break;
    case "explore":
      apiData = fetchExplorePosts;
      break;
    case "user":
      apiData = fetchUserPosts;
      break;
  }

  // fetch initial data
  await apiData();
  loading = false;

  // setup to fetch more data when user is almost near the bottom of the page
  const contentContainer = document.getElementById("content");
  contentContainer.addEventListener("scroll", async () => {
    const offset =
      contentContainer.scrollHeight -
      (contentContainer.scrollTop + contentContainer.offsetHeight);
    if (offset < 1000 && !lastPage.value && !loading) {
      loading = true;
      await apiData();
      loading = false;
    }
  });
});
</script>
