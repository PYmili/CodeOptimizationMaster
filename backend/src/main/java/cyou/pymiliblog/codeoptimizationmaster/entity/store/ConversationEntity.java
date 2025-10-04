package cyou.pymiliblog.codeoptimizationmaster.entity.store;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "conversation", autoResultMap = true)
public class ConversationEntity implements Serializable {

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 标题 */
    @TableField("title")
    private String title;

    /** 创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    public ConversationEntity(String title) {
        this.title = title;
    }
}
