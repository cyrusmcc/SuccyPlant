import api from "./api";

const BLOG_API_URL = "http://localhost:8080/api/blog/";

var pageNum = 0;

class BlogService {
  getBlogPosts() {
    return api
      .get(BLOG_API_URL + "get-all", {
        headers: {
          pageNum,
        },
      })
      .then((response) => {
        pageNum++;
        return response.data;
      });
  }
  getBlogPostById(id) {
    return api.get(BLOG_API_URL + "get/" + id).then((response) => {
      return response;
    });
  }
  newBlogPost({ title }, { authorUsername }, { bodyTextFile }, { image }) {
    const formData = new FormData();
    formData.append("title", title);
    formData.append("authorUsername", authorUsername);
    formData.append("bodyText", bodyTextFile[0]);
    formData.append("image", image[0]);
  }
}

export default new BlogService();
