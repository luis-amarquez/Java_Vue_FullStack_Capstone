<template>
  <p class="mr-3 text-sm font-medium font-bold">
    {{ count }}
    <span class="font-normal text-gray-600">
      <slot></slot>
    </span>
  </p>
</template>

<script setup>
defineProps(["count"]);
</script>
