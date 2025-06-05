package cn.kshost.fastview.backend.service.impl;
import cn.kshost.fastview.backend.mapper.RoleMenuMapper;
import cn.kshost.fastview.backend.pojo.dto.RoleMenuIdsDto;
import cn.kshost.fastview.backend.pojo.po.Role;
import cn.kshost.fastview.backend.pojo.po.RoleMenu;
import cn.kshost.fastview.backend.service.IRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色-菜单关联表 服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<Long> getMenuIdsByRoleId(Integer roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId).eq(RoleMenu::getIsDelete, 0).eq(RoleMenu::getStatus, 1);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
       return roleMenus.stream().map(roleMenu -> roleMenu.getMenuId() ).collect(Collectors.toList());
    }
    /**
     * 通过角色id设置菜单id
     * @param roleMenuIdsDto
     */
    @Override
    public void modifyRoleMenuByRoleId(RoleMenuIdsDto roleMenuIdsDto) {
        //根据角色id删除所有记录
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleMenuIdsDto.getRoleId());
        int i = roleMenuMapper.delete(wrapper);

        ArrayList<RoleMenu> roleMenuList = new ArrayList<>();
        //构建数据
        roleMenuIdsDto.getMenuIds().forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleMenuIdsDto.getRoleId());
            roleMenu.setMenuId(menuId);
            roleMenu.setCreateTime(LocalDateTime.now());
            roleMenu.setUpdateTime(LocalDateTime.now());
            roleMenuList.add(roleMenu);
        });
        //插入id和角色列表
        roleMenuMapper.insert(roleMenuList);
    }
}
