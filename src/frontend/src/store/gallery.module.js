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
    },
    removeTag: (state, tag) => {
      for (let i = 0; i < state[0].tags.length; i++) {
        if (state[0].tags[i].label === tag.label) {
          if (state[0].tags[i].selected.includes(tag.option))
            state[0].tags[i].selected.splice(state[0].tags[i].selected.indexOf(tag.option), 1);
        }
        if (state[0].tags[i].selected == 0) state[0].tags.splice(i, 1);
      }
    },
    setSearchTerm: (state, searchTerm) => {
      state[0].searchTerm = searchTerm;
    },
    setPosts: (state, posts) => {
      state[0].posts = posts;
    },
  }
};
