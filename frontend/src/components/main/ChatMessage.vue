<template>
  <div
      class="chat-message"
      :class="{ 'chat-user': message.type === 'user', 'chat-ai': message.type === 'ai' }"
  >
    <!-- AI 加载中 -->
    <div
        v-if="message.type === 'ai' && message.loading"
        v-loading="true"
        element-loading-text="Ai 思考中 ..."
        class="ai-loading"
    />

    <div class="message" :style="message.type === 'ai' ? 'max-width: 100%' : 'max-width: 70%'">
      <!-- 文件列表 -->
      <div v-if="message.files?.length" class="message-files">
        <el-tag
            v-for="(file, idx) in message.files"
            :key="`${file.name}-${idx}`"
            size="small"
        >
          {{ file.name }}
        </el-tag>
      </div>

      <!-- 用户消息：纯文本；AI 消息：用 md-preview 渲染 -->
      <template v-if="message.type === 'user'">
        <div v-show="message.text" class="message-content">{{ message.text }}</div>
      </template>
      <template v-else>
        <md-preview
            v-if="message.text"
            :model-value="message.text"
            class="message-content"
        />
      </template>

      <!-- 复制按钮 -->
      <div v-show="message.text" class="message-content-btn-group"
           :style="message.type === 'user' ? 'justify-content: flex-end' : 'justify-content: flex-start'">
        <el-button size="small" :icon="CopyDocument" text @click="copyText(message.text)">复制</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { MdPreview } from 'md-editor-v3'
import 'md-editor-v3/lib/preview.css'   // 预览样式（必须）

import { CopyDocument } from '@element-plus/icons-vue'
import { copyText } from '@/utils/useClipboard.js'
import { ElButton } from 'element-plus'

defineProps({
  message: { type: Object, required: true }
})
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

.ai-loading {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 140px;
  height: 40px;
  background: transparent;
  border-radius: 8px;
  /* 关键：强制横排 */
  writing-mode: horizontal-tb !important;
}

.message {
  max-width: 70%;
  display: flex;
  flex-direction: column;
}

.message-files {
  display: flex;
  gap: 5px;
  margin-bottom: 4px;
}

/* ---- Markdown 基础样式 ---- */
.message-content {
  padding: 8px 12px;
  border-radius: 8px;
  word-break: break-word;
  line-height: 1.6;
}

/* 信息内容的按钮组 */
.message-content-btn-group {
  display: flex;
  /* border-top: var(--el-border-color) solid 1px; */
  margin-top: 3px;
  padding: 5px;
}

/*
.message-content-btn-group :deep(.el-button>span) {
  font-size: 12px !important;
}
*/

.chat-user .message-content {
  background-color: var(--el-color-primary);
  color: var(--el-text-color-primary);
}

.chat-ai .message-content {
  background-color: var(--el-bg-color);
  color: var(--el-text-color-primary);
}

/* 标题 */
.message-content :deep(h1),
.message-content :deep(h2),
.message-content :deep(h3) {
  margin: 0.3em 0;
}

/* 列表 */
.message-content :deep(ul),
.message-content :deep(ol) {
  padding-left: 20px;
}

/* 代码 */
.message-content :deep(code) {
  background-color: var(--el-text-color-disabled);
  padding: 2px 4px;
  border-radius: 3px;
  font-family: monospace;
}

.message-content :deep(pre) {
  background-color: var(--el-bg-color-overlay);
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}

.message-content :deep(pre) code {
  background: none;
  padding: 0;
}

/* 链接 */
.message-content :deep(a) {
  color: var(--el-text-color-placeholder);
  text-decoration: none;
}

.message-content :deep(a:hover) {
  text-decoration: underline;
}

/* 引用 */
.message-content :deep(blockquote) {
  margin: 0;
  padding-left: 10px;
  border-left: 4px solid var(--el-border-color);
  color: var(--el-text-color-primary);
}
</style>