import axios from "axios";

const request = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_URL,
    timeout: 10000,
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
})

// 请求拦截：统一带 token
request.interceptors.request.use(config => {
    const token = localStorage.getItem('easy_comprehensive_test_token')
    if (token) config.headers.Authorization = `Bearer ${token}`
    return config
})

export default request;