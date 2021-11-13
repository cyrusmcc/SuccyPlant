<template>
  <div class="tagSort">
    <drop-down
      label="Type"
      :options="['House Plant', 'Succ', 'Cactus']"
      @select-tag="addTagChip"
    ></drop-down>
    <drop-down
      label="Genus"
      :options="genusValues"
      @select-tag="addTagChip"
    ></drop-down>
    <drop-down
      label="Size"
      :options="['Small', 'Medium', 'Large']"
      @select-tag="addTagChip"
    ></drop-down>
    <drop-down
      label="Difficulty"
      :options="['Beginner Friendly', 'Advanced']"
      @select-tag="addTagChip"
    ></drop-down>
    <drop-down
      label="Light"
      :options="['Low', 'Medium', 'High']"
      @select-tag="addTagChip"
    ></drop-down>
    <drop-down
      label="Pet Friendly"
      :options="['Yes', 'No']"
      @select-tag="addTagChip"
    ></drop-down>
    <div class="selectedTags"></div>
  </div>
</template>

<script>
import dropDown from "../components/Dropdown";
import galleryService from "../service/gallery.service";

export default {
  name: "TagSort",
  components: {
    dropDown,
  },
  data() {
    return {
      selectedTags: [],
      genusValues: [],
    };
  },
  created() {
    const genusVals = async () => {
      const arr = await galleryService.getTagsByCategory("genus");
      this.genusValues = arr;
    };
    genusVals();
  },
  methods: {
    addTagChip(label, option) {
      let params = new URLSearchParams(this.$router.currentRoute.value.query);

      if (!params.has(label) && !window.location.search) {
        params.set(label, option);
        params.sort();
        this.$router.push("/plants" + "?" + params.toString());
      }
      if (params.has(label) && !params.getAll(label).includes(option)) {
        params.append(label, option);
        params.sort();
        this.$router.push("/plants" + "?" + params.toString());
      }

      this.$emit("sort-posts-by-tag", params.toString());
    },
  },
};
</script>

<style lang="scss" scoped>
.tagSort {
  display: flex;
  flex-direction: row;
  row-gap: 10px;
  column-gap: 10px;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: wrap;
  margin-top: 10px;
  border-radius: 4px;
  height: fit-content;
  width: 95%;
}
</style>
