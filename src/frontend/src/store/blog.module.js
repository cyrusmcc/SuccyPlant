const blogArr = [];

export const blogs = {
  namespaced: true,
  state: blogArr,
  actions: {
  },
  getters: {
    getBlogs: state => {
      return state.blogArr;
    },
    getBlogById: (state) => (id) => {
      if (blogArr.length == 0) {
        return null;
      }
      else {
        return state.blogArr.find(blog => blog.id == id);
      }
    },
  }
};