<template>
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
            <div class="commentActions">
                <button class="replyButton">Reply</button>
            </div>
        </div>
    </div>
</template>

<script>
import ProfilePic from "./ProfilePic.vue";

export default {
    name: "CommentThread",
    props: ["comment"],
    components: {
        ProfilePic,
    },
    data() {
        return {
        };
    },
    methods: {
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

.separatorDot {
    align-self: center;
    background-color: $highlightTwo;
    border-radius: 50%;
    display: inline-block;
    height: 5px;
    width: 5px;
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

.replyButton {
    font-size: 0.8rem;
    margin-left: 5px;
    opacity: 0.88;
    margin-top: 5px;
    border: none;
    background: none;
}

#userPicImgCont {
    height: 2rem;
    min-width: 2rem;
    width: 2rem;
}
</style>