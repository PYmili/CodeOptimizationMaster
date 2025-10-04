package cyou.pymiliblog.codeoptimizationmaster.controller.ai;

import cyou.pymiliblog.codeoptimizationmaster.ai.service.CodeOptimizationMasterService;
import cyou.pymiliblog.codeoptimizationmaster.common.RestCommon;
import cyou.pymiliblog.codeoptimizationmaster.service.store.ConversationService;
import dev.langchain4j.internal.Json;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/ai")
public class CodeOptimizationMasterController {

    @Resource
    private CodeOptimizationMasterService codeOptimizationMasterService;
    @Resource
    private ConversationService conversationService;

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat(@RequestParam("id") Long id, @RequestParam("msg") String message) {
        if (!StringUtils.hasText(message)) {
            log.error("msg is null or empty");
            return Flux.just(
                    ServerSentEvent.<String>builder()
                            .data(RestCommon.missingParam().toString())
                            .event("error")
                            .build()
            );
        }

        if (!conversationService.exists(id)) {
            return Flux.just(
                    ServerSentEvent.<String>builder()
                            .event("error")
                            .data(Json.toJson(RestCommon.fail("conversation id not exists!")))
                            .build());
        }

        return codeOptimizationMasterService.chat(id, message)
                .map(chuck -> ServerSentEvent.<String>builder()
                        .event("message")
                        .data(chuck)
                        .build())
                .onErrorResume(e -> {
                    log.error("chat SEE error: ", e);
                    return Flux.just(ServerSentEvent.<String>builder()
                            .event("error")
                            .data("服务异常：" + e.getMessage())
                            .build());
                });
    }

}
