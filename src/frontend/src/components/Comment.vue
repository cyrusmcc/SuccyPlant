<template>
    <div :class="comment.depth == 0 ? 'comment parentComment' : 'comment childComment'"
        :id="'comment ' + comment.commentId" :style="{ 'margin-left': comment.depth * 15 + 'px' }">
        <profile-pic class="profileUserPic" alt="profile picture">
            <img id="commentProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
        </profile-pic>
        <div class="commentText">
            <div class="commentHeader">
                <router-link class="commentUsername" :to="'/p/' + comment.user.username"> {{ comment.user.username }}
                </router-link>
                <span class="separatorDot"></span>
                <div class="commentDate">
                    {{ parseCommentDate(comment.timestamp) }}
                </div>
            </div>
            <div class="commentBody" lang="en">
                {{ comment.content }}
            </div>
            <div class="commentActions">
                <button class="replyButton" @click="emitReply(comment.commentId)">Reply</button>
            </div>
        </div>
    </div>
    <div v-if="comment.children.length > 0">
        <comment v-for="child in comment.children" :key="child" :comment="child"></comment>
    </div>
</template>

<script>
import ProfilePic from "./ProfilePic.vue";

export default {
    name: "CommentThread",
    props: ["comment"],
    emits: ["handleReply"],
    components: {
        ProfilePic,
    },
    data() {
        return {
        };
    },
    methods: {
        parseCommentDate(timestamp) {
            console.log(this.comment.depth);
            let date = new Date(timestamp);
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let year = date.getFullYear();
            return month + "/" + day + "/" + year;
        },
        emitReply(commentId) {

            this.$emit("handleReply", commentId);
        },

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
    cursor: pointer;
}

.replyButton:hover {
    opacity: 1;
}

#userPicImgCont {
    height: 2rem;
    min-width: 2rem;
    width: 2rem;
}
</style>