<template>
  <div class="filterBarContainer">
    <div id="selectedTags"></div>
    <search-bar></search-bar>
    <button
      @click="toggleFilter"
      class="filterButton"
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
      @sort-posts-by-tag="getSortedGalPosts"
      :showFilter="showFilter"
    ></tag-sort>
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
    };
  },
  methods: {
    toggleFilter() {
      this.showFilter = !this.showFilter;
    },
  },
};
</script>

<style lang="scss" scoped>
.searchBar {
  width: 90%;
}
button {
  background: $primaryLight;
  border: none;
}
.filterBarContainer {
  width: 95%;
  min-width: 320px;
  display: flex;
  flex-direction: column;
  align-items: center;
  grid-area: 1 / 1 / 5 / 2;
  border: 1px solid $outline;
  border-radius: 4px;
  box-shadow: $shadowLight;
}
.filterButton {
  margin-top: 10px;
  border: none;
  padding: 8px 13px;
  border-radius: 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
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
  height: fit-content;
  width: 100%;
  border-radius: 4px 4px 0 0;
  background-color: $accentDark;
}

#selectedTags :deep(.tagChip) {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin: 5px;
  padding: 3px;
  border-radius: 4px;
  background-color: $accentOne;
  color: $primaryDark;
  font-size: 0.8rem;
  font-weight: bold;
  cursor: pointer;
}

#selectedTags :deep(.tagChipLabel) {
  display: none;
}

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
</style>