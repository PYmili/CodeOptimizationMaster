<template>
  <div
      class="chat-panel"
      @drop="onDrop"
      @dragover.prevent="isDragOver = true"
      @dragleave="isDragOver = false"
      :class="{ 'drag-over': isDragOver }"
  >
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

    <div class="input-container">
      <el-input
          v-model="inputValue"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 8 }"
          placeholder="尽管问..."
          :disabled="disable"
          @keyup.enter.exact="sendMessage"
          :style="{ width }"
          resize="none"
          class="chat-textarea"
      />

      <div class="toolbar">
        <div class="toolbar-left">
          <el-tooltip content="选择功能模式" placement="top">
            <el-dropdown trigger="click" @command="handleSelectToolbar">
              <el-button class="icon-btn" circle size="small">
                <el-icon><Operation /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="网络搜索" disabled>
                    <el-icon><Search /></el-icon>
                    <span>联网搜索</span>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-tooltip>

          <div class="selected-tool-command">
            <el-scrollbar>
              <div class="tool-tag">
                <el-tag
                    v-for="(tool, idx) in toolList"
                    :key="idx"
                    closable
                    @close="removeToolTag"
                >
                  {{ tool }}
                </el-tag>
              </div>
            </el-scrollbar>
          </div>
        </div>

        <div class="toolbar-right">
          <el-tooltip content="上传附件" placement="top">
            <el-button class="icon-btn" circle size="small" @click="handleCommand('attach')">
              <el-icon><Paperclip /></el-icon>
            </el-button>
          </el-tooltip>

          <el-tooltip content="打开工具箱" placement="top">
            <el-button class="icon-btn" circle size="small" @click="handleCommand('toolbox')">
              <el-icon><Box /></el-icon>
            </el-button>
          </el-tooltip>

          <el-tooltip :disabled="disable" content="发送消息 (Enter)" placement="top">
            <el-button
                class="send-btn"
                circle
                size="small"
                type="primary"
                :disabled="disable"
                @click="sendMessage"
            >
              <el-icon><Top /></el-icon>
            </el-button>
          </el-tooltip>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits, defineProps } from 'vue'
import {
  Top,
  Operation,
  Paperclip,
  Box,
  Search,
} from '@element-plus/icons-vue'

defineProps({
  width: String,
  height: String,
  disable: Boolean
});
const emit = defineEmits(['sendMessage', 'command'])

const inputValue = ref('')
const isDragOver = ref(false)
const fileList = ref([]) // 当前拖入的文件
const toolList = ref([]) // 工具列表

const sendMessage = () => {
  const text = inputValue.value.trim()
  if (!text && !fileList.value.length) return
  emit('sendMessage', {
    type: 'user',
    text: text,
    files: fileList.value
  })
  // 清空
  inputValue.value = ''
  fileList.value = []
}

const handleSelectToolbar = (command) => {
  toolList.value.push(command)
}
const handleCommand = (command) => {
  if (command === 'attach') {
    const input = document.createElement('input')
    input.type = 'file'
    input.multiple = true
    input.addEventListener('change', e => {
      const files = Array.from(e.target.files)
      if (files.length) fileList.value.push(...files)
      input.remove()
    })
    input.click()
  }
  emit('command', command)
}

const onDrop = (e) => {
  e.preventDefault()
  isDragOver.value = false
  const files = Array.from(e.dataTransfer.files)
  if (files.length) fileList.value.push(...files)
}

const removeToolTag = (idx) => toolList.value.splice(idx, 1)
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
  border-radius: var(--el-border-radius-base) var(--el-border-radius-base) 0 0;
  padding: 4px 8px;
}

.file-list {
  display: flex;
  gap: 6px;
  white-space: nowrap;
}

/* 输入容器 */
.input-container {
  background: var(--el-bg-color-page);
  border: 1px solid var(--el-border-color);
  border-radius: var(--el-border-radius-base);
  padding: 16px;
  transition: border-color 0.3s;
}

.input-container:hover {
  border-color: var(--el-border-color-hover);
}

.input-container:focus-within {
  border-color: var(--el-color-primary);
}

/* 文本框样式 */
.chat-textarea :deep(.el-textarea__inner) {
  background: transparent;
  border: none;
  color: var(--el-text-color-primary);
  font-size: var(--el-font-size-base);
  padding: 0;
  box-shadow: none;
  line-height: 1.6;
}

.chat-textarea :deep(.el-textarea__inner)::placeholder {
  color: var(--el-text-color-placeholder);
}

.chat-textarea :deep(.el-textarea__inner):focus {
  box-shadow: none;
}

/* 工具栏 */
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid var(--el-border-color-light);
}

.toolbar-left,
.toolbar-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

/* 按钮通用样式 */
.icon-btn {
  background: transparent;
  border: 1px solid var(--el-border-color);
  color: var(--el-text-color-regular);
  transition: all 0.3s;
}

.icon-btn:hover {
  background: var(--el-fill-color-light);
  border-color: var(--el-border-color-hover);
  color: var(--el-text-color-primary);
}

/* 圆形图标按钮 */
.icon-btn {
  width: 32px;
  height: 32px;
  padding: 0;
}

/* 发送按钮 */
.send-btn {
  width: 32px;
  height: 32px;
  padding: 0;
  background: var(--el-fill-color-dark);
  border-color: var(--el-fill-color-dark);
}

.send-btn:hover {
  background: var(--el-text-color-disabled);
  border-color: var(--el-text-color-disabled);
}

.send-btn:active {
  background: var(--el-color-primary);
  border-color: var(--el-color-primary);
}

/* 下拉菜单项 */
:deep(.el-dropdown-menu__item) {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
}

:deep(.el-dropdown-menu__item .el-icon) {
  font-size: 16px;
}

:deep(.el-dropdown-menu__item span) {
  font-size: var(--el-font-size-base);
}

/* 响应式调整 */
@media (max-width: 768px) {
  .toolbar {
    flex-wrap: wrap;
    gap: 8px;
  }
  .chat-panel {
    min-width: 90vw;
  }
}
</style>
