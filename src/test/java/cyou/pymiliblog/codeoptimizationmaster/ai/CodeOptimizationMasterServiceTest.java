package cyou.pymiliblog.codeoptimizationmaster.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("local")
class CodeOptimizationMasterServiceTest {

    @Resource
    private CodeOptimizationMasterService service;

    @Test
    void chat() {
        System.out.println(service.chat("你好！"));
    }
}