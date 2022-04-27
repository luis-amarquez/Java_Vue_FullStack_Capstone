<template>
  <div class="w-full flex items-center h-14 lg:pl-7 pr-5 pl-0 bg-white justify-between bottom-divider">
    <div class="text-sm flex items-end justify-start">
      <h1 class="font-normal text-xs leading-none text-gray-600">
        {{ page }}
      </h1>
      <span class="mx-1 leading-none">-</span>
      <p class="max-w-2xl leading-none text-xs text-gray-400">
        {{ desc }}
      </p>
    </div>
  </div>
</template>

<script setup>
defineProps(["page", "desc"]);
</script>
