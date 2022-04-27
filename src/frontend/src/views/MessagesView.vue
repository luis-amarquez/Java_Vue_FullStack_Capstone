<template>
  <section class="discussions">
    <RouterLink :to="{ name: 'message', params: { id: message.id } }" v-for="message in messages" :key="message.id" class="discussion">
      <div class="photo" style="background-image: url(https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__480.png);">
        <div class="online"></div>
      </div>
      <div class="desc-contact">
        <p class="name">
          {{
            message.user1.id === store.auth.user.id
              ? message.user2.fullName
              : message.user1.fullName
          }}
        </p>
      </div>
    </RouterLink>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

import { useAuthStore } from "../stores/auth";

const store = useAuthStore();

let messages = ref([]);

const fetchMessages = async () => {
  await axios
    .get("/api/message/message_rooms", {
      headers: {
        Authorization: `Bearer ${store.auth.accessToken}`,
      },
      params: {
        page: 0,
        count: 50,
      },
    })
    .then((response) => {
      console.log(response.data);
      messages.value.push(...response.data.content);
    });
};

onMounted(async () => {
  await fetchMessages();
});
</script>
<style scoped>
.discussions {
  width: 100%;
  overflow: hidden;
  display: inline-block;
}

.discussions .discussion {
  width: 100%;
  height: 70px;
  background-color: #FAFAFA;
  border-bottom: solid 1px #E0E0E0;
  display:flex;
  align-items: center;
  cursor: pointer;
}

.discussions .discussion:hover {
  background-color: #d7d0d0;
}

.discussions .discussion .photo {
  margin-left:20px;
  display: block;
  width: 45px;
  height: 45px;
  background: #E6E7ED;
  -moz-border-radius: 50px;
  -webkit-border-radius: 50px;
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
}

.online {
  position: relative;
  top: 30px;
  left: 35px;
  width: 13px;
  height: 13px;
  background-color: #8BC34A;
  border-radius: 13px;
  border: 3px solid #FAFAFA;
}

.desc-contact {
  height: 43px;
  width:50%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.discussions .discussion .name {
  margin: 0 0 0 20px;
  font-family:'Montserrat', sans-serif;
  font-size: 20pt;
  color:#515151;
}
</style>
