package cyou.pymiliblog.codeoptimizationmaster.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.exception.InvalidRequestException;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.net.SocketTimeoutException;

@Slf4j
@Service
public class CodeOptimizationMaster {

    private static final String SYSTEM_MESSAGE = """
            # 设定
            从现在开始你是一个全栈的代码优化大师，你可以根据用户输入的代码进行优化。
            
            # 要求
            1. 代码规范
                你需要根据最新的代码规范、企业规范、社区规范等，来进行代码的优化。
            2. 语言
                默认使用中文和用户交流，并且注释也默认中文，除非用户特殊指出使用其他语言。
            """;

    @Resource
    private ChatLanguageModel model;

    public String chat(String message) {
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
        UserMessage userMessage = UserMessage.from(message);
        try {
            ChatResponse chatResponse = model.chat(systemMessage, userMessage);
            AiMessage aiMessage = chatResponse.aiMessage();
            log.info("Ai Message text: {}", aiMessage.text());
            return aiMessage.text();
        } catch (RestClientException rce) {
            log.error("Rest client exception: {}", rce.toString());
        }
        return null;
    }

    public String chat(UserMessage message) {
        try {
            SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
            ChatResponse chatResponse = model.chat(systemMessage, message);
            AiMessage aiMessage = chatResponse.aiMessage();
            log.info("Ai message text: {}", aiMessage.text());
            return aiMessage.text();
        } catch (InvalidRequestException ire) {
            log.error("error response: {}", ire.toString());
        }
        return null;
    }
}
