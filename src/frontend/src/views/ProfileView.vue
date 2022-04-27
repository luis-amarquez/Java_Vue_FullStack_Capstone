<template>
  <div
    v-if="user"
    class="relative h-full w-full transform opacity-100 transition-all duration-150 ease-out">
    <div class="mx-auto flex w-full overflow-hidden sm:overflow-auto">
      <div
        class="col-span-12 mx-auto w-full overflow-x-hidden px-0 lg:col-span-10 xl:col-span-8 xl:overflow-visible">
        <div
          class="theme-bg theme-border-b dark:bg-dark-900 dark:border-dark-850 overflow-hidden border-gray-100 sm:rounded-t-xl">
          <div
            class="relative h-48 bg-gray-200 bg-cover bg-center"
            style="background-image: url(''); cursor: zoom-in"
          >
            <!-- edit profile button -->
            <!--            <a href="/settings/profile" class="absolute top-0 right-0 p-1 px-2 mt-2 mr-2 text-xs text-white bg-black rounded opacity-50 text click-attached">Edit Your Profile</a>-->
          </div>
          <div
            class="dark:border-dark-850 relative flex justify-between border-b border-gray-100 p-3 pb-2"
          >
            <div class="user-image-info -mt-12 flex items-end">
              <div class="relative h-20 w-20 lg:h-24 lg:w-24">
                <img
                  class="dark:bg-dark-900 h-20 w-20 rounded-full border-4 border-white bg-gray-100 dark:border-blue-500 lg:h-24 lg:w-24"
                  src="https://cdn.devdojo.com/users/April2022/github801.jpg"
                />
              </div>
              <div class="flex flex-col pb-2 pl-2">
                <h1
                  class="dark:text-dark-200 text-sm font-bold leading-none text-gray-800 lg:text-base"
                >
                  {{ user["fullName"] }}
                </h1>
                <p
                  class="dark:text-dark-500 -mt-1 flex items-center text-xs font-bold leading-none text-gray-700 lg:text-sm"
                >
                  @{{ user["username"] }}
                </p>
              </div>
            </div>
            <div class="btns flex items-center">
              <div class="ml-2">
                <div class="w-full">
                  <button
                    class="dark:border-dark-750 dark:text-dark-400 dark:bg-dark-850 dark-hover:text-dark-400 focus:shadow-outline-blue follow-button flex cursor-pointer items-center justify-center rounded-md border border-gray-200 bg-white px-3 py-1 font-medium leading-6 text-gray-700 shadow-sm transition duration-150 ease-in-out hover:text-gray-500 focus:border-blue-300 focus:outline-none"
                  >
                    <svg
                      fill="none"
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      viewBox="0 0 24 24"
                      class="my-1 h-4 w-4 sm:mr-1"
                    >
                      <path
                        d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"
                      ></path>
                    </svg>
                    <span class="hidden text-sm sm:inline">Follow</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="flex px-5 pt-4 pb-0">
            <div class="relative flex w-full flex-col justify-start">
              <p class="mt-1 mb-3 text-sm leading-5 text-gray-600">
                User has not added any information in their about section.
              </p>
            </div>
          </div>
        </div>
        <div>
          <div class="mt-5">
<!--            <div class="posts-container">-->
<!--              <PostCard v-for="post in posts" :key="post.id" :post="post" />-->
<!--            </div>-->
            <PostsContainer :data="{ page: page, username: username }" />
          </div>
        </div>
<!--        <p v-if="postTotal" class="dark:text-dark-500 w-full py-5 text-center text-gray-500">-->
<!--          You have reached the end of the road-->
<!--        </p>-->
<!--        <p v-else class="dark:text-dark-500 w-full py-5 text-center text-gray-500">-->
<!--          {{ username }} does not have any posts.-->
<!--        </p>-->
      </div>
    </div>
  </div>
  <div
    v-else-if="userNotExist"
    class="relative h-full w-full transform opacity-100 transition-all duration-150 ease-out">
    <div class="mx-auto flex w-full overflow-hidden sm:overflow-auto">
      <div
        class="col-span-12 mx-auto w-full overflow-x-hidden px-0 lg:col-span-10 xl:col-span-8 xl:overflow-visible">
        <div
          class="theme-bg theme-border-b dark:bg-dark-900 dark:border-dark-850 overflow-hidden border-gray-100 sm:rounded-t-xl">
          <div
            class="relative h-48 bg-gray-200 bg-cover bg-center"
            style="background-image: url(''); cursor: zoom-in">
          </div>
          <div
            class="dark:border-dark-850 relative flex justify-between border-b border-gray-100 p-3 pb-2"
          >
            <div class="user-image-info -mt-12 flex items-end">
              <div class="relative h-20 w-20 lg:h-24 lg:w-24">
                <img
                  class="dark:bg-dark-900 h-20 w-20 rounded-full border-4 border-white bg-gray-100 dark:border-blue-500 lg:h-24 lg:w-24"
                  src="https://cdn.devdojo.com/users/April2022/github801.jpg"
                />
              </div>
              <div class="flex flex-col pb-2 pl-2">
                <p
                  class="dark:text-dark-500 -mt-1 flex items-center text-xs font-bold leading-none text-gray-700 lg:text-sm"
                >
                  @{{ username }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <p class="w-full py-12 text-center">This account doesn't exist.</p>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
// import UserDataCount from "../components/UserDataCount.vue";
import UserService from "../services/user.service";
import PostsContainer from "../components/PostsContainer.vue";
// import PostCard from "../components/PostCard.vue";

const route = useRoute();
const username = route.params.username;
const page = route.meta["page"];

let user = ref();
let userNotExist = ref(false);

onMounted(async () => {
  user.value = await UserService.getUserFromUsername(username).catch((err) => {
    if (err.response.status === 404) {
      userNotExist.value = true;
    }
  });
});
</script>
