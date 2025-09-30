package cyou.pymiliblog.codeoptimizationmaster.ai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeOptimizationMasterFactory {

    @Resource
    private ChatLanguageModel model;

    @Bean
    public CodeOptimizationMasterService codeOptimizationMasterService() {
        return AiServices.create(CodeOptimizationMasterService.class, model);
    }

}
