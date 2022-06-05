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
        <div class="commentsContainer" v-for="(comment, index) in comments" :key="index">
            <div class="commentContainer">
                <div class="commentHeader">
                    <profile-pic class="profileUserPic" alt="profile picture">
                        <img id="commentProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
                    </profile-pic>
                    {{ comment.user.name }}
                </div>
                {{ comment.body }}
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
    margin-top: 30px;
    display: flex;
    flex-direction: column;
    width: 100%;
    height: fit-content;
}

.commentCount {
    color: $primaryDark;
    align-self: flex-start;
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
    width: 100%;
    margin-bottom: 10px;
}

.replyTextArea {
    height: 44px;
    width: 100%;
    border: none;
    resize: none;
    background: $primaryLight;
}
</style>