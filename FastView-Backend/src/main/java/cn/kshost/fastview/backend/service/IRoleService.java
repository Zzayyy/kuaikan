package cn.kshost.fastview.backend.service;

import cn.kshost.fastview.backend.pojo.dto.RoleQueryDto;
import cn.kshost.fastview.backend.pojo.po.Role;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
public interface IRoleService extends IService<Role> {

    void addSysRole(Role role);

    Role getSysRoleById(Integer id);

    Page<Role> getRoleListPage(RoleQueryDto roleQueryDto);
}
