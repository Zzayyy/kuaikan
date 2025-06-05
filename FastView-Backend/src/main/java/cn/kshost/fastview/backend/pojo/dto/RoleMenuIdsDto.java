package cn.kshost.fastview.backend.pojo.dto;

import lombok.Data;
import lombok.ToString;
import java.util.List;

/**
 * 接受角色id和菜单id列表 用户设置角色的菜单
 */
@Data
@ToString
public class RoleMenuIdsDto {
    private Long roleId;
    private List<Long> menuIds;
}