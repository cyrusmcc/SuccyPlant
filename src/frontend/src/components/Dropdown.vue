<template>
  <div class="dropDown" @click="toggle()" :style="{ background: color }">
    <div class="dropBoxSelectContainer">
      <span class="dropBoxLabel">{{ label }}</span>
      <div class="imgContainer" v-if="!isOpen">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div
      class="dropBoxOptionsContainer scrollContainer"
      v-show="isOpen"
      :style="{ border: '2px solid ' + color }"
    >
      <div class="dropBoxOptions" v-for="option in options" :key="option">
        <div class="dropBoxOption" @click="$emit('select-tag', label, option)">
          {{ option }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "dropdown",
  props: {
    label: {
      type: String,
      default: "",
    },
    options: {
      type: Array,
      required: true,
    },
    color: {
      required: false,
      type: String,
      default: "#f4f4f3",
    },
  },
  data() {
    return {
      isOpen: false,
    };
  },
  methods: {
    toggle() {
      this.isOpen = !this.isOpen;
    },
  },
  computed: {
    cssVars() {
      return {
        "--background": this.color,
        "--border": "1px solid " + this.color,
      };
    },
  },
};
</script>

<style scoped lang="scss">
.dropDown {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  column-gap: 5px;
  row-gap: 2px;
  width: 100%;
  background: var(--background);
  border-radius: 4px;
}

.dropBoxSelectContainer {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  height: 2.5rem;
  width: 100%;
  padding: 3px;
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 4px;
}

.dropBoxOptionsContainer {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  row-gap: 10px;
  width: 100%;
  text-overflow: ellipsis;
  max-height: 100px;
  overflow: scroll;
  overflow-x: hidden;
  padding: 3px;
  background-color: $primaryLight;
  border: var(--border);
  border-radius: 0 4px 4px 4px;
}

.dropBoxOption:hover {
  font-weight: bold;
  cursor: pointer;
}

.imgContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: fit-content;
  width: fit-content;
}

.downArrow {
  width: 80%;
  height: auto;
  user-select: none;
}

.scrollContainer::-webkit-scrollbar {
  background-color: $primaryLight;
  border-left: none;
  width: 6px;
}
</style>
