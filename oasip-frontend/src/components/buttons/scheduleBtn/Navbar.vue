<script setup>
import { onBeforeMount, ref } from "vue";
import { useRouter } from "vue-router";
defineEmits(["option",'upcoming','past']);
const category = ref([]);
const appRouter = useRouter();

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
const scheduleRouter = () => appRouter.push({ name: "scheduleContents",});
const categoryRouter = () => appRouter.push({ name: "categoryContents",});
</script>

<template>
    <th class="text-xl font-extrabold px-10">
        <div class="dropdown dropdown-hover">
            <button tabindex="0" class="m-1 text-xl font-extrabold">
              <p>NAME<i class="arrow down ml-3 mb-1"></i></p>
            </button>
            <ul
                tabindex="0"
                class="dropdown-content menu p-2 bg-base-300 shadow rounded-box w-64"
            >
                <li>
                    <button class="text-xl" @click.left="scheduleRouter">
                        Name
                    </button>
                </li>
                <li>
                    <button class="text-xl" @click.left="categoryRouter">
                        Category
                    </button>
                </li>
            </ul>
        </div>
    </th>
   <th class="px-10">
    <div class="dropdown dropdown-hover">
      <button tabindex="0" class="m-1 text-xl font-extrabold"><p>CLINIC<i class="arrow down ml-3 mb-1"></i></p></button>
      <ul
        tabindex="0"
        class="dropdown-content menu p-2 bg-base-300 shadow rounded-box w-64"
      >
      <li><button @click="$emit('option',0)" class="text-xl">All Clinic</button></li>
        <li v-for="content in category">
          <button @click="$emit('option',content.id)" class="text-xl">{{ content.eventCategoryName.substring(0,content.eventCategoryName.length - 7) }} </button>
        </li>
      </ul>
    </div>
  </th>
<th class="px-10">
    <div class="dropdown dropdown-hover">
      <button tabindex="0" class="m-1 text-xl font-extrabold"><p>DATE<i class="arrow down ml-3 mb-1"></i></p></button>
      <ul
        tabindex="0"
        class="dropdown-content menu p-2 bg-base-300 shadow rounded-box w-36"
      >
      <li><button @click="$emit('upcoming')" class="text-xl">Upcoming</button></li>
      <li><button @click="$emit('past')" class="text-xl">Past</button></li>
      </ul>
    </div>
  </th> 
  <th class="text-xl font-extrabold px-10">DURATION</th>
</template>

<style scoped>
.arrow {
  border: solid rgb(255, 255, 255);
  border-width: 0 4px 4px 0;
  display: inline-block;
  padding: 3px;
}
.down {
  transform: rotate(45deg);
  -webkit-transform: rotate(45deg);
}
</style>
