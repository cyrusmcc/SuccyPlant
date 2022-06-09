<template>
    <div class="commentBoxContainer">
        <span class="commentCount">{{ commentCount }} Comments</span>
        <div class="replyBox" v-if="currentUser">
            <!--
            <profile-pic class="profileUserPic" alt="profile picture">
                <img id="replyBoxProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
            </profile-pic>
-->
            <text-editor />
            <!--
            <textarea id="userReplyTextArea" class="replyTextArea" placeholder="Reply here..."
                @input="adjustTextArea()"></textarea>
-->
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
                                {{ comment.user.name }}
                            </div>
                            <span class="separatorDot"></span>
                            <div class="commentDate">
                                {{ comment.date }}
                            </div>
                        </div>
                        <div class="commentBody" lang="en">
                            {{ comment.body }}
                        </div>
                    </div>
                </div>
                <div class="commentReplies">
                    <div class="childComment comment" v-for="(reply, index) in comment.replies" :key="index">
                        <profile-pic class="profileUserPic" alt="profile picture">
                            <img id="commentProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
                        </profile-pic>
                        <div class="commentText">
                            <div class="commentHeader">
                                <div class="commentUsername">
                                    {{ comment.user.name }}
                                </div>
                                <span class="separatorDot"></span>
                                <div class="commentDate">
                                    {{ comment.date }}
                                </div>
                            </div>
                            <div class="commentBody" lang="en">
                                {{ comment.body }}
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

export default {
    name: "CommentBox",
    components: {
        ProfilePic,
        TextEditor
    },
    data() {
        return {
            comments: [
                {
                    id: 1,
                    user: {
                        id: 1,
                        name: "John Doe",
                        profilePic: "../assets/imgs/userDark.svg"
                    },
                    body: "This is a comment",
                    date: "01/21/2022",
                    replies: [
                        {
                            id: 1,
                            user: {
                                id: 1,
                                name: "John Doe",
                                profilePic: "../assets/imgs/userDark.svg"
                            },
                            body: "This is a reply"
                        }
                    ]
                },
                {
                    id: 2,
                    user: {
                        id: 2,
                        name: "Jane Doe",
                        profilePic: "../assets/imgs/userDark.svg"
                    },
                    body: "This is a comment",
                    date: "01/21/2022",
                    replies: [
                        {
                            id: 1,
                            user: {
                                id: 1,
                                name: "John Doe",
                                profilePic: "../assets/imgs/userDark.svg"
                            },
                            body: "This is a reply"
                        }
                    ]
                }
            ],
            commentCount: 12,
            replyBoxHeight: 0,
            replyBoxWidth: 0,
        }
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
                    imageNode.src = imgUrl;
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
    width: 100%;
}

.replyTextArea {
    background: $primaryLight;
    border: none;
    height: 44px;
    resize: none;
    width: 100%;
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
    display: flex;
    flex-direction: column;
    column-gap: 10px;
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
    margin: 15px 0 0 15px;
    padding-left: 5px;
}
</style>