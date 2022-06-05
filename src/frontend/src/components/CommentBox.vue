<template>
    <div class="commentBoxContainer">
        <span class="commentCount">0 Comments</span>
        <div class="replyBox" v-if="currentUser">
            <profile-pic class="profileUserPic" alt="profile picture">
                <img id="replyBoxProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
            </profile-pic>
            <text-editor />
            <!--
            <textarea id="userReplyTextArea" class="replyTextArea" placeholder="Reply here..."
                @input="adjustTextArea()"></textarea>
                -->
        </div>
        <div class="commentsContainer"></div>
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