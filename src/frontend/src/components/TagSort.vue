<template>
  <div class="tagSort" v-if="showFilter">
    <div id="genus" class="dropBoxContainer">
      <drop-down
        label="Genus"
        :options="genusValues"
        :color="'#86c2b6'"
        @selectTag="addTagChip"
      ></drop-down>
    </div>
    <div id="type" class="dropBoxContainer">
      <drop-down
        label="Type"
        :options="['House Plant', 'Succ', 'Cactus']"
        :color="'#8caed3'"
        :display-type="'sideSelect'"
        @selectTag="addTagChip"
      ></drop-down>
    </div>
    <div id="size" class="dropBoxContainer">
      <drop-down
        label="Size"
        :options="['Small', 'Medium', 'Large']"
        :color="'#bdb2ff'"
        :display-type="'sideSelect'"
        @selectTag="addTagChip"
      ></drop-down>
    </div>
    <div id="difficulty" class="dropBoxContainer">
      <drop-down
        label="Difficulty"
        :options="['Beginner Friendly', 'Advanced']"
        :color="'#ffb2b2'"
        :display-type="'sideSelect'"
        @selectTag="addTagChip"
      ></drop-down>
    </div>
    <div id="light" class="dropBoxContainer">
      <drop-down
        label="Light"
        :options="['Low', 'Medium', 'High']"
        :color="'#f5c881'"
        :display-type="'sideSelect'"
        @selectTag="addTagChip"
      ></drop-down>
    </div>
    <div class="dropBoxContainer" id="petSafe">
      <drop-down
        label="Pet Safe"
        :options="['Yes', 'No']"
        :color="'#cbb7ac'"
        :display-type="'sideSelect'"
        @selectTag="addTagChip"
      ></drop-down>
    </div>
  </div>
</template>

<script>
import dropDown from "../components/Dropdown";
import galleryService from "../service/gallery.service";
import router from "../router/router";

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
  props: ["showFilter"],
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

      if (!params.has(label)) {
        params.set(label, option);
        params.sort();
        this.$router.push("/plants" + "?" + params.toString());
      }
      if (params.has(label) && !params.getAll(label).includes(option)) {
        params.append(label, option);
        params.sort();
        this.$router.push("/plants" + "?" + params.toString());
      }

      console.log("add " + params.toString());

      let selectedTagContainer = document.getElementById("selectedTags");
      let tagChip = document.createElement("div");
      let tagChipLabel = document.createElement("span");
      let tagChipOption = document.createElement("span");
      let chipId = label.toString().toLowerCase().replace(/\s/g, "") + "Chip";

      tagChip.classList.add("tagChip");
      tagChip.setAttribute("id", chipId);
      tagChip.addEventListener(
        "click",
        function () {
          const tags = params.getAll(label).filter((tag) => tag !== option);
          params.delete(label);
          for (const tag of tags) params.append(label, tag);

          console.log(router);
          router.push("/plants" + "?" + params.toString());
          document.getElementById(chipId).remove();

          // hack to force re-render after removing chip, investigate
          // other solutions that don't force re-render
          router.go();
        },
        0
      );

      tagChipLabel.classList.add("tagChipLabel");
      tagChipOption.classList.add("tagChipOption");

      tagChipLabel.innerHTML = label;
      tagChipOption.innerHTML = option;

      tagChip.appendChild(tagChipLabel);
      tagChip.appendChild(tagChipOption);
      tagChip.style.backgroundColor = this.getColor(label);
      selectedTagContainer.appendChild(tagChip);

      this.$emit("sort-posts-by-tag", params.toString());
    },
    getColor(label) {
      switch (label) {
        case "Genus":
          return "#86c2b6";
        case "Type":
          return "#8caed3";
        case "Size":
          return "#bdb2ff";
        case "Difficulty":
          return "#ffb2b2";
        case "Light":
          return "#f5c881";
        case "Pet Safe":
          return "#cbb7ac";
        default:
          return "#86c2b6";
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.tagSort {
  display: flex;
  flex-direction: row;
  column-gap: 10px;
  row-gap: 10px;
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  margin-top: 10px;
  overflow: hidden;
  height: fit-content;
  width: 100%;
  padding: 10px 0;
}

.dropBoxContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.dropDown {
  width: 95%;
}
</style>
