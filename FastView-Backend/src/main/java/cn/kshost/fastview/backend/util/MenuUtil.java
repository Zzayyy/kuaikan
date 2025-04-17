package cn.kshost.fastview.backend.util;

import cn.kshost.fastview.backend.pojo.Menu;
import cn.kshost.fastview.backend.pojo.MenuItem;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 构建菜单树工具类
 */
public class MenuUtil {

    public static List<MenuItem> buildMenuTree(List<Menu> menuList){
        List<MenuItem> menuItemList = new ArrayList<>();
        for (Menu menu : menuList) {
           //检索一级菜单
            if (menu.getParentId() == 0) {
                //封装当前节点
               MenuItem menuItem  = buildMenuItem(menu,menuList);
               menuItemList.add(menuItem);
            }
        }
        return  menuItemList;
    }

    private static MenuItem buildMenuItem(Menu menu, List<Menu> menuList) {
        MenuItem menuItem = new MenuItem();
        BeanUtils.copyProperties(menu,menuItem);
        Map metaMap = JSON.parseObject(menu.getMeta(), Map.class);
        menuItem.setMeta(metaMap);
        menuItem.setChildren(new ArrayList<>());
        for (Menu child : menuList) {
            if (child.getParentId() == menuItem.getId()) {
                menuItem.getChildren().add(buildMenuItem(child,menuList));
            }
        }
        return menuItem;
    }
}
