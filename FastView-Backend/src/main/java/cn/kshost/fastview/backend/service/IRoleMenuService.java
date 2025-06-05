package cn.kshost.fastview.backend.service;
import cn.kshost.fastview.backend.pojo.dto.RoleMenuIdsDto;
import cn.kshost.fastview.backend.pojo.po.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色-菜单关联表 服务类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<Long> getMenuIdsByRoleId(Integer roleId);

    void modifyRoleMenuByRoleId(RoleMenuIdsDto roleMenuIdsDto);
}
