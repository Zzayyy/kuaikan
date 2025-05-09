package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.exception.DataException;
import cn.kshost.fastview.backend.mapper.RoleMapper;
import cn.kshost.fastview.backend.pojo.dto.RoleQueryDto;
import cn.kshost.fastview.backend.pojo.po.Role;
import cn.kshost.fastview.backend.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void addSysRole(Role role) {
        if (Objects.nonNull(role)) {
            //判断逻辑为空
            role.setIsDelete(0);
            role.setStatus(1);
            role.setCreateTime(LocalDateTime.now());
            role.setCreateTime(LocalDateTime.now());
            roleMapper.insert(role);
            return;
        }
        throw  new  DataException(FastViewEnum.DATA_ERROR);
    }

    @Override
    public Role getSysRoleById(Integer id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId, id);
        wrapper.eq(Role::getIsDelete, 0);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public Page<Role> getRoleListPage(RoleQueryDto roleQueryDto) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<Role>();
        wrapper.eq(Role::getIsDelete, 0);
        if (roleQueryDto.getRoleName() != null && !"".equals(roleQueryDto.getRoleName())) {
            wrapper.like(Role::getRoleName, roleQueryDto.getRoleName());
        }
        if (roleQueryDto.getStatus() != null) {
            wrapper.eq(Role::getStatus, roleQueryDto.getStatus());
        }
        Page<Role> rolePage = new Page<>(roleQueryDto.getPageNum(), roleQueryDto.getPageSize());
        roleMapper.selectPage(rolePage, wrapper);
        return  rolePage;

    }
}
