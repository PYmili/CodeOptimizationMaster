<template>
  <div
      class="chat-message"
      :class="{'chat-user': message.type==='user','chat-ai': message.type==='ai'}"
  >
    <div class="message-content">{{ message.text }}</div>
  </div>
</template>

<script setup>
defineProps({
  message: {type: Object, required: true}
});
</script>

<style scoped>
.chat-message {
  display: flex;
  margin-bottom: 10px;
}

.chat-user {
  justify-content: flex-end;
}

.chat-ai {
  justify-content: flex-start;
}

.message-content {
  max-width: 70%;
  padding: 8px 12px;
  border-radius: 8px;
  word-break: break-word;
}

.chat-user .message-content {
  background-color: var(--el-color-primary);
  color: var(--el-text-color-primary);
}

.chat-ai .message-content {
  background-color: var(--el-bg-color);
  color: var(--el-text-color-primary);
}
</style>
