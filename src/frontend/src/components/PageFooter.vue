<template>
  <div class="footerContainer">
    <router-link class="footerTitle" to="/">SUCCYPLANT</router-link>
    <span class="footerDesc">Home plant encyclopedia since 2021</span>
    <span class="footerCredits">Developed with love by Cyrus</span>
  </div>
</template>

<script>
export default {};
</script>

<style scopd lang="scss">
.footerContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  row-gap: 5px;
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 10rem;
  background: $primaryDark;
}

.footerTitle {
  font-family: $amatic;
  font-size: 1.5rem;
  font-weight: bold;
  color: $primaryLight;
  text-shadow: -1px 2px 2px black;
}

.footerDesc,
.footerCredits {
  font-family: $inter;
  font-size: 0.6rem;
  color: $primaryLight;
}
</style>
