<template>
  <router-view/>
</template>

<script setup>
import {onMounted} from "vue";
import { printConsole } from "@/utils/figletTextToConsole.js";
import {printImageToConsole} from "@/utils/ImageTextToConsole.js";

const imagePath = 'public/image/surprise mother fucker.png';
const printText = 'Surprise mother fucker !';

onMounted(async () => {
  printImageToConsole(imagePath)
  await printConsole(printText)
})
</script>

<style>
body {
  margin: 0;
  padding: 0;
}
</style>
