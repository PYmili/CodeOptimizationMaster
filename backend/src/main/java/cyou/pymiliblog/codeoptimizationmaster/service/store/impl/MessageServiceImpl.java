package cyou.pymiliblog.codeoptimizationmaster.service.store.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyou.pymiliblog.codeoptimizationmaster.entity.store.MessageEntity;
import cyou.pymiliblog.codeoptimizationmaster.mapper.store.MessageMapper;
import cyou.pymiliblog.codeoptimizationmaster.service.store.MessageService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity>
        implements MessageService {

    @Override
    public List<ChatMessage> getMessages(Long conversationId) {
        LambdaQueryWrapper<MessageEntity> wrapper = createLambdaQueryWrapper(conversationId);
        // 根据创建时间排序
        wrapper.orderByAsc(MessageEntity::getCreateTime);

        MessageEntity entity = this.getOne(wrapper);

        log.info("entity: {}", entity);

        if (entity == null || entity.getMessages().isEmpty())
            return List.of();
        return ChatMessageDeserializer.messagesFromJson(entity.getMessages());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMessages(Long conversationId, List<ChatMessage> messages) {
        String json = ChatMessageSerializer.messagesToJson(messages);
        MessageEntity entity = new MessageEntity();
        entity.setConversationId(conversationId);
        entity.setMessages(json);
        return this.update(entity, createLambdaQueryWrapper(conversationId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMessages(Long conversationId) {
        return this.remove(createLambdaQueryWrapper(conversationId));
    }

    private LambdaQueryWrapper<MessageEntity> createLambdaQueryWrapper(Long conversationId) {
        LambdaQueryWrapper<MessageEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageEntity::getConversationId, conversationId);
        return wrapper;
    }
}
