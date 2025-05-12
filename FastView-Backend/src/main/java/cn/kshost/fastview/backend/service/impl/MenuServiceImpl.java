package cn.kshost.fastview.backend.service.impl;
import cn.kshost.fastview.backend.mapper.MenuMapper;
import cn.kshost.fastview.backend.pojo.po.Menu;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getChildren(Long parentId) {
        return this.list(lambdaQuery()
                .eq(Menu::getParentId, parentId)
                .eq(Menu::getIsDelete, 0)
                .eq(Menu::getStatus, 1)
                .orderByAsc(Menu::getOrderNum));
    }

}
