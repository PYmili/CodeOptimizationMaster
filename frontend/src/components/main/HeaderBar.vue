<template>
  <el-header class="header-bar">
    <!-- 移动端汉堡 -->
    <el-icon
        class="hamburger hidden-md-and-up"
        size="20"
        @click="$emit('toggle-menu')"
    >
      <Menu />
    </el-icon>

    <span class="header-title">代码优化大师</span>

    <div class="header-actions">
      <!-- 主题切换 -->
      <el-switch
          v-model="isDark"
          inline-prompt
          :active-icon="ElementPlusIcons.Moon"
          :inactive-icon="ElementPlusIcons.Sunny"
      />
      <!-- 头像 -->
      <el-avatar
          :src="userAvatar"
          :style="{ width: '32px', height: '32px', cursor: 'pointer' }"
          @click="drawer = true"
      />
    </div>
  </el-header>

  <!-- 用户信息抽屉（原有代码） -->
  <el-drawer
      v-model="drawer"
      title="用户信息"
      direction="ltr"
      :size="320"
  >
    <el-form :model="userForm" label-width="70px">
      <el-form-item label="昵称"><el-input v-model="userForm.nickName" /></el-form-item>
      <el-form-item label="邮箱"><el-input v-model="userForm.email" /></el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveUser">保存</el-button>
        <el-button @click="drawer = false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script setup>
import {ref, reactive} from 'vue'
import {useDark, useToggle} from '@vueuse/core'
import * as ElementPlusIcons from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {Menu} from '@element-plus/icons-vue'

const isDark = useDark()
const toggleDark = useToggle(isDark)
const drawer = ref(false)
const userAvatar = ref(null)
const userForm = reactive({nickName: '优化大师', email: 'master@example.com'})

function saveUser() {
  ElMessage.success('已保存')
  drawer.value = false
}

defineEmits(['toggle-menu'])
</script>

<style scoped>
.header-bar {
  position: relative;
  display: flex;
  align-items: center;
  padding: 0 20px;
  font-size: 18px;
  font-weight: bold;
  justify-content: space-between;
  border-bottom: 1px solid var(--el-border-color-light);
}

.header-title {
  flex: 1;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.hamburger {
  cursor: pointer;
  margin-right: 8px;
}
</style>