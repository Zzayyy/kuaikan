package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.pojo.MenuItem;
import cn.kshost.fastview.backend.pojo.User;
import cn.kshost.fastview.backend.pojo.vo.LoginUserVo;
import cn.kshost.fastview.backend.service.IUserService;
import cn.kshost.fastview.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-11
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
public class UserController {


    @Autowired
    private IUserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody User user) {

        LoginUserVo loginUserVo=  userService.login(user);
       if (!Objects.isNull(loginUserVo)) {
           return Result.success("success",loginUserVo);
       }
           return Result.error("用户名或密码错误",null);
    }

    @Operation(summary = "根据token获取路由")
    @GetMapping("/getAsyncRoutes")
    public Result getAsyncRoutes(HttpServletRequest request) {
        List<MenuItem> menuList = new ArrayList<>();
        MenuItem permission = new MenuItem();
        permission.setPath("/permission");
        permission.setName("权限管理");
        permission.setMeta(Map.of("title", "权限管理"));
        permission.setChildren(List.of(
                new MenuItem("/permission/page/index", "PermissionPage", Map.of("title", "页面权限"), new ArrayList<>()),
                new MenuItem("/permission/button/index", "PermissionuttonPage", Map.of("title", "按钮权限"), new ArrayList<>())
        ));
        MenuItem hero = new MenuItem();
        hero.setPath("/hero");
        hero.setName("hero");
        hero.setMeta(Map.of("title", "英雄联盟"));
        hero.setChildren(List.of(
                new MenuItem("/hero/heroManager/index", "heroManager", Map.of("title", "英雄管理"), new ArrayList<>()),
                new MenuItem("/hero/skinManage/index", "skinManage", Map.of("title", "皮肤管理"), new ArrayList<>())
        ));
        menuList.add(permission);
        menuList.add(hero);
        return Result.success("success", menuList);


    }







}
