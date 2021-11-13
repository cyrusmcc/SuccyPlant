<template>
  <div class="searchBar">
    <div class="searchBarInputWrapper">
      <input
        type="text"
        class="searchBarInput"
        v-model="searchQuery"
        @keyup.enter="search"
        placeholder="Search"
      />
      <button class="searchBarButton" @click="search">
        <img class="searchBarIcon" src="../assets/imgs/magGlassDark.svg" />
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: "",
    };
  },
  methods: {
    search() {
      this.$emit("search", this.searchQuery);
    },
  },
};
</script>

<style lang="scss" scoped>
.searchBar {
  padding-top: 40px;
  margin-top: 10px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 95%;
  height: fit-content;
}

.searchBarInputWrapper {
  width: 100%;
  position: relative;
  display: flex;
}

.searchBarInput {
  width: 100%;
  height: 100%;
  padding: 0.5rem;
  border: medium solid $accentDark;
  border-right: none;
  border-radius: 8px 0 0 8px;
  background-color: $primaryLight;
  font-family: inherit;
  font-weight: 400;
  color: $primaryDark;
  outline: none;
}

.searchBarButton {
  background-color: $primaryLight;
  display: flex;
  align-items: center;
  justify-content: center;
  border: medium solid $accentDark;
  border-left: none;
  border-radius: 0 8px 8px 0;
}

.searchBarIcon {
  width: 1.2em;
  height: 1.2rem;
}
</style>
