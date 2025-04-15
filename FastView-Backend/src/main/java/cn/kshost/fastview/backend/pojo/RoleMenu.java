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
 * 角色-菜单关联表
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Getter
@Setter
@ToString
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID（关联sys_role.id）
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单ID（关联sys_menu.id）
     */
    @TableField("menu_id")
    private Long menuId;

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
