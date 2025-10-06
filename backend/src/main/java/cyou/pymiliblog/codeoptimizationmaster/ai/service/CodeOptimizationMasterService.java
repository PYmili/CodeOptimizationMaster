package cyou.pymiliblog.codeoptimizationmaster.ai.service;

import dev.langchain4j.service.*;
import reactor.core.publisher.Flux;

public interface CodeOptimizationMasterService {

    @SystemMessage(fromResource = "/prompts/system.md")
    Flux<String> chat(@MemoryId Object id, @UserMessage String message);

    @SystemMessage(fromResource = "/prompts/system.md")
    Result<String> chatWithRag(@MemoryId Object id, @UserMessage String userMessage);

}
