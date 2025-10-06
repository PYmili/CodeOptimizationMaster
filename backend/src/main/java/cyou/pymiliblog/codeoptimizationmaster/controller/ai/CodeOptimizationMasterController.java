package cyou.pymiliblog.codeoptimizationmaster.controller.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyou.pymiliblog.codeoptimizationmaster.ai.service.CodeOptimizationMasterService;
import cyou.pymiliblog.codeoptimizationmaster.common.RestCommon;
import cyou.pymiliblog.codeoptimizationmaster.service.ai.AiChatCacheService;
import cyou.pymiliblog.codeoptimizationmaster.service.store.ConversationService;
import cyou.pymiliblog.codeoptimizationmaster.vo.ai.AiChatVo;
import dev.langchain4j.internal.Json;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/ai")
public class CodeOptimizationMasterController {

    @Resource
    private CodeOptimizationMasterService codeOptimizationMasterService;
    @Resource
    private ConversationService conversationService;
    @Resource
    private AiChatCacheService aiChatCacheService;

    @PostMapping("/addMessage")
    public ResponseEntity<RestCommon<Object>> addMessage(@RequestBody AiChatVo vo) throws Exception {
        log.info("/ai/addMessage conversation id: {}", vo.getId());
        if (!StringUtils.hasText(vo.getMessage())) {
            log.error("msg is null or empty");
            return RestCommon.missingParam();
        }
        Long conversationId = StringUtils.hasText(vo.getId())
                ? Long.valueOf(vo.getId()) : null;
        if (conversationId == null || !conversationService.exists(conversationId)) {
            log.error("conversation id is null or exists!");
            return RestCommon.fail("conversation id not exists!");
        }
        // 写入缓存
        aiChatCacheService.saveMessage(conversationId, vo.getMessage());
        return RestCommon.ok(null);
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat(@RequestParam("id") String id) {
        Optional<String> message = aiChatCacheService.getMessage(Long.valueOf(id));
        if (message.isEmpty() || !StringUtils.hasText(message.get()))
            return Flux.just(
                    ServerSentEvent.<String>builder()
                            .event("error")
                            .data(Json.toJson(RestCommon.fail("conversation id not exists!")))
                            .build());

        return codeOptimizationMasterService.chat(Long.valueOf(id), message.get())
                .map(chuck -> ServerSentEvent.<String>builder()
                        .event("message")
                        .data(chuck)
                        .build())
                .concatWith(Flux.just(   // “结束”事件
                        ServerSentEvent.<String>builder()
                                .event("close")   // 前端监听这个事件
                                .data("[DONE]")
                                .build()
                ))
                .onErrorResume(e -> {
                    log.error("chat SEE error: ", e);
                    return Flux.just(ServerSentEvent.<String>builder()
                            .event("error")
                            .data("服务异常：" + e.getMessage())
                            .build());
                });
    }

}
