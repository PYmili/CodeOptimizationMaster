package cyou.pymiliblog.codeoptimizationmaster.ai;

import dev.langchain4j.service.SystemMessage;

public interface CodeOptimizationMasterService {

    @SystemMessage(fromResource = "/prompts/system.md")
    String chat(String message);

}
