<template>
  <li>
    <RouterLink :to="link">
      <slot></slot>
    </RouterLink>
  </li>
</template>

<script setup>
defineProps(["link"]);
</script>
