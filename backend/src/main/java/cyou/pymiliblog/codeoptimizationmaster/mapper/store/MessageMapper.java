package cyou.pymiliblog.codeoptimizationmaster.mapper.store;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cyou.pymiliblog.codeoptimizationmaster.entity.store.MessageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<MessageEntity> {
}
