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
    newComment(commentContent, commentAuthor, postId, replyToId) {
        const formData = new FormData();
        formData.append("commentContent", commentContent);
        formData.append("commentAuthor", commentAuthor);
        formData.append("postId", postId);
        formData.append("replyToId", replyToId);
        //if (values.image) formData.append("image", values.image);

        return api.post(COMMENT_API_URL + "new-comment", formData);
    }
}

export default new CommentService();
