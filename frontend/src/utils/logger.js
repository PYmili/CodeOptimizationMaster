import log from "loglevel";

// 通过环境变量决定初始等级
if (import.meta.env.PROD) {
    log.setLevel("warn");  // 生产环境只输出 warn / error
} else {
    log.setLevel("debug"); // 开发环境输出所有级别
}

export default {
    debug: (...args) => log.debug(...args),
    info:  (...args) => log.info(...args),
    warn:  (...args) => log.warn(...args),
    error: (...args) => log.error(...args),
};
