<script setup>
import { ref } from "vue";

defineEmits(["moreDetail", "editDetail"]);
const props = defineProps({
  detail: {
    type: Object,
    require: true,
  },
  category: {
    type: Array,
    require: true,
  },
  name: {
    type: String,
    require: true,
  },
  description: {
    type: String,
    require: true,
  },
  duration: {
    type: Number,
    require: true,
  },
});

const isModalOn = ref(false);
const isunique = ref(false);
const error = ref(false);

const unique = (name, id) => {
  props.category.forEach((e) => {
    if (id != e.id) {
      if (e.eventCategoryName == name) {
        isunique.value = true;
        error.value = true;
      }
    }
  });
};
</script>

<template>
  <button
    class="btn modal-button"
    @click="
      $emit('moreDetail');
      isModalOn = !isModalOn;
      error = false;
      name = detail.eventCategoryName;
      description = detail.eventCategoryDescription;
      duration = detail.eventDuration;
    "
  >
    EDIT
  </button>
  <div v-show="isModalOn" class="modal-show flex justify-center">
    <div class="modal-content bg-base-100 rounded-2xl">
      <div class="flex justify-end">
        <button class="close" @click="isModalOn = !isModalOn">x</button>
      </div>
      <div class="flex justify-center">
        <div>
          <form
            method="post"
            @submit.prevent="
              $emit(
                'editDetail',
                detail.id,
                name,
                description,
                duration,
                isunique
              );
              isunique == true ? isModalOn : (isModalOn = !isModalOn);
              isunique = false;
            "
          >
            <div
              v-show="isModalOn"
              class="text-base font-medium grid justify-center py-2"
            >
              <p
                class="grid justify-center font-bold text-4xl mb-4 font-header"
              >
                Category Name
              </p>
              <input
                type="text"
                v-model="name"
                class="text-black p-1 m-1 rounded-md"
                maxlength="100"
                required
              />
              <p class="text-red-600" v-show="error">
                Error!!! CategoryName must be unique!!!
              </p>
            </div>
            <div class="text-base font-medium grid justify-center py-2">
              <div v-show="isModalOn">
                <p
                  class="grid justify-center font-bold text-2xl mb-4 font-header"
                >
                  Description
                </p>
                <textarea
                  cols="60"
                  rows="4"
                  maxlength="500"
                  v-model="description"
                  class="text-black p-2 m-1 rounded-md"
                ></textarea>
              </div>
            </div>
            <div class="text-lg font-medium grid justify-center py-2">
              <p>
                Duration :
                <input
                  type="number"
                  min="1"
                  max="480"
                  v-model="duration"
                  class="text-black p-1 m-1 rounded-md"
                  required
                />
                <span>min: 1 | max: 480</span>
              </p>
            </div>
            <div class="flex justify-end">
              <input
                class="btn m-2"
                v-show="isModalOn"
                type="submit"
                value="OK"
                @click="unique(name, detail.id)"
              />
              <input
                class="btn m-2"
                v-show="isModalOn"
                type="button"
                value="Cancel"
                @click="isModalOn = !isModalOn"
              />
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.auto-fill {
  color: #8f8f8f;
}
.font-header {
  color: #ff9d00;
}
.modal-content {
  margin: auto;
  padding: 20px;
  width: 550px;
}
.modal-show {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(32, 32, 32);
  background-color: rgba(73, 73, 73, 0.4);
}
.close {
  color: #aaaaaa;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: rgb(82, 80, 80);
  text-decoration: none;
  cursor: pointer;
}
</style>
