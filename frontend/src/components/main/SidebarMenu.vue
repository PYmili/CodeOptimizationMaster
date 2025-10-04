<template>
  <el-menu
      class="sidebar-menu"
      router
      :default-active="activeIndex"
      :default-openeds="openMenus"
      unique-opened
  >
    <el-sub-menu
        v-for="menu in menus"
        :key="menu.index"
        :index="menu.index"
    >
      <template #title>
        <el-icon :component="menu.icon" />
        <span>{{ menu.title }}</span>
      </template>
      <el-menu-item
          v-for="sub in menu.children"
          :key="sub.index"
          :index="sub.index"
      >
        {{ sub.title }}
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue'
import * as ElementPlusIcons from '@element-plus/icons-vue'

const menus = ref([
  {
    index: '1',
    title: '会话',
    icon: ElementPlusIcons.ChatDotRound,
    children: [
      { index: '1-1', title: '代码分析' },
      { index: '1-2', title: '重构建议' },
    ],
  },
])
const activeIndex = ref('1-1')
const openMenus = ref(['1'])
</script>

<style scoped>
.sidebar-menu {
  height: 100%;
}
</style>