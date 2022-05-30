<template>
  <div class="tagSort" v-if="showFilter">
    <div id="genus" class="dropBoxContainer">
      <drop-down label="Genus" :options="genusValues" :color="'#86c2b6'" ref="genusDropDown" @selectTag="addTagChip">
      </drop-down>
    </div>
    <div id="type" class="dropBoxContainer">
      <drop-down label="Type" :options="['House Plant', 'Succ', 'Cactus']" :color="'#95d991'"
        :display-type="'sideSelect'" ref="typeDropDown" @selectTag="addTagChip"></drop-down>
    </div>
    <div id="size" class="dropBoxContainer">
      <drop-down label="Size" :options="['Small', 'Medium', 'Large']" :color="'#bdb2ff'" :display-type="'sideSelect'"
        ref="sizeDropDown" @selectTag="addTagChip"></drop-down>
    </div>
    <div id="difficulty" class="dropBoxContainer">
      <drop-down label="Difficulty" :options="['Easy', 'Moderate', 'Advanced']" :color="'#ffb2b2'"
        :display-type="'sideSelect'" ref="difficultyDropDown" @selectTag="addTagChip"></drop-down>
    </div>
    <div id="light" class="dropBoxContainer">
      <drop-down label="Light" :options="['Low', 'Medium', 'High']" :color="'#f5c881'" :display-type="'sideSelect'"
        ref="lightDropDown" @selectTag="addTagChip"></drop-down>
    </div>
    <div id="water" class="dropBoxContainer">
      <drop-down label="Water" :options="['Low', 'Medium', 'High']" :color="'#93c3f5'" :display-type="'sideSelect'"
        ref="waterDropDown" @selectTag="addTagChip"></drop-down>
    </div>
    <div class="dropBoxContainer" id="petSafe">
      <drop-down label="Pet Safe" :options="['Yes', 'No']" :color="'#cbb7ac'" :display-type="'sideSelect'"
        ref="petsafeDropDown" @selectTag="addTagChip"></drop-down>
    </div>
  </div>
</template>

<script>
import dropDown from "../components/Dropdown";
import plantService from "../service/plant.service";

export default {
  name: "TagSort",
  components: {
    dropDown,
  },
  data() {
    return {
      genusValues: [],
    };
  },
  props: ["showFilter"],
  created() {
    const genusVals = async () => {
      const arr = await plantService.getTagsByCategory("genus");
      this.genusValues = arr;
    };
    genusVals();
  },
  computed: {
    selectedTags() {
      return this.$store.getters["plants/getTags"];
    },
    preselectedTags() {
      return this.$store.getters["plants/getPreselectedTags"];
    },
  },
  mounted() {
    let preSelected = this.preselectedTags;
    if (this.preselectedTags) {
      this.$nextTick(() => {
        preSelected.forEach((tag) => {
          this.addTagChip(tag.label, tag.option);
        });
      })
    }
  },
  methods: {
    addTagChip(label, option) {
      // if no tags with provided label exist
      if (
        this.selectedTags.length === 0 ||
        this.$store.getters["plants/getSelectedTagsByLabel"](label).length == 0
      ) {
        this.selectedTags.push({
          label: label,
          selected: [option],
        });
      } else {
        this.selectedTags.forEach((tag) => {
          if (tag.label === label) {
            this.removeDropDownOption(tag.label, tag.selected);
            tag.selected = [option];
          }
        });
      }
      if (this.isDropDownOptionSelected(label, option)) {
        this.removeTagChip(label, option);
      } else {
        this.$store.commit("plants/setTags", this.selectedTags);
        this.enableDropDownOption(label, option);
      }

      this.drawTags();
    },
    drawTags() {
      let selectedTagContainer = document.getElementById("selectedTags");
      document.getElementById("selectedTags").innerHTML = "";

      for (let i = 0; i < this.selectedTags.length; i++) {
        let tag = this.selectedTags[i];
        let label = tag.label;

        for (let j = 0; j < this.selectedTags[i].selected.length; j++) {
          let option = this.selectedTags[i].selected[j];
          let tagChip = document.createElement("div");
          let tagChipLabel = document.createElement("span");
          let tagChipOption = document.createElement("span");
          let chipId =
            label.toString().toLowerCase().replace(/\s/g, "") +
            option.toString().toLowerCase().replace(/\s/g, "") +
            "Chip";

          tagChip.classList.add("tagChip");
          tagChip.setAttribute("id", chipId);

          tagChip.addEventListener("click", () => {
            document.getElementById(chipId).remove();
            this.removeTagChip(label, option);
          });

          tagChipLabel.classList.add("tagChipLabel");
          tagChipOption.classList.add("tagChipOption");

          tagChipLabel.innerHTML = label;
          tagChipOption.innerHTML = option;

          tagChip.appendChild(tagChipLabel);
          tagChip.appendChild(tagChipOption);
          tagChip.style.backgroundColor = this.getColor(label);
          selectedTagContainer.appendChild(tagChip);
        }
      }

      this.$emit("sort-posts-by-tags", this.selectedTags);
    },
    removeTagChip(label, option) {
      this.$store.commit("plants/removeTag", { label, option });
      this.removeDropDownOption(label, option);
      this.$store.commit("plants/setTags", this.selectedTags);
      this.$emit("sort-posts-by-tags", this.selectedTags);
    },
    removeDropDownOption(label, option) {
      let refName = label.toLowerCase().split(" ").join("") + "DropDown";
      const ref = this.$refs[refName];
      ref.removeSelect(label + "-" + option);
    },
    enableDropDownOption(label, option) {
      let refName = label.toLowerCase().split(" ").join("") + "DropDown";
      const ref = this.$refs[refName];
      ref.enableSelect(label + "-" + option);
    },
    isDropDownOptionSelected(label, option) {
      let refName = label.toLowerCase().split(" ").join("") + "DropDown";
      const ref = this.$refs[refName];
      return ref.isSelected(label + "-" + option);
    },
    getColor(label) {
      switch (label) {
        case "Genus":
          return "#86c2b6";
        case "Type":
          return "#95d991";
        case "Size":
          return "#bdb2ff";
        case "Difficulty":
          return "#ffb2b2";
        case "Light":
          return "#f5c881";
        case "Water":
          return "#93c3f5";
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
  align-items: flex-start;
  column-gap: 10px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  height: fit-content;
  justify-content: center;
  margin-top: 10px;
  overflow: hidden;
  padding: 10px 0;
  row-gap: 10px;
  width: 100%;
}

.dropBoxContainer {
  align-items: center;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.dropDown {
  width: 95%;
}
</style>
