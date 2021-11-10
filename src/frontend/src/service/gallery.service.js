import api from "./api";

const BLOG_API_URL = "http://localhost:8080/api/gallery/";

var pageNum = 0;

class GalleryService {
  getGalleryPosts() {
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
  getBlogPostByTags(tags) {
    return api.get(BLOG_API_URL + "get/" + tags).then((response) => {
      return response;
    });
  }
}

export default new GalleryService();