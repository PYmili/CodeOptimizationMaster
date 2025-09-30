package cyou.pymiliblog.codeoptimizationmaster;

import cyou.pymiliblog.codeoptimizationmaster.ai.CodeOptimizationMaster;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@SpringBootTest
class CodeOptimizationMasterApplicationTests {

    @Resource
    private CodeOptimizationMaster codeOptimizationMaster;

    @Test
    void contextLoads() {
    }

    @Test
    void chat() {
        // String response = codeOptimizationMaster.chat("你好");
        // System.out.println(response);
        // System.out.println(codeOptimizationMaster.chat(UserMessage.from(
        //         TextContent.from("描述这张图片信息"),
        //         ImageContent.from("https://k.sinaimg.cn/n/sinakd20100/560/w1080h1080/20230424/ddb1-644aff71006cd9021e6c5f10dbf0375d.jpg/w700d1q75cms.jpg")
        // )));
        System.out.println(codeOptimizationMaster.chat("""
                ```python
                n = 10
                res = 0
                for i in range(n):
                    for j in range(i, n):
                        res += j
                print(res)
                ```
                帮我优化它
                """));
    }
}
