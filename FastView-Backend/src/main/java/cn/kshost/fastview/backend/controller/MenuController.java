package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.po.Menu;
import cn.kshost.fastview.backend.pojo.po.MenuItem;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IMenuService;
import cn.kshost.fastview.backend.util.MenuUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    @Operation(summary = "获取所有菜单树")
    @GetMapping("/list")
    public Result<List<MenuItem>> listMenus() {

        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getIsDelete, 0)
                .orderByAsc(Menu::getOrderNum);
        List<Menu> list = menuService.list(wrapper);

        //构建菜单树
        List<MenuItem> menuItems = MenuUtil.buildMenuTree(list);
        return Result.success(FastViewEnum.QUERY_SUCCESS,menuItems);
    }

    /**
     * 获取所有菜单列表
     */
    @Operation(summary = "获取所有启用菜单树 status=1")
    @GetMapping("/listActivityMenus")
    public Result<List<MenuItem>> listActivityMenus() {

        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getIsDelete, 0)
                .eq(Menu::getStatus, 1)
                .orderByAsc(Menu::getOrderNum);
        List<Menu> list = menuService.list(wrapper);

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
        HashMap<String, String> meta = new HashMap<>();
        meta.put("showParent", "true");
        meta.put("title", menu.getMeta());

        menu.setMeta(JSON.toJSONString(meta));
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
        //构建meta
        HashMap<String, String> meta = new HashMap<>();
        meta.put("showParent", "true");
        meta.put("title", menu.getMeta());

        menu.setMeta(JSON.toJSONString(meta));

        menuService.updateById(menu);
        return Result.success(FastViewEnum.MODIFY_SUCCESS);
    }

    /**
     * 删除菜单
     */
    @Operation(summary = "根据菜单id列表删除菜单")
    @PostMapping("/removeMenuByIds")
    public Result removeMenuById(@RequestBody List<Long> ids){
       menuService.removeByIds(ids);
       return Result.success(FastViewEnum.DELETE_SUCCESS);
    }



}
