const galState = [
  {
    tags: ["tag 1"],
    searchTerm: "hey",
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
  },
};
