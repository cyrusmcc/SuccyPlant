import api from "./api";

const BLOG_API_URL = "http://localhost:8080/api/gallery/";

var pageNum = 0;
var sortedPageNum = 0;

class GalleryService {
  getPosts(tags, searchTerm) {
    console.log(searchTerm);
    tags = JSON.stringify(tags);
    return api
      .get(BLOG_API_URL + "get-all", {
        headers: {
          pageNum,
        },
        params: {
          tags,
          searchTerm,
        }
      })
      .then((response) => {
        pageNum++;
        return response.data;
      });
  }
  getSortedPosts(params) {
    return api
      .get(BLOG_API_URL + "plants" + "?" + params, {
        sortedPageNum,
      })
      .then((response) => {
        return response.data;
      });
  }
  getTagsByCategory(category) {
    return api
      .get(BLOG_API_URL + "get-tags-by-category/" + category)
      .then((response) => {
        return response.data;
      });
  }
}

export default new GalleryService();
