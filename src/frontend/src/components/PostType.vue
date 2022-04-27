<template>
  <RouterLink :to="link" class="post-type">
    <span class="text-sm mr-1 sm:block hidden">{{ icon }}</span>
    <span class="text-xs">
      <slot></slot>
    </span>
    <span class="hover-animation"></span>
  </RouterLink>
</template>

<script setup>
defineProps(["link", "icon"]);
</script>
