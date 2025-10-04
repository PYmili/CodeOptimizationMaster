package cyou.pymiliblog.codeoptimizationmaster.controller.conversation;

import cyou.pymiliblog.codeoptimizationmaster.common.RestCommon;
import cyou.pymiliblog.codeoptimizationmaster.entity.store.ConversationEntity;
import cyou.pymiliblog.codeoptimizationmaster.entity.store.MessageEntity;
import cyou.pymiliblog.codeoptimizationmaster.service.store.ConversationService;
import cyou.pymiliblog.codeoptimizationmaster.service.store.MessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Resource
    private ConversationService conversationService;
    @Resource
    private MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<RestCommon<Object>> create(@RequestParam String title) {
        if (!StringUtils.hasText(title)) return RestCommon.missingParam();

        // 创建会话
        ConversationEntity entity = new ConversationEntity(title);
        if (!conversationService.save(entity))
            return RestCommon.fail("create conversation fail!");

        // 创建消息记忆
        if (!messageService.save(new MessageEntity(entity.getId())))
            return RestCommon.fail("create message fail!");

        return RestCommon.ok(entity.getId());
    }

}
