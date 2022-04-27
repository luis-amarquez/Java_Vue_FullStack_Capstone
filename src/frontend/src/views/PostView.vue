<template>
  <div v-if="post" class="relative w-full transition-all ease-out duration-150 transform h-full opacity-100" style="border-width: 1px 1px 1px 0">
    <div class="w-full mx-auto">
      <div class="w-full pb-3">
        <h1 v-if="post.title" class="text-lg font-bold text-gray-800 lg:text-xl mt-5 px-6 xl:text-2xl prata dark:text-dark-100 sm:leading-none">
          {{ post.title }}
        </h1>
      </div>
      <div>
        <div class="flex flex-col divide-y divide-y-100" id="question_container">
          <div class="relative w-full h-full">
            <div class="relative pl-6 pr-12 pt-3 pb-6  theme-bg">
              <div class="flex">
                <div class="flex-shrink-0 select-none hidden w-12 h-12 mr-5 sm:flex">
                  <RouterLink :to="{ name : 'profile', params: {username: post.user.username}}"><img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png" class="w-12 h-12 min-w-full rounded-full"></RouterLink>
                </div>
                <div class="flex flex-col flex-1 w-auto min-w-0 ml-0">
                  <div class="relative flex items-center text-sm select-none mb-1">
                    <RouterLink :to="{ name : 'profile', params: {username: post.user.username}}" class="font-bold text-gray-800 hover:underline">{{ post.user.username }}</RouterLink>
                    <span class="text-gray-400 ">・</span>
                    <p class="text-gray-500 text-xs">{{ date }}</p>
                  </div>
                  <div class="md:prose-md prose mb-5 w-full overflow-hidden">
                    <p>{{ post.content }}</p>
                  </div>
                </div>
              </div>
            </div>

            <div class="w-full pl-5 pr-3 lg:pr-6 py-6 bg-white border-t border-gray-100">
              <div class="relative flex w-full space-x-3">
                <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png" class="w-11 h-11 rounded-full">
                <label for="newComment" class="flex items-center w-full h-12 px-5 text-gray-500 bg-gray-100 rounded-full cursor-pointer">Write a Reply</label>
              </div>
            </div>


            <div v-for="comment in comments" class="relative pl-6 pr-12 pt-3 pb-6  theme-bg" style="border-top-width: 1px">
              <div class="flex">
                <div class="flex-shrink-0 select-none hidden w-12 h-12 mr-5 sm:flex">
                  <RouterLink :to="{ name : 'profile', params: {username: comment.user.username}}"><img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png" class="w-12 h-12 min-w-full rounded-full"></RouterLink>
                </div>
                <div class="flex flex-col flex-1 w-auto min-w-0 ml-0">
                  <div class="relative flex items-center text-sm select-none mb-1">
                    <RouterLink :to="{ name : 'profile', params: {username: comment.user.username}}" class="font-bold text-gray-800 hover:underline">{{ comment.user.username }}</RouterLink>
                    <span class="text-gray-400 ">・</span>
                    <p class="text-gray-500 text-xs">Apr 14th, 2022 03:29 AM</p>
                  </div>
                  <div class="w-full mb-5 overflow-hidden prose answer question-block md:prose-md">
                    <p>{{ comment.comment }}</p>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import PostService from "../services/post.service";
import CommentService from "../services/comment.service";
import { dateTimeDiff } from "../services/util";

const route = useRoute();
const username = route.params.username;
const postId = route.params.postId;

let post = ref();
let exists = ref(null);
let comments = ref([]);
let currentPage = ref(0);
let lastPage = ref(true);
let date = ref();

const fetchComments = async () => {
  await CommentService.getPostComments(postId, currentPage.value).then(
    (data) => {
      comments.value.push(...data.comments);
      lastPage.value = data.lastPage;
      currentPage.value = data.currentPage + 1;
    }
  );
};

onMounted(async () => {
  // load post from the route username and postId
  post.value = await PostService.getUserPost(username, postId)
    .then(async (post) => {
      // fetch comments only if post returns with http 200
      await fetchComments();
      exists.value = true;
      date.value = dateTimeDiff(post["created"]);
      return post;
    })
    .catch((err) => {
      // set exists to false since not api returned 404
      if (err.response.status === 404) {
        exists.value = false;
      }
    });
});
</script>
