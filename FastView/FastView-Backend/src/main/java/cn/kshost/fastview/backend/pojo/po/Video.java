package cn.kshost.fastview.backend.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 视频表
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Getter
@Setter
@ToString
@TableName("video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视频标题
     */
    @TableField("title")
    private String title;

    /**
     * 视频描述
     */
    @TableField("descr")
    private String descr;

    /**
     * 封面图片URL
     */
    @TableField("cover_url")
    private String coverUrl;

    /**
     * 视频文件URL
     */
    @TableField("video_url")
    private String videoUrl;

    /**
     * 点赞数
     */
    @TableField("stars")
    private Integer stars;

    /**
     * 创建人ID（关联sys_user.id）
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 状态：1 正常，0 禁用
     */
    @TableField("status")
    private Byte status;

    /**
     * 逻辑删除：0 未删除，1 已删除
     */
    @TableField("is_delete")
    private Byte isDelete;

    /**
     * 分类ID（关联video_category.id
     */
    @TableField("category_id")
    private Long categoryId;

    @TableField("category_name")
    private String categoryName;

}
