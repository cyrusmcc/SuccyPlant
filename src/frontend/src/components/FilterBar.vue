<template>
  <div class="filterBarContainer">
    <div id="selectedTags"></div>
    <search-bar @search="setSearchValue"></search-bar>
    <button
      @click="toggleFilter"
      class="filterButton"
      v-show="showFilterButton"
      :style="[
        showFilter == true
          ? { color: '#f4f4f3', background: '#121113' }
          : { color: '#121113', background: '#f4f4f3' },
      ]"
    >
      <img
        class="filterIcon"
        src="@/assets/imgs/filter.svg"
        v-if="!showFilter"
      />
      <img
        class="filterIcon"
        src="@/assets/imgs/filterLight.svg"
        v-if="showFilter"
      />

      <span>Filter</span>
    </button>
    <tag-sort
      @sort-posts-by-tags="setSelectedTags"
      :showFilter="showFilter"
    ></tag-sort>
    <button class="searchButton" v-if="showFilter" @click="emitSortOptions">
      Search
    </button>
  </div>
</template>

<script>
import SearchBar from "./SearchBar.vue";
import TagSort from "./TagSort.vue";

export default {
  components: { TagSort, SearchBar },
  data() {
    return {
      showFilter: false,
      showFilterButton: true,
      screenWidth: window.innerWidth,
      selectedTags: [],
      searchTerm: "",
    };
  },
  methods: {
    toggleFilter() {
      this.showFilter = !this.showFilter;
    },
    setWindowWidth() {
      this.screenWidth = window.innerWidth;
      if (this.screenWidth < 500) {
        this.showFilter = false;
        this.showFilterButton = true;
      } else {
        this.showFilter = true;
        this.showFilterButton = false;
      }
    },

    setSelectedTags(tags) {
      this.selectedTags = tags;
      this.emitSortOptions();
    },
    setSearchValue(value) {
      this.searchTerm = value;
      this.emitSortOptions();
    },
    emitSortOptions() {
      this.$emit("sort-posts-by-tags", this.selectedTags, this.searchTerm);
    },
  },
  mounted() {
    this.setWindowWidth();
    window.onresize = () => {
      this.setWindowWidth();
    };
  },
};
</script>

<style lang="scss" scoped>
@include screen-md {
  .filterBarContainer {
    position: sticky;
    top: 45px;
    //box-sizing: border-box;
    // border: 1px solid $outline;
    // border-radius: 4px;
    // padding: 10px;
    // box-shadow: $shadowLight;
  }
}

.searchBar {
  width: 90%;
}
.filterBarContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  grid-area: 1 / 1 / 5 / 2;
  width: 95%;
  min-width: 320px;
  padding-bottom: 10px;
  border: 1px solid $outline;
  border-radius: 4px;
  box-shadow: $shadowLight;
}
.filterButton {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 10px;
  padding: 8px 13px;
  border-radius: 20px;
  border: none;
  background: $primaryLight;
  cursor: pointer;
}
.filterIcon {
  cursor: pointer;
  width: 16px;
  height: 16px;
  margin-right: 10px;
}

#selectedTags {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: flex-start;
  column-gap: 5px;
  height: fit-content;
  width: 100%;
  padding: 0 5px;
  box-sizing: border-box;
  border-radius: 4px 4px 0 0;
  background-color: $accentDark;
}

#selectedTags :deep(.tagChip) {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin: 5px 0;
  padding: 2px 6px;
  border-radius: 20px;
  //background-color: $accentOne;
  color: $primaryDark;
  font-size: 0.8rem;
  font-weight: bold;
  cursor: pointer;
}

#selectedTags :deep(.tagChipLabel) {
  display: none;
}

.searchButton {
  margin-top: 10px;
  padding: 8px 13px;
  border: none;
  border-radius: 20px;
  background-color: $primaryDark;
  color: $primaryLight;
  cursor: pointer;
}
</style>