<template>
  <v-flex
    sm12
    md8
    text-center
  >
    <form @submit.prevent="sendMessage">
      <v-text-field
        label="Type a message"
        type="text"
        v-model="message"
      >
        <template v-slot:append>
          <v-btn
            color="primary"
            @click="sendMessage"
          >
            Send message
            <v-icon right>keyboard_return</v-icon>
          </v-btn>
        </template>
      </v-text-field>
    </form>
  </v-flex>
</template>

<script>
export default {
  data() {
    return {
      message: ""
    };
  },
  computed: {
    conferenceId() {
      return this.$route.params.conferenceId;
    }
  },
  methods: {
    sendMessage() {
      const newMessage = {
        type: "MESSAGE",
        // userName: "you",
        message: this.message
      };
      const messageWithConferenceId = { conferenceId: this.conferenceId, message: newMessage };
      this.$store.dispatch("main/sendMessage", messageWithConferenceId);
      this.message = "";
    }
  }
};
</script>

<style scoped>
</style>