<template>
  <!-- Will be displayed only when user is signed in -->
  <UserPostHeader />
  <div class="relative h-full w-full transform transition-all duration-150 ease-out">
    <div class="col-span-12 mx-auto w-full">
      <!-- only needed when user is not signed in -->
      <AnonymousPageHeader :page="anonHeader.page" :desc="anonHeader.desc" />
      <PostsContainer :data="{ page: page, category: category }" />

      <p class="dark:text-dark-500 w-full py-5 text-center text-gray-500">
        You have reached the end of the road
      </p>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import AnonymousPageHeader from "../components/AnonymousPageHeader.vue";
import UserPostHeader from "../components/UserPostHeader.vue";
import PostsContainer from "../components/PostsContainer.vue";

const route = useRoute();
const anonHeader = route.meta["anonHeader"];
const category = route.meta["category"];
const page = route.meta["page"];
</script>
