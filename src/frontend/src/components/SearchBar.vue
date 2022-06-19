<template>
  <div class="searchBar">
    <input class="searchBarInput" placeholder="Search plants" v-model="content" @keyup.enter="search(content)"
      @input="search(content)" />
    <div class="searchBarIcon">
      <img class="magGlassImg" src="@/assets/imgs/magGlassDark.svg" alt="search icon" />
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      content: "",
    };
  },
  methods: {
    search(content) {
      if (content.length > 3) {
        this.$emit("search", content);
      }
    },
  },
};
</script>

<style scoped lang="scss">
.searchBar {
  box-sizing: border-box;
  display: flex;
  position: relative;
  width: 100%;
  height: 40px;
  padding: 0;
  border: medium solid $primaryDark;
  border-radius: 20px;
  margin-top: 20px;
  background-color: $primaryLight;
  max-width: 40rem;
}

.searchBarInput {
  width: 80%;
  height: 100%;
  font-size: 1rem;
  margin-left: 10px;
  border: none;
  outline: none;
  background: transparent;
}

.searchBarIcon {
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: center;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  width: 30px;
  height: 100%;
  cursor: pointer;
}

.magGlassImg {
  height: auto;
  width: 0.85rem;
}
</style>
