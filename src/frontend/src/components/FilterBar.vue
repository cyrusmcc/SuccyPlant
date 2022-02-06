<template>
  <div class="filterBarContainer">
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
button {
  background: $primaryLight;
  border: none;
}
.filterBarContainer {
  width: 95%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
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
</style>