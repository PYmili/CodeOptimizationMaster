package cyou.pymiliblog.codeoptimizationmaster.service.ai;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiChatCacheService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private static final String KEY_PATTERN = "chat:message:%d";

    /**
     * 写入消息
     */
    public void saveMessage(Long convId, String message) throws Exception {
        String key = String.format(KEY_PATTERN, convId);
        try {
            redisTemplate.opsForValue().set(key, message, Duration.ofMinutes(10));
        } catch (Exception e) {
            log.error("[saveMessage] redis error, convId={}", convId, e);
            throw new Exception("缓存写入失败");
        }
    }

    /**
     * 读取消息（给 /chat 用）
     */
    public Optional<String> getMessage(Long convId) {
        String key = String.format(KEY_PATTERN, convId);
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

}