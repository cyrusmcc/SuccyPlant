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
  getBlogImageById(id) {
    return api.get(BLOG_API_URL + "get-image/" + id, {
      responseType: "blob",
    });
  }
  newBlogPost(values, authorUsername, bodyText) {
    const formData = new FormData();
    formData.append("title", values.title);
    formData.append("authorUsername", authorUsername);
    formData.append("bodyText", bodyText);
    formData.append("image", values.image[0]);

    return api.post(BLOG_API_URL + "new-post", formData);
  }
}

export default new BlogService();
