/**
 * 聊天 API 模块
 * 处理与后端的 SSE (Server-Sent Events) 通信
 */

/**
 * 发送聊天消息（SSE 流式响应）
 * @param {string} conversationId - 会话 ID
 * @param {string} message - 消息内容
 * @param {Function} onChunk - 接收到数据块时的回调函数
 * @returns {Promise<void>}
 */
export async function sendChatMessage(conversationId, message, onChunk) {
    // 模拟 SSE 流式响应
    // 实际项目中，这里应该使用 EventSource 或 fetch 处理 SSE

    return new Promise((resolve, reject) => {
        try {
            // 模拟的 AI 响应内容
            const mockResponse = `根据您提供的代码，我发现以下几个可以优化的地方：

1. **性能优化**
   - 建议使用 \`useMemo\` 缓存计算结果
   - 避免在循环中创建新的函数对象

2. **代码规范**
   - 变量命名建议使用驼峰命名法
   - 添加必要的类型注解

3. **示例代码**
\`\`\`javascript
// 优化前
const result = data.map(item => {
  return item.value * 2
})

// 优化后
const result = useMemo(() => {
  return data.map(item => item.value * 2)
}, [data])
\`\`\`

希望这些建议对您有帮助！`

            // 模拟流式传输
            let index = 0
            const interval = setInterval(() => {
                if (index < mockResponse.length) {
                    // 每次发送 1-3 个字符，模拟真实的流式效果
                    const chunkSize = Math.floor(Math.random() * 3) + 1
                    const chunk = mockResponse.slice(index, index + chunkSize)
                    onChunk(chunk)
                    index += chunkSize
                } else {
                    clearInterval(interval)
                    resolve()
                }
            }, 30) // 每 30ms 发送一次

        } catch (error) {
            reject(error)
        }
    })
}

/**
 * 实际项目中使用 EventSource 的示例代码（注释掉）
 */
/*
export async function sendChatMessage(conversationId, message, onChunk) {
  return new Promise((resolve, reject) => {
    const eventSource = new EventSource(
      `/api/chat/stream?conversationId=${conversationId}&message=${encodeURIComponent(message)}`
    )

    eventSource.onmessage = (event) => {
      const data = event.data
      if (data === '[DONE]') {
        eventSource.close()
        resolve()
      } else {
        onChunk(data)
      }
    }

    eventSource.onerror = (error) => {
      eventSource.close()
      reject(error)
    }
  })
}
*/

/**
 * 实际项目中使用 fetch 处理 SSE 的示例代码（注释掉）
 */
/*
export async function sendChatMessage(conversationId, message, onChunk) {
  const response = await fetch('/api/chat/stream', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      conversationId,
      message,
    }),
  })

  if (!response.ok) {
    throw new Error('请求失败')
  }

  const reader = response.body.getReader()
  const decoder = new TextDecoder()

  while (true) {
    const { done, value } = await reader.read()

    if (done) {
      break
    }

    const chunk = decoder.decode(value, { stream: true })

    // 处理 SSE 格式的数据
    const lines = chunk.split('\n')
    for (const line of lines) {
      if (line.startsWith('data: ')) {
        const data = line.slice(6)
        if (data === '[DONE]') {
          return
        }
        onChunk(data)
      }
    }
  }
}
*/