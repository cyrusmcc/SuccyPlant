<template>
    <div class="commentBoxContainer">
        <span class="commentCount" v-html="commentCount == 1 ?
        commentCount + ' Comment' : commentCount + ' Comments'"></span>
        <div class="replyBox" v-if="currentUser">
            <text-editor @bodyText="setCommentContent" />
            <button class="submitCommentButton" @click="handleNewComment">Submit</button>
        </div>
        <div class="commentsContainer">
            <div class="threadContainer" v-for="(comment, index) in comments" :key="index">
                <div class="parentComment comment">
                    <profile-pic class="profileUserPic" alt="profile picture">
                        <img id="commentProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
                    </profile-pic>
                    <div class="commentText">
                        <div class="commentHeader">
                            <div class="commentUsername">
                                {{ comment.user.username }}
                            </div>
                            <span class="separatorDot"></span>
                            <div class="commentDate">
                                {{ parseCommentDate(comment.timestamp) }}
                            </div>
                        </div>
                        <div class="commentBody" lang="en">
                            {{ comment.content }}
                        </div>
                    </div>
                </div>
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
import userService from "../service/user.service";
import TextEditor from "./TextEditor.vue";
import commentService from "../service/comment.service";
//import { Form, Field, ErrorMessage } from "vee-validate";
//import * as yup from "yup";

export default {
    name: "CommentBox",
    components: {
        ProfilePic,
        TextEditor,
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
    mounted() {
        if (this.currentUser) {
            let currentUserName = this.currentUser.username;

            userService.getUserProfilePic(currentUserName).then(
                (response) => {
                    let imageNode = document.getElementById("replyBoxProfilePic");
                    let imgUrl = URL.createObjectURL(response.data);
                    if (imageNode) imageNode.src = imgUrl;
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
        }
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
            commentService.newComment(
                this.commentContent,
                this.currentUser.username,
                this.$route.params.id,
                this.replyToId).then((response) => {
                    this.message = response.data;
                })
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
        parseCommentDate(timestamp) {
            let date = new Date(timestamp);
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let year = date.getFullYear();
            return month + "/" + day + "/" + year;
        }
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

#userPicImgCont {
    height: 3.5rem;
    width: 3.5rem;
}

.replyBox {
    align-items: center;
    column-gap: 10px;
    display: flex;
    flex-direction: row;
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

#userPicImgCont {
    height: 2rem;
    min-width: 2rem;
    width: 2rem;
}

.separatorDot {
    align-self: center;
    background-color: $highlightTwo;
    border-radius: 50%;
    display: inline-block;
    height: 5px;
    width: 5px;
}

.commentsContainer {
    display: flex;
    flex-direction: column;
    row-gap: 30px;
    ;
}

.threadContainer {
    border: 1px solid $outline;
    box-shadow: $shadowLight;
    column-gap: 10px;
    display: flex;
    flex-direction: column;
    padding: 4px;
}

.comment {
    column-gap: 10px;
    display: flex;
    flex-direction: row;
}

.commentText {
    display: flex;
    flex-direction: column;
    row-gap: 3px;
}

.commentHeader {
    align-items: center;
    column-gap: 10px;
    display: flex;
}

.commentUsername {
    font-size: 0.8rem;
}

.commentDate {
    font-size: 0.7rem;
}

.commentBody {
    font-family: $raleway;
    font-size: 0.9rem;
    white-space: pre-wrap;
    word-break: break-all;
}

.childComment {
    border-left: 1px solid $primaryDark;
    margin-top: 10px;
    padding-left: 5px;
}
</style>