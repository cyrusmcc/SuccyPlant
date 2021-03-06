const plantState = [
  {
    tags: [],
    searchTerm: "",
    plants: [],
    // preselected allows us to create links which will filter plans on redirect to plant gallery page
    preselectedTags: [],
  },
];

export const plants = {
  namespaced: true,
  state: plantState,
  actions: {},
  getters: {
    getTags: (state) => {
      return state[0].tags;
    },
    getSearchTerm: (state) => {
      return state[0].searchTerm;
    },
    getPlants: (state) => {
      return state[0].plants;
    },
    getPlantById: (state) => (id) => {
      for (let i = 0; i < state[0].plants.length; i++) {
        if (state[0].plants[i].id == id) {
          return state[0].plants[i];
        }
      }
    },
    getSelectedTagsByLabel: (state) => (label) => {
      return state[0].tags.filter((tag) => tag.label === label);
    },
    getPreselectedTags: (state) => {
      return state[0].preselectedTags;
    },
  },
  mutations: {
    setTags: (state, tags) => {
      state[0].tags = tags;
    },
    removeTag: (state, tag) => {
      for (let i = 0; i < state[0].tags.length; i++) {
        if (state[0].tags[i].label === tag.label) {
          if (state[0].tags[i].selected.includes(tag.option))
            state[0].tags[i].selected.splice(
              state[0].tags[i].selected.indexOf(tag.option),
              1
            );
        }
        if (state[0].tags[i].selected == 0) state[0].tags.splice(i, 1);
      }
    },
    removeTagByLabel: (state, label) => {
      for (let i = 0; i < state[0].tags.length; i++) {
        if (state[0].tags[i].label === label) {
          state[0].tags.splice(i, 1);
        }
      }
    },
    setSearchTerm: (state, searchTerm) => {
      state[0].searchTerm = searchTerm;
    },
    setPlants: (state, plants) => {
      state[0].plants = plants;
    },
    setPreselectedTags: (state, tags) => {
      state[0].preselectedTags = tags;
    },
    addPlants: (state, plants) => {
      if (state[0].plants == null) this.setPlants(state, plants);
      else state[0].plants = state[0].plants.concat(plants);
    }
  },
};
