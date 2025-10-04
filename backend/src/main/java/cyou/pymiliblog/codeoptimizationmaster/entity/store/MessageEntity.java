package cyou.pymiliblog.codeoptimizationmaster.entity.store;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "message", autoResultMap = true)
public class MessageEntity implements Serializable {

    /** 主键；与表自增主键对应，这里用雪花即可，保持与conversation一致 */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 所属会话ID */
    @TableField("conversation_id")
    private Long conversationId;

    /** 消息内容（JSON） */
    @TableField("messages")
    private String messages;

    /** 创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    public MessageEntity(Long conversationId) {
        this.conversationId = conversationId;
    }
}