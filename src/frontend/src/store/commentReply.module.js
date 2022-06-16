const state = {
    replyCommentId: null,
}
export const commentReply = {
    namespaced: true,
    state: state,
    actions: {
    },
    getters: {
        getReplyCommentId: (state) => {
            return state.replyCommentId
        }
    },
    mutations: {
        setReplyCommentId: (state, id) => {
            state.replyCommentId = id
        },
    },
};
