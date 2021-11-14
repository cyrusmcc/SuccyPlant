<template>
  <div class="dropdown" @click="toggle()">
    <div
      class="dropBoxSelectContainer"
      :style="[
        isOpen == true
          ? { borderRadius: '4px 4px 0 0' }
          : { borderRadius: '4px' },
      ]"
    >
      <span class="dropBoxLabel">{{ label }}</span>
      <div class="imgContainer" v-if="!isOpen">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBoxOptionsContainer scrollContainer" v-show="isOpen">
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
};
</script>

<style scoped lang="scss">
.dropdown {
  display: flex;
  flex-direction: column;
  column-gap: 5px;
  row-gap: 2px;
  width: 6rem;
  border-radius: 4px;
  align-items: flex-start;
  justify-content: center;
}

.dropBoxSelectContainer {
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  background-color: $accentTwo;
  padding: 3px;
  width: 6rem;
  border-radius: 4px;
}

.dropBoxOptionsContainer {
  display: flex;
  flex-direction: column;
  row-gap: 10px;
  width: 6rem;
  text-overflow: ellipsis;
  max-height: 100px;
  overflow: scroll;
  overflow-x: hidden;
  padding: 3px;
  background-color: $accentTwo;
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
  background-color: $accentTwo;
  border-left: none;
  width: 6px;
}
</style>
