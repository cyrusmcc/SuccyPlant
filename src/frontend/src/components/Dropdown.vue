<template>
  <div class="dropDown" @click="toggle()" :style="{ background: color }" v-if="displayType == 'dropDown'">
    <div class="dropBoxSelectContainer">
      <span class="dropBoxLabel">{{ label }}</span>
      <div class="imgContainer" v-if="!isOpen">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBoxOptionsContainer scrollContainer" v-show="isOpen" :style="{ border: '2px solid ' + color }">
      <div class="dropBoxOptionContainer sideBarLink" v-for="option in options" :key="option"
        :id="label + '-' + option">
        <div class="dropBoxOption" @click="$emit('selectTag', label, option)">
          {{ option }}
        </div>
      </div>
    </div>
  </div>
  <div class="sideSelect" :style="{ background: color }" v-if="displayType == 'sideSelect'">
    <div class="sideSelectContainer">
      <span class="dropBoxLabel">{{ label }}</span>
    </div>
    <div class="sideSelectOptions">
      <div class="sideSelectOptionContainer sideBarLink" v-for="option in options" :key="option"
        @click="selectOption(label, option)" :id="label + '-' + option">
        <div>
          {{ option }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "dropdown",
  emits: ["selectTag"],
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
    displayType: {
      type: String,
      default: "dropDown",
    },
  },
  data() {
    return {
      isOpen: false,
      selected: [],
    };
  },
  computed: {
    cssVars() {
      return {
        "--background": this.color,
        "--border": "1px solid " + this.color,
      };
    },
  },
  methods: {
    toggle() {
      this.isOpen = !this.isOpen;
    },
    selectOption(label, option) {
      this.$emit("selectTag", label, option);
    },
    // called by component which implements dropdown functionality, used to update option style to show selected status
    enableSelect(filterOption) {
      let element = document.getElementById(filterOption);
      console.log(filterOption);
      if (element) {
        element.style.background = "#e4ebf8";
        this.selected.push(filterOption);
      }
    },
    removeSelect(filterOption) {
      let element = document.getElementById(filterOption);
      if (element) {
        element.style.background = "#f4f4f3";
        this.selected.splice(this.selected.indexOf(filterOption), 1);
      }
    },
    isSelected(filterOption) {
      return this.selected.includes(filterOption);
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
  width: 95%;
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
  width: 100%;
  text-overflow: ellipsis;
  max-height: 100px;
  overflow: auto;
  overflow-x: hidden;
  background-color: $primaryLight;
  border: 1px solid transparent;
  border-radius: 0 4px 4px 4px;
}

.dropBoxOptionContainer {
  border: 2px solid transparent;
}

.dropBoxOption {
  padding: 10px 0;
  margin-left: 5px;
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

.sideSelect {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  column-gap: 10px;
  row-gap: 2px;
  width: 95%;
  box-sizing: border-box;
  border-radius: 4px;
  padding-right: 5px;
}

.sideSelectContainer {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  height: 2.5rem;
  width: fit-content;
  padding: 3px;
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 4px;
  white-space: nowrap;
}

.sideSelectOptions {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  column-gap: 5px;
  width: 100%;
}

.sideSelectOptionContainer {
  padding: 0 9px;
  box-sizing: border-box;
  border: 2px solid transparent;
  border-radius: 20px;
  background: $primaryLight;
  font-size: 0.8rem;
  cursor: pointer;
}

.scrollContainer::-webkit-scrollbar {
  background-color: $primaryLight;
  border-left: none;
  width: 6px;
}
</style>
