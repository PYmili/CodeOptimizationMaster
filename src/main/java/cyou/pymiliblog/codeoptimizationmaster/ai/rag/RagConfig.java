package cyou.pymiliblog.codeoptimizationmaster.ai.rag;

import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RagConfig {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private RedisEmbeddingStore embeddingStore;

    @Bean
    public ContentRetriever contentRetriever() {
        // 删除所有文档
        embeddingStore.removeAll();

        // 加载文档
        List<Document> documents = FileSystemDocumentLoader
                .loadDocuments("src/main/resources/documents");

        // 文档拆分器配置
        DocumentByParagraphSplitter splitter =
                new DocumentByParagraphSplitter(1000, 200);

        // 插入文档到redis-vector
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .textSegmentTransformer(textSegment ->
                        TextSegment.from(
                                textSegment.metadata().getString("file_name")
                                        + "\n" + textSegment.text(),
                                textSegment.metadata()
                        )
                )
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(documents);

        // 创建内容检索
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .maxResults(5)
                .minScore(0.75)
                .build();
    }

}
