<template>
  <div class="tagSort">
    <div class="dropBox">
      <div class="tagtype">Type</div>
      <div class="imgContainer">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBox">
      <div class="tagtype">Genus</div>
      <div class="imgContainer">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBox">
      <div class="tagtype">Difficulty</div>
      <div class="imgContainer">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBox">
      <div class="tagtype">Size</div>
      <div class="imgContainer">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBox">
      <div class="tagtype">Pet Friendly</div>
      <div class="imgContainer">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBox">
      <div class="tagtype">Light</div>
      <div class="imgContainer">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
    <div class="dropBox">
      <div class="tagtype">Water</div>
      <div class="imgContainer">
        <img class="downArrow" src="../assets/imgs/downArrowDark.svg" />
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.tagSort {
  display: flex;
  flex-direction: row;
  row-gap: 10px;
  column-gap: 10px;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: wrap;
  margin-top: 10px;
  border-radius: 4px;
  height: fit-content;
  width: 95%;
}

.dropBox {
  display: flex;
  flex-direction: row;
  column-gap: 5px;
  align-items: center;
  background-color: $accentTwo;
  border-radius: 4px;
  padding: 3px 0 3px 5px;
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
}
</style>