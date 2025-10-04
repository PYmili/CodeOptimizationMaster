package cyou.pymiliblog.codeoptimizationmaster.ai.service.impl.factory;

import cyou.pymiliblog.codeoptimizationmaster.ai.repository.MysqlChatMemoryStore;
import cyou.pymiliblog.codeoptimizationmaster.ai.service.CodeOptimizationMasterService;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeOptimizationMasterFactory {

    @Resource
    private OpenAiStreamingChatModel streamingChatModel;
    @Resource
    private MysqlChatMemoryStore memoryStore;
    @Resource
    private ContentRetriever contentRetriever;

    @Bean
    public CodeOptimizationMasterService codeOptimizationMasterService() {
        // 创建Service
        return AiServices.builder(CodeOptimizationMasterService.class)
                // 会话记忆
                .chatMemoryProvider(id -> MessageWindowChatMemory.builder()
                        .id(id)
                        .maxMessages(20)
                        .chatMemoryStore(memoryStore)
                        .build())
                .streamingChatModel(streamingChatModel)
                .contentRetriever(contentRetriever)
                .build();
    }

}
