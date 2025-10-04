package cyou.pymiliblog.codeoptimizationmaster.ai;

import cyou.pymiliblog.codeoptimizationmaster.ai.service.CodeOptimizationMasterService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("local")
class CodeOptimizationMasterServiceTest {

    @Resource
    private CodeOptimizationMasterService service;

    @Test
    void chat() {
        // System.out.println(service.chat("你好！"));
    }
}