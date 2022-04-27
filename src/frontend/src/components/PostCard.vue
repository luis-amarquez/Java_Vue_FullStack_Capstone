<template>
  <RouterLink
    :to="{
      name: 'post',
      params: {
        username: post.user.username,
        postId: post.id,
      },
    }"
    href=""
    class="post-card bottom-divider hover:bg-gray-50"
  >
    <div class="post-top">
      <div>
        <div class="post-user">
          <RouterLink :to="{ name : 'profile', params: {username: post.user.username}}">
            <img
              src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png"
              alt="User Image"
            />
          </RouterLink>
        </div>

        <!-- Title and Description of Item -->
        <div class="post-content-container">
          <div class="post-content">
            <p class="post-username">
              <RouterLink
                :to="{
                  name: 'profile',
                  params: {
                    username: post.user.username,
                  },
                }"
                class="hover:underline"
                >{{ post.user.fullName }}</RouterLink
              >
              <span class="text-gray-400">・</span>
              <span class="post-date"> {{ date }}</span>
            </p>
            <h1 class="post-title">
              {{ post.title }}
            </h1>
            <div class="post-user-content">
              {{ post.content }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="post-bottom">
      <div>
        <div class="option">
          <div>
            <p>{{ post.likes }} Likes</p>
          </div>
        </div>
      </div>
    </div>
  </RouterLink>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "../stores/auth";
import { dateTimeDiff } from "../services/util";

const store = useAuthStore();
const props = defineProps(["post"]);

const date = ref(dateTimeDiff(props.post.created.replace("T", " ")));
</script>
