<template>
    <div class="container">
        <div class="optionContainer">
            <input class="termsInput" type="checkbox" value="true" />
            <div>Agree to our <span class="modalLabel" @click="openModal('terms')"> terms of service</span> to proceed
            </div>
        </div>
        <div class=" optionContainer">
            <input class="policyInput" type="checkbox" value="true" />
            <div>Agree to our <span class="modalLabel" @click="openModal('policy')">privacy policy</span>
                proceed</div>
        </div>
        <Modal v-if="showModal" @closeModal="closeModal">
            <div v-if="modalType == 'terms'">Terms</div>
            <div v-if="modalType == 'policy'">Privacy Policy</div>
        </Modal>
    </div>
</template>

<script>
import Modal from './Modal.vue';
export default {
    name: "TermsAndPolicy",
    data() {
        return {
            modalType: "",
            showModal: false,
        };
    },
    methods: {
        openModal(contentType) {
            this.modalType = contentType;
            this.showModal = true;
        },
        closeModal() {
            this.showModal = false;
            this.modalType = "";
        },
    },
    components: { Modal }
}
</script>

<style scoped lang="scss">
.optionContainer {
    align-items: center;
    column-gap: 5px;
    display: flex;
    flex-direction: row;
    font-size: 0.8rem;
    margin-bottom: 5px;
    accent-color: $highlightTwo;
}

.modalLabel {
    color: $highlightTwo;
    cursor: pointer;
    font-weight: bold;
    overflow: visible;
}
</style>