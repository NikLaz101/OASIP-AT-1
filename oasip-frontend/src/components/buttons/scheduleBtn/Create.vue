<script setup>
import { ref, onBeforeMount } from "vue";
import moment from "moment";
defineEmits(["create"]);
const props = defineProps({
  detail: {
    type: Array,
    require: true,
  },
});
const isModalOn = ref(false);
const category = ref([]);

// GET
const getCategories = async () => {
  const res = await fetch(import.meta.env.VITE_CATEGORY_URL);
  if (res.status === 200) {
    category.value = await res.json();
  } else console.log("error, cannot get data");
};

onBeforeMount(async () => {
  await getCategories();
});

const Name = ref('');
const Email = ref('');
const Selected = ref();
const Time = ref();
const Duration = ref();
const Notes = ref("");
const selectedId = ref();

const isOverlap = ref(false);
const error = ref(false);
const overlap = () => {
  var startTime = moment(Time.value).format();
  var endTime = moment(Time.value).add(Duration.value, "minutes").format();
  props.detail.forEach((e) => {
    if (e.categoryId === selectedId.value) {
      var startTime_2 = e.eventStartTime;
      var endTime_2 = moment(e.eventStartTime)
        .add(e.eventDuration, "minute")
        .format();
      if (checkOverlap(startTime, endTime, startTime_2, endTime_2)) {
        isOverlap.value = true;
        error.value = true;
        console.log("Overlap");
      }
    }
  });
};

const checkOverlap = (start_1, end_1, start_2, end_2) => {
    if (start_1 < end_2 && end_1 > start_2) return true;
  return false;
};

const newDuration = () => {
  category.value.forEach((category) => {
    if (category.eventCategoryName === Selected.value) {
      Duration.value = category.eventDuration;
      selectedId.value = category.id;
    }
  });
};

const date = ref();
function updateTime() {
  date.value = moment().format("YYYY-MM-DDTHH:mm:ss");
}
const realTime = () => {
  setInterval(updateTime, 1000);
};
realTime();
</script>

<template>
  <div id="create">
    <button
      class="btn text-xl font-extrabold px-10"
      @click="
        Name = '';
        Email = '';
        Selected = undefined;
        Time = undefined;
        Duration = undefined;
        Notes = '';
        error = false;
        isModalOn = !isModalOn;
      "
    >
      CREATE
    </button>
    <div v-show="isModalOn" class="modal-show flex justify-center">
      <div class="modal-content bg-base-100 rounded-2xl">
        <div class="flex justify-end">
          <button class="close" @click="isModalOn = !isModalOn">x</button>
        </div>
        <!-- form -->
        <form
          method="post"
          @submit.prevent="
            $emit(
              'create',
              Name,
              Email,
              selectedId,
              Time,
              Duration,
              Notes,
              isOverlap
            );
            Name == '' ||
            Email == '' ||
            Selected == undefined ||
            Time == undefined ||
            isOverlap == true
              ? isModalOn
              : (isModalOn = !isModalOn);
            isOverlap = false;
          "
        >
          <!-- Name -->
          <div class="grid justify-center">
            <label for="name">Name <span class="auto-fill">({{ Name.length }}/100)</span></label>
            <div class="py-3">
              <input
                type="text"
                v-model="Name"
                maxlength="100"
                class="form-element bg-base-100 italic"
                placeholder="Your name"
                required
              />
            </div>
            <!-- Email -->
            <label for="Email">Email  <span class="auto-fill">({{ Email.length }}/50)</span></label>
            <div class="py-3">
              <input
                type="email"
                v-model="Email"
                maxlength="50"
                class="form-element bg-base-100 border-b-2 italic"
                placeholder="Your email"
                required
              />
            </div>
            <!-- Clinic -->
            <label for="clinics">Clinic</label>
            <div class="py-3">
              <select
                name="clinics"
                class="form-element bg-base-100 border-b-2 italic"
                @change="newDuration"
                v-model="Selected"
                required
              >
                <option
                  v-for="categories in category"
                  :value="categories.eventCategoryName"
                >
                  {{ categories.eventCategoryName }}
                </option>
              </select>
            </div>
            <!-- Date -->
            <label for="Date">Date</label>
            <div class="py-3">
              <input
                type="datetime-local"
                v-model="Time"
                :min="date"
                step="any"
                class="text-black form-element"
                required
              />
              <p class="text-red-600" v-show="error">
                Error!!! this start time is overlapped other event.
              </p>
            </div>
            <!-- Duration -->
            <label for="Duration">Duration (minutes)</label>
            <div class="py-3">
              <input
                class="bg-base-100 border-b-2 italic focus:outline-none pointer-events-none form-element"
                readonly
                type="text"
                v-model="Duration"
                placeholder="Select your clinic"
              />
            </div>
            <!-- Note -->
            <label for="Note">Note  <span class="auto-fill">({{ Notes.length }}/500)</span></label>
            <div class="py-3">
              <textarea
                cols="50"
                rows="2"
                v-model="Notes"
                maxlength="500"
                class="bg-base-100 border-b-2 italic p-2 form-element"
                placeholder="Your message"
              ></textarea>
            </div>
          </div>
          <div class="flex justify-end pt-2">
            <!-- Create -->
            <input
              class="btn"
              type="submit"
              value="Create"
              @click="overlap()"
            />
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style>
.form-element {
  border-color: #494a7d;
  border-radius: 5px;
  padding: 10px;
  border-width: 2px;
  width: 100%;
}
.form-element:focus {
  outline: none !important;
  border: 2px solid #fcc302;
}

.modal-content {
  margin: auto;
  padding: 20px;
  width: 500px;
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
.auto-fill {
  color: #8f8f8f;
}
</style>
