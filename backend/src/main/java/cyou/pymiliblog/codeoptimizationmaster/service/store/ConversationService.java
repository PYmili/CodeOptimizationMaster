package cyou.pymiliblog.codeoptimizationmaster.service.store;

import com.baomidou.mybatisplus.extension.service.IService;
import cyou.pymiliblog.codeoptimizationmaster.entity.store.ConversationEntity;

public interface ConversationService extends IService<ConversationEntity> {

    boolean exists(Long id);

}
