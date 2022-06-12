<template>
    <div class="commentBoxContainer">
        <span class="commentCount" v-html="commentCount == 1 ?
        commentCount + ' Comment' : commentCount + ' Comments'"></span>
        <div class="replyBox" v-if="currentUser">
            <text-editor @bodyText="setCommentContent" />
            <button class="submitCommentButton" @click="handleNewComment">Submit</button>
            <div v-if="message" class="error-feedback"> {{ message }}</div>
        </div>
        <div class="commentsContainer">
            <div class="threadContainer" v-for="(comment, index) in comments" :key="index">
                <comment :comment="comment"></comment>
                <div class="commentReplies">
                    <div class="childComment comment" v-for="(reply, index) in comment.replies" :key="index"
                        :style="{ 'margin-left': reply.depth * 15 + 'px' }">
                        <profile-pic class="profileUserPic" alt="profile picture">
                            <img id="commentProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
                        </profile-pic>
                        <div class="commentText">
                            <div class="commentHeader">
                                <div class="commentUsername">
                                    {{ reply.user.name }}
                                </div>
                                <span class="separatorDot"></span>
                                <div class="commentDate">
                                    {{ reply.date }}
                                </div>
                            </div>
                            <div class="commentBody" lang="en">
                                {{ reply.body }}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ProfilePic from "./ProfilePic.vue";
//import userService from "../service/user.service";
import TextEditor from "./TextEditor.vue";
import commentService from "../service/comment.service";
import Comment from "./Comment.vue"
//import { Form, Field, ErrorMessage } from "vee-validate";
//import * as yup from "yup";

export default {
    name: "CommentBox",
    components: {
        ProfilePic,
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
        handleNewComment() {

            if (this.commentContent.length < 3) {
                this.message = "Comment must be at least 3 characters long";
                return;
            }

            commentService.newComment(
                this.commentContent,
                this.currentUser.username,
                this.$route.params.id,
                this.replyToId).then((response) => {
                    this.message = response.data.message;
                    console.log(response.data)
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
</style>