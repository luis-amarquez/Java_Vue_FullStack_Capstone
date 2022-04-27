<template>
  <div v-if="store.auth.authenticated" class="relative flex flex-col items-center justify-between">
    <div class="flex w-full items-start">
      <div class="flex flex w-full items-center px-6 py-5">
        <a href="/github80" class="block w-10 h-10 rounded-full flex-shrink-0 click-attached">
          <img class="w-10 h-10 bg-gray-100 border-0 border-white rounded-full" src="https://cdn.devdojo.com/users/April2022/github801.jpg">
        </a>
        <label for="newPostForm" class="text-sm text-gray-400 ml-3 w-full bg-gray-50 transition-all duration-300 ease-out hover:text-gray-700 cursor-pointer px-4 py-2.5 rounded-full ">
          Write New Post...
        </label>
      </div>
    </div>
    <div class="flex items-center w-full justify-between relative z-30 border-t border-gray-100 bg-white">
      <div class="flex w-full items-center justify-center">
        <PostType link="/" icon="✍️">Post</PostType>
        <PostType link="/explore/questions" icon="❓">Question</PostType>
        <PostType link="/explore/snippets" icon="💻">Snippet</PostType>
        <PostType link="/explore/articles" icon="📖">Article</PostType>
        <PostType link="/explore/fun" icon="😂">Fun</PostType>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "../stores/auth";
import PostType from "../components/PostType.vue";

const store = useAuthStore();
</script>
