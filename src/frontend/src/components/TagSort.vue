<template>
  <div class="tagSort">
    <drop-down
      label="Type"
      :options="['House Plant', 'Succ', 'Cactus']"
      @select-tag="addTagChip"
      id="type"
    ></drop-down>
    <drop-down
      label="Genus"
      :options="genusValues"
      @select-tag="addTagChip"
      id="genus"
    ></drop-down>
    <drop-down
      label="Size"
      :options="['Small', 'Medium', 'Large']"
      @select-tag="addTagChip"
      id="size"
    ></drop-down>
    <drop-down
      label="Difficulty"
      :options="['Beginner Friendly', 'Advanced']"
      @select-tag="addTagChip"
      id="difficulty"
    ></drop-down>
    <drop-down
      label="Light"
      :options="['Low', 'Medium', 'High']"
      @select-tag="addTagChip"
      id="light"
    ></drop-down>
    <drop-down
      label="Pet Safe"
      :options="['Yes', 'No']"
      @select-tag="addTagChip"
      id="petSafe"
    ></drop-down>
    <div id="selectedTags"></div>
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
          console.log("ff");

          const tags = params.getAll(label).filter((tag) => tag !== option);
          params.delete(label);
          for (const tag of tags) params.append(label, tag);

          console.log(router);
          router.push("/plants" + "?" + params.toString());
          document.getElementById(chipId).remove();
        },
        0
      );

      tagChipLabel.classList.add("tagChipLabel");
      tagChipOption.classList.add("tagChipOption");

      tagChipLabel.innerHTML = label;
      tagChipOption.innerHTML = option;

      tagChip.appendChild(tagChipLabel);
      tagChip.appendChild(tagChipOption);
      selectedTagContainer.appendChild(tagChip);

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

#selectedTags {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: flex-start;
  margin-top: 10px;
  border-radius: 4px;
  height: fit-content;
  width: 100%;
  background-color: $accentDark;
}

/deep/ .tagChip {
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

/deep/ .tagChipLabel {
  display: none;
}

/deep/ #selectedTags > #type {
  background-color: $accentTwo;
}

/deep/ #selectedTags > #genusChip,
/deep/ #genus > div,
/deep/ #genus > .scrollContainer::-webkit-scrollbar {
  background-color: $accentOne;
}

/deep/ #selectedTags > #sizeChip,
/deep/ #size > div,
/deep/ #size > .scrollContainer::-webkit-scrollbar {
  background-color: $accentThree;
}

/deep/ #selectedTags > #difficultyChip,
/deep/ #difficulty > div,
/deep/ #difficulty > .scrollContainer::-webkit-scrollbar {
  background-color: $accentFour;
}

/deep/ #selectedTags > #lightChip,
/deep/ #light > div,
/deep/ #light > .scrollContainer::-webkit-scrollbar {
  background-color: $accentFive;
}

/deep/ #selectedTags > #petsafeChip,
/deep/ #petSafe > div,
/deep/ #petSafe > .scrollContainer::-webkit-scrollbar {
  background-color: $accentSix;
}
</style>
