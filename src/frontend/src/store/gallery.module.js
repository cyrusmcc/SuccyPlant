const galleryArr = [];

export const gallery = {
  namespaced: true,
  state: galleryArr,
  actions: {},
  getters: {
    getGalPosts: (state) => {
      return state.galleryArr;
    },
  },
};