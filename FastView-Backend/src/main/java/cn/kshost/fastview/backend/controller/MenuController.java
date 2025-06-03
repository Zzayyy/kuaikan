package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.po.Menu;
import cn.kshost.fastview.backend.pojo.po.MenuItem;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IMenuService;
import cn.kshost.fastview.backend.util.MenuUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 根据id获取菜单
     * @param id
     * @return
     */
    @Operation(summary = "根据id获取菜单")
    @GetMapping("/{id}")
    public Result<Menu> getMenu(@PathVariable Long id) {
        return Result.success(FastViewEnum.QUERY_SUCCESS,menuService.getById(id));
    }


    /**
     * 根据父ID获取子菜单列表
     */
    @Operation(summary = "根据父ID获取子菜单列表")
    @GetMapping("/getChildren/{parentId}")
    public Result<List<Menu>> getChildren(@PathVariable Long parentId) {
        List<Menu> children = menuService.getChildren(parentId);
        return Result.success(FastViewEnum.QUERY_SUCCESS,children); // 假设你使用的是 Result.success() 封装
    }

    /**
     * 获取所有菜单列表
     */
    @GetMapping("/list")
    public Result<List<MenuItem>> listMenus() {
        List<Menu> list = menuService.list(menuService.lambdaQuery()
                .eq(Menu::getIsDelete, 0)
                .orderByAsc(Menu::getOrderNum));
        //构建菜单树
        List<MenuItem> menuItems = MenuUtil.buildMenuTree(list);
        return Result.success(FastViewEnum.QUERY_SUCCESS,menuItems);
    }

    /**
     *
     */
    @Operation(summary = "新增菜单")
    @PostMapping("addMenu")
    public Result<String> addMenu(@RequestBody Menu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menu.setIsDelete((byte) 0);
        boolean result = menuService.save(menu);
        return result ? Result.success(FastViewEnum.ADD_SUCCESS) : Result.error(FastViewEnum.ADD_ERROR);
    }

    /**
     * 修改菜单
     */
    @Operation(summary = "修改菜单")
    @PostMapping("/modifyMenuById")
    public Result modifyMenuById(@RequestBody Menu menu) {
        menu.setUpdateTime(LocalDateTime.now());
        menu.setIsDelete((byte) 0);
        menuService.updateById(menu);
        return Result.success(FastViewEnum.MODIFY_SUCCESS);
    }

    /**
     * 删除菜单
     */
    @Operation(summary = "根据id删除菜单")
    @PostMapping("/removeMenuById/{id}")
    public Result removeMenuById(Integer id){
       menuService.removeById(id);
       return Result.success(FastViewEnum.DELETE_SUCCESS);
    }



}
