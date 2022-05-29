<script setup>
import {ref} from 'vue';
defineEmits(["sort-date"])
const Time = ref();
</script>

<template>
  <span class="datepicker-toggle">
    <span>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-5 w-5"
        viewBox="0 0 20 20"
        fill="currentColor"
      >
        <path
          fill-rule="evenodd"
          d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z"
          clip-rule="evenodd"
        />
      </svg>
    </span>
    <form method="post" @input.prevent="$emit('sort-date', Time); Time = undefined">
      <input type="date" class="datepicker-input" v-model="Time" />
    </form>
  </span>
</template>

<style>
.datepicker-toggle {
  display: inline-block;
  position: relative;
  width: 18px;
  height: 19px;
}

.datepicker-input {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
  box-sizing: border-box;
}
.datepicker-input::-webkit-calendar-picker-indicator {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  cursor: pointer;
}
</style>
