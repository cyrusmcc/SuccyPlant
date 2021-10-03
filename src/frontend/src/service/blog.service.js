import api from "./api";

const BLOG_API_URL = 'http://localhost:8080/api/blog/';

var pageNum = 0;

class BlogService {
  getBlogPosts() {

   return api.get(BLOG_API_URL + "get-all", {
      headers: {
        pageNum
      }
    })
      .then((response => {
        pageNum++;
        return response.data;
      }));  
  }
}

export default new BlogService();