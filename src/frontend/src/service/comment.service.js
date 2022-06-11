import api from "./api";

const COMMENT_API_URL = "http://localhost:8080/api/comment/";

class CommentService {
    getPostComments(postId) {
        return api
            .get(COMMENT_API_URL + "get-comments/" + postId)
            .then((response) => {
                return response.data;
            });
    }
    newComment(values, commentAuthor, commentContent) {
        const formData = new FormData();
        formData.append("commentContent", commentContent);
        formData.append("commentAuthor", commentAuthor);
        formData.append("postId", values.postId);
        formData.append("replyToId", values.replyToId);
        if (values.image) formData.append("image", values.image);

        return api.post(COMMENT_API_URL + "new-comment", formData);
    }
}

export default new CommentService();
