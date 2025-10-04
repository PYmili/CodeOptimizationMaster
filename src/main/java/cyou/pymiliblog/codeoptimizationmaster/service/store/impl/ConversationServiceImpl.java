package cyou.pymiliblog.codeoptimizationmaster.service.store.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyou.pymiliblog.codeoptimizationmaster.entity.store.ConversationEntity;
import cyou.pymiliblog.codeoptimizationmaster.mapper.store.ConversationMapper;
import cyou.pymiliblog.codeoptimizationmaster.service.store.ConversationService;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, ConversationEntity>
        implements ConversationService {

    @Override
    public boolean exists(Long id) {
        LambdaQueryWrapper<ConversationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConversationEntity::getId, id);
        return this.exists(wrapper);
    }

}
