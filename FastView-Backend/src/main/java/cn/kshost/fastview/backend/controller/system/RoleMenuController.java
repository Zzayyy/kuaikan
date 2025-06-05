package cn.kshost.fastview.backend.controller.system;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.dto.RoleMenuIdsDto;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色-菜单关联表 前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */

@Tag(name = "角色菜单关联接口")
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Autowired
    IRoleMenuService roleMenuService;

    /**
     * 根据角色id获取菜单id列表
     */
    @Operation(summary = "根据角色id获取菜单id列表")
    @PostMapping("/getMenuIdsByRoleId/{roleId}")
    public Result<List<Long>> getMenuIdsByRoleId(@PathVariable Integer roleId) {
        List<Long> menuIds  = roleMenuService.getMenuIdsByRoleId(roleId);
        return Result.success(menuIds);
    }

    /**
     * 给角色设置对应菜单
     */
    @Operation(summary = "给角色设置对应菜单")
    @PostMapping("/modifyRoleMenuByRoleId")
    public Result modifyRoleMenuByRoleId(@RequestBody RoleMenuIdsDto roleMenuIdsDto) {
        roleMenuService.modifyRoleMenuByRoleId(roleMenuIdsDto);
        return Result.success(FastViewEnum.MODIFY_SUCCESS);

    }
    

}
