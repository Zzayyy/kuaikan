package cn.kshost.fastview.backend.pojo;

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
 * 菜单表
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Getter
@Setter
@ToString
@TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 路由路径
     */
    @TableField("path")
    private String path;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 元信息（JSON 格式存储）
     */
    @TableField("meta")
    private String meta;

    /**
     * 父级菜单ID，0 表示一级菜单
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 排序值，越小越靠前
     */
    @TableField("order_num")
    private Integer orderNum;

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
}
