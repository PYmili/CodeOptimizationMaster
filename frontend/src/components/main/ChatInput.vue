<template>
  <div
      class="chat-panel"
      @drop="onDrop"
      @dragover.prevent="isDragOver = true"
      @dragleave="isDragOver = false"
      :class="{ 'drag-over': isDragOver }"
  >
    <!-- 文件列表 -->
    <div v-if="fileList.length" class="file-bar">
      <el-scrollbar>
        <div class="file-list">
          <el-tag
              v-for="(file, idx) in fileList"
              :key="idx"
              closable
              @close="removeFile(idx)"
          >
            {{ file.name }}
          </el-tag>
        </div>
      </el-scrollbar>
    </div>

    <!-- 输入框 -->
    <div class="chat-input">
      <el-input
          v-model="inputValue"
          placeholder="请输入消息 / 拖文件至此"
          @keyup.enter="sendMessage"
          :style="{ width, height }"
          clearable
      >
        <template #append>
          <el-button
              style="height: 100%"
              type="primary"
              :icon="Top"
              @click="sendMessage"
          />
        </template>
      </el-input>
    </div>
  </div>
</template>

<script setup>
import {ref, defineEmits, defineProps} from 'vue'
import {Top} from '@element-plus/icons-vue'

defineProps({
  width: String,
  height: String
})

const emit = defineEmits(['sendMessage', 'sendFile'])

const inputValue = ref('')
const isDragOver = ref(false)
const fileList = ref([]) // 当前拖入的文件

const sendMessage = () => {
  const text = inputValue.value.trim()
  if (!text && !fileList.value.length) return
  if (text) emit('sendMessage', text)
  fileList.value.forEach(f => emit('sendFile', f))
  // 清空
  inputValue.value = ''
  fileList.value = []
}

const onDrop = (e) => {
  e.preventDefault()
  isDragOver.value = false
  const files = Array.from(e.dataTransfer.files)
  if (files.length) fileList.value.push(...files)
}

const removeFile = (idx) => fileList.value.splice(idx, 1)
</script>

<style scoped>
.chat-panel {
  padding: 0 10px;
  position: relative;
}

/* 拖放高亮 */
.chat-panel.drag-over::after {
  content: '';
  position: absolute;
  inset: 0;
  border: 2px dashed var(--el-color-primary);
  background: var(--el-color-primary-light-9);
  pointer-events: none;
  border-radius: inherit;
}

/* 文件条：与输入框无间隔 */
.file-bar {
  margin-bottom: 0;
  background: var(--el-bg-color);
  border: 1px solid var(--el-border-color);
  border-bottom: none;
  border-radius: 6px 6px 0 0;
  padding: 4px 8px;
}

.file-list {
  display: flex;
  gap: 6px;
  white-space: nowrap;
}

.chat-input {
  border-radius: 0 0 6px 6px;
  overflow: hidden;
}

:deep(.el-input__wrapper) {
  border-radius: 20px 0 0 20px;
}
</style>