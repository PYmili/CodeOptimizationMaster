import request from "@/api/index.js";
import logger from '@/utils/logger.js'

const BASE_API_URL = `${import.meta.env.VITE_APP_BASE_URL}/ai`


/**
 * 处理 SSE
 * @param id            会话id
 * @param onMessage
 * @param onError
 * @param onClose
 * @returns {(function(): void)|*}
 */
const handleEventSource = (id, onMessage, onError, onClose) => {
    const es = new EventSource(`${BASE_API_URL}/chat?id=${id}`, { withCredentials: true })

    console.group("[SSE]")
    es.addEventListener('message', (e) => {
        logger.debug('[SSE] message', e)
        onMessage(e)
    })

    es.addEventListener('error', (e) => {
        logger.error('[SSE] error', e)
        if (onError) onError()
        console.groupEnd()
        // 服务器主动结束或网络断开，浏览器会自动尝试重连；
    })

    es.addEventListener('close', () => {
        logger.info('[SSE] 收到服务端结束标志，关闭连接')
        es.close()           // 防止浏览器重连
        onClose()
        console.groupEnd()
    })

    // 返回「关闭函数」给调用方，组件卸载时调用
    return () => {
        logger.info('[SSE] client close')
        es.close()
    }
}


/**
 * 建立 SSE 连接，收到每条消息时回调 onMessage
 * @param data                - 请求载荷
 * @param onMessage         - (event: MessageEvent) => {}
 * @param onError           - (err: Event) => {}
 * @param onClose           - () => {}
 * @returns {() => void}               - 调用返回的函数即可主动关闭连接
 */
export const AiChat = async (data, onMessage, onError, onClose) => {
    try {
        return await request.post(`${BASE_API_URL}/addMessage`, data)
            .then((response) => {
                logger.debug("[SSE] add message response", response);
                return handleEventSource(data.id, onMessage, onError, onClose)
            })
            .catch((error) => () => {
                logger.error('[SSE] add message error', error)
                onError()
                return () => {};
            });
    } catch (e) {
        logger.error('[SSE] add message error', e);
        onError()
        return () => {}
    }
}