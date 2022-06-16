<template>
    <div :class="comment.depth == 0 ? 'comment parentComment' : 'comment childComment'"
        :id="'comment ' + comment.commentId">
        <div class="commentData">
            <profile-pic class="profileUserPic" alt="profile picture">
                <img id="commentProfilePic" alt="profile picture" src="../assets/imgs/userDark.svg" />
            </profile-pic>
            <div class="commentText">
                <div class="commentHeader">
                    <router-link class="commentUsername" :to="'/p/' + comment.user.username"> {{ comment.user.username
                    }}
                    </router-link>
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
        <div class="commentActions">
            <button class="replyButton" :id="'replyButton ' + comment.commentId"
                @click="storeReply(comment.commentId)">Reply</button>
        </div>
    </div>
    <div :id="'commentReplies ' + comment.commentId" class="commentReplies" v-if="comment.children.length > 0"
        :style="{ borderLeft: '2px solid ' + getBorderColor(comment.commentId) }">
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
            replyActive: false,
            borderColors: ["#93c3f5", "#86c2b6", "#f5c881", "#ffb2b2", "#bdb2ff", "#cbb7ac", "#95d991"],
            currentBorderIndex: 0,
        };
    },
    computed: {
        currentReplyCommentid() {
            return this.$store.getters["commentReply/getReplyCommentId"];
        },
    },
    mounted() {
        let replyContainer = document.getElementById("commentReplies " + this.comment.commentId);
        if (replyContainer)
            replyContainer.style.marginLeft = (this.comment.depth + 1) * 15 + "px";

    },
    methods: {
        parseCommentDate(timestamp) {
            let date = new Date(timestamp);
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let year = date.getFullYear();
            return month + "/" + day + "/" + year;
        },
        storeReply(commentId) {
            this.replyActive = !this.replyActive;
            if (this.replyActive) {
                this.$store.commit("commentReply/setReplyCommentId", commentId);
                let replyButton = document.getElementById("replyButton " + commentId);
                replyButton.innerHTML = "Cancel";
            } else {
                let replyButton = document.getElementById("replyButton " + commentId);
                this.$store.commit("commentReply/setReplyCommentId", null);
                replyButton.innerHTML = "Reply";
            }
        },
        getBorderColor(commentId) {
            let index = commentId % this.borderColors.length;
            return this.borderColors[index];
        },
    },
    watch: {
        currentReplyCommentid() {
            if (this.currentReplyCommentid !== this.comment.commentId) {
                let replyButton = document.getElementById("replyButton " + this.comment.commentId);
                replyButton.innerHTML = "Reply";
                this.replyActive = false;
            }
        },
    },
}
</script>

<style scoped lang="scss">
.comment {
    display: flex;
    flex-direction: column;
}

.commentData {
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
    border-bottom: 2px solid transparent;
    box-sizing: border-box;
    font-size: 0.8rem;
}

.commentUsername:hover {
    border-bottom: 2px solid $highlightTwo;
}

.commentUsername:visited {
    color: $primaryDark;
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
    margin-top: 20px;
    padding-left: 10px;
}

.commentReplies {
    // border-left: 1px solid $primaryDark;
}

.replyButton {
    font-size: 0.8rem;
    opacity: 0.7;
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