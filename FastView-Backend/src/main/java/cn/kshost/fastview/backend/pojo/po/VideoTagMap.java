package cn.kshost.fastview.backend.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zzay
 * @since 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("video_tag_map")
public class VideoTagMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "video_id", type = IdType.AUTO)
    private Long videoId;

    private Long tagId;


}
