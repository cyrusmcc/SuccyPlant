const galState = [
  {
    tags: [],
    searchTerm: "",
    posts: [],
  }
]

export const gallery = {
  namespaced: true,
  state: galState,
  actions: {},
  getters: {
    getTags: (state) => {
      return state[0].tags;
    },
    getSearchTerm: (state) => {
      return state[0].searchTerm;
    },
    getPosts: (state) => {
      return state[0].posts;
    },
    getSelectedTagsByLabel: (state) => (label) => {
      return state[0].tags.filter(tag => tag.label === label);
    }
  },
  mutations: {
    setTags: (state, tags) => {
      state[0].tags = tags;
    }
  },
};
