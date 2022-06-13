<template>
    <div class="commentBoxContainer">
        <span class="commentCount" v-html="commentCount == 1 ?
        commentCount + ' Comment' : commentCount + ' Comments'"></span>
        <div class="replyBox" id="replyBox" v-if="currentUser">
            <text-editor @bodyText="setCommentContent" />
            <button class="submitCommentButton" @click="handleNewComment">Submit</button>
            <div v-if="message" class="error-feedback"> {{ message }}</div>
        </div>
        <div class="commentsContainer">
            <div class="threadContainer" v-for="(comment, index) in comments" :key="index">
                <div :id="'commentContainer ' + comment.commentId">
                    <comment :comment="comment" @handleReply="handleReply"></comment>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import TextEditor from "./TextEditor.vue";
import commentService from "../service/comment.service";
import Comment from "./Comment.vue"
//import { Form, Field, ErrorMessage } from "vee-validate";
//import * as yup from "yup";

export default {
    name: "CommentBox",
    components: {
        TextEditor,
        Comment,
    },
    data() {
        return {
            message: "",
            comments: [],
            commentContent: "",
            commentCount: 0,
            replyBoxHeight: 0,
            replyBoxWidth: 0,
            replyToId: -1,
        }
    },
    created() {
        this.getComments();
    },
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        },
    },
    methods: {
        adjustTextArea() {
            let textArea = document.getElementById("userReplyTextArea");
            textArea.style.height = "1px";
            textArea.style.height = (25 + textArea.scrollHeight) + "px";
        },
        setCommentContent(content) {
            this.commentContent = content;
        },
        handleReply(commentId) {
            // Only want one reply box open at a time.
            if (document.getElementById("userReplyTextArea")) {
                document.getElementById("userReplyTextArea").remove();
            }
            // Cancel reply if user clicks reply to same comment again.
            if (commentId === this.replyToId) {
                let userReply = document.getElementById("userReplyTextArea");
                this.replyToId = -1;
                this.commentContent = "";
                if (userReply) userReply.remove();
            }
            else {
                this.replyToId = commentId;
                let textArea = document.getElementById("replyBox");
                let textAreaClone = textArea.cloneNode(true);
                let parentCommentContainer = document.getElementById('commentContainer ' + commentId);
                let parentComment = document.getElementById("comment " + commentId);
                let parentCommentMarginLeft = parseInt(parentComment.style.marginLeft);

                // indent reply box according to level of comment
                textAreaClone.id = "userReplyTextArea";
                textAreaClone.style.marginLeft = parentCommentMarginLeft + "px";
                textAreaClone.style.width = "";
                textAreaClone.style.maxWidth = "100%";
                textAreaClone.style.minWidth = "50%";

                // update comment content on input to reply text area
                let taCloneText = textAreaClone
                    .getElementsByClassName("textEditor")[0]
                    .querySelector("#bodyContainer")
                    .querySelector("#textEditorBody");
                taCloneText.addEventListener("input", () => {
                    this.setCommentContent(taCloneText.value);
                });

                // handle new comment on reply box submit
                let textAreaCloneSubmitButton = textAreaClone.querySelector(".submitCommentButton");
                textAreaCloneSubmitButton.addEventListener("click", () => {
                    this.handleNewComment();
                    textAreaClone.remove();
                });

                parentCommentContainer.appendChild(textAreaClone);
            }
        },
        handleNewComment() {
            if (!this.commentContent) {
                return;
            }
            else {
                this.message = "";
            }

            if (this.commentContent.length < 3) {
                this.message = "Comment must be at least 3 characters long";
                return;
            }

            console.log(this.replyToId)
            commentService.newComment(
                this.commentContent,
                this.currentUser.username,
                this.$route.params.id,
                this.replyToId).then((response) => {
                    this.message = response.data.message;
                    this.getComments();
                    this.commentContent = "";
                    this.replyToId = -1;
                    document.getElementById("textEditorBody").value = "";
                })
                .catch((error) => {
                    this.message = error.response.data ? error.response.data : error.toString();
                });
        },
        getComments() {
            commentService.getPostComments(this.$route.params.id).then(
                (response) => {
                    this.comments = response;
                    this.commentCount = response.length;
                    this.buildCommentTree(this.comments)
                },
                (error) => {
                    this.message =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                }
            );
        },

        // e/a comment has reference to parent comment, so we need to build a tree of comments
        // to represent a threaded comment structure
        buildCommentTree(comments) {
            let commentTree = [];
            let commentMap = {};
            for (let comment of comments) {
                commentMap[comment.commentId] = comment;
                comment.children = [];
            }
            for (let comment of comments) {
                if (comment.parentComment && comment.parentComment.commentId) {
                    commentMap[comment.parentComment.commentId].children.push(comment);
                } else {
                    commentTree.push(comment);
                }
            }

            console.log(commentTree)
            console.log(commentMap)
            this.comments = commentTree;
        },
    }
}
</script>

<style scoped lang="scss">
.commentBoxContainer {
    display: flex;
    flex-direction: column;
    height: fit-content;
    margin: 30px 0 10px 0;
    width: 100%;
}

.commentCount {
    align-self: flex-start;
    color: $primaryDark;
    margin-bottom: 20px;
}

.replyBox {
    align-items: center;
    column-gap: 10px;
    display: flex;
    flex-direction: column;
    height: fit-content;
    margin-bottom: 30px;
    position: relative;
    width: 100%;
}

.replyTextArea {
    background: $primaryLight;
    border: none;
    height: 44px;
    resize: none;
    width: 100%;
}

.submitCommentButton {
    background: none;
    border: none;
    color: $primaryLight;
    cursor: pointer;
    font-size: 0.8rem;
    margin: 8px;
    opacity: 0.9;
    position: absolute;
    right: 0;
    top: 0;
}

.submitCommentButton:hover {
    opacity: 1;
}


.commentsContainer {
    display: flex;
    flex-direction: column;
    row-gap: 30px;
    ;
}

.threadContainer {
    column-gap: 10px;
    display: flex;
    flex-direction: column;
    padding: 4px;
}

#commentContainer {
    display: flex;
    flex-direction: column;
}
</style>