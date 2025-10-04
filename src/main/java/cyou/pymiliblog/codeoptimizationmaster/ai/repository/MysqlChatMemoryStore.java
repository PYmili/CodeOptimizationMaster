package cyou.pymiliblog.codeoptimizationmaster.ai.repository;

import cyou.pymiliblog.codeoptimizationmaster.service.store.MessageService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class MysqlChatMemoryStore implements ChatMemoryStore {

    @Resource
    private MessageService service;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        log.info("get messages memory id: {}", memoryId);
        return service.getMessages((Long) memoryId);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        boolean updateMessages = service.updateMessages((Long) memoryId, list);
        log.info("update messages is {}", updateMessages);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        boolean deleted = service.deleteMessages((Long) memoryId);
        log.info("delete messages is {}", deleted);
    }

}
