<template>
  <div class="main-view">
    <HeaderBar @toggle-menu="drawer = true" />

    <el-drawer v-model="drawer" direction="ltr" :size="240" :with-header="false" class="mobile-drawer">
      <SidebarMenu />
    </el-drawer>

    <el-container class="main-container">
      <el-aside width="240px" class="main-sidebar hidden-sm-and-down">
        <SidebarMenu />
      </el-aside>

        <el-main class="main-chat" ref="messagesContainer">
          <div class="chat-messages">
            <ChatMessage v-for="(msg, i) in messages" :key="i" :message="msg" />
          </div>

          <div class="chat-input">
            <ChatInput
                :width="isMobile ? '90vw' : '50vw'"
                height="100px"
                @send-message="handleSendMessage"
                @send-file="f => console.log('file:', f)"
            />
          </div>
        </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import HeaderBar from '@/components/main/HeaderBar.vue'
import SidebarMenu from '@/components/main/SidebarMenu.vue'
import ChatMessage from '@/components/main/ChatMessage.vue'
import ChatInput from '@/components/main/ChatInput.vue'

const drawer = ref(false)
const messages = ref([])
const messagesContainer = ref(null)
const isMobile = ref(window.innerWidth <= 768)

const handleSendMessage = text => {
  if (!text) return
  messages.value.push({ type: 'user', text })
  simulateSSE(text)
}

const simulateSSE = userMessage => {
  const aiMsg = { type: 'ai', text: '' }
  messages.value.push(aiMsg)
  const fullText = `这是 AI 对"${userMessage}"的回复，演示逐字输出效果。`
  let i = 0
  const timer = setInterval(() => {
    if (i < fullText.length) {
      aiMsg.text += fullText[i++]
      scrollToBottom()
    } else clearInterval(timer)
  }, 50)
}

const scrollToBottom = () =>
    nextTick(() => {
      const el = messagesContainer.value
      if (el) el.scrollTop = el.scrollHeight
    })
</script>

<style scoped>
.main-view {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--el-bg-color);
  overflow: hidden;
}

.main-container {
  flex: 1;
  min-height: 0;
  display: flex;
}

.main-sidebar {
  background: var(--el-bg-color);
}

/* <CHANGE> main-chat now handles scrolling with custom scrollbar */
.main-chat {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow-y: auto;
  padding: 10px;
  scrollbar-width: thin;
  scrollbar-color: rgba(0, 0, 0, 0.3) transparent;
  background: var(--el-bg-color-overlay);
}

.main-chat::-webkit-scrollbar {
  width: 6px;
}

.main-chat::-webkit-scrollbar-track {
  background: transparent;
}

.main-chat::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.3);
  border-radius: 3px;
}

.main-chat::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.5);
}

/* <CHANGE> chat-messages no longer scrolls, just centers content */
.chat-messages {
  flex: 1;
  overflow: visible;
  width: 50vw;
  margin: 0 auto;
  padding-bottom: 120px;
  position: relative; /* 避免产生新的 stacking context */
  z-index: 1; /* 保证比输入框低 */
}

.chat-input {
  position: sticky;
  bottom: 0;
  z-index: 10; /* 比 chat-messages 更高 */
  width: 100%;
  display: flex;
  justify-content: center;
  padding-bottom: 16px;
}

@media (max-width: 768px) {
  .main-container {
    flex-direction: column;
  }
  .hidden-sm-and-down {
    display: none !important;
  }
  .chat-messages {
    width: 100%;
  }
}
</style>