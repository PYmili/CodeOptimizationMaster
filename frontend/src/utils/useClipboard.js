export const copyText = async (text) => {
    if (!text) return Promise.reject('空文本')
    // HTTPS || localhost
    if (navigator.clipboard && window.isSecureContext) {
        return navigator.clipboard.writeText(text)
    }
    // 降级：execCommand 仅留作兼容
    const ta = document.createElement('textarea')
    ta.value = text
    ta.style.position = 'fixed'
    ta.style.opacity = 0
    document.body.appendChild(ta)
    ta.select()
    const ok = document.execCommand('copy')
    document.body.removeChild(ta)
    return ok ? Promise.resolve() : Promise.reject('execCommand failed')
}