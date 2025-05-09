package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.pojo.po.Role;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "添加角色",description = "传入角色信息")
    @PostMapping("/addSysRole")
    public Result addSysRole(@RequestBody Role role) {

        return  null;

    }
}
