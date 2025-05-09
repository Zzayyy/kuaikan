package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.dto.RoleQueryDto;
import cn.kshost.fastview.backend.pojo.po.Role;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    IRoleService roleService;
    /**
     * 添加用户
     * @param role
     * @return
     */
    @Operation(summary = "添加角色")
    @PostMapping("/addSysRole")
    public Result addSysRole(@RequestBody Role role) {
        roleService.addSysRole(role);
        return  Result.success(FastViewEnum.ADD_SUCCESS);
    }

    @Operation(summary = "根据id查询角色信息")
    @GetMapping("/getSysRoleById/{id}")
    public Result getSysRoleById(@PathVariable Integer id) {
       Role role =   roleService.getSysRoleById(id);
       return Result.success(FastViewEnum.ADD_SUCCESS, role);
    }

    @Operation(summary = "模糊条件分页查询角色")
    @PostMapping("/getRoleListPage")
    public Result getRoleListPage(@RequestBody RoleQueryDto roleQueryDto) {

        Page<Role>  rolePage = roleService.getRoleListPage(roleQueryDto);
        return Result.success(FastViewEnum.QUERY_SUCCESS, rolePage);
    }
}
