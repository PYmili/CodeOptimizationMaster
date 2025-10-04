package cyou.pymiliblog.codeoptimizationmaster.service.store;

import com.baomidou.mybatisplus.extension.service.IService;
import cyou.pymiliblog.codeoptimizationmaster.entity.store.MessageEntity;
import dev.langchain4j.data.message.ChatMessage;

import java.util.List;

public interface MessageService extends IService<MessageEntity> {

    List<ChatMessage> getMessages(Long conversationId);

    boolean updateMessages(Long conversationId, List<ChatMessage> messages);

    boolean deleteMessages(Long conversationId);

}
