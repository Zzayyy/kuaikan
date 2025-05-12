package cn.kshost.fastview.backend.service;
import cn.kshost.fastview.backend.pojo.po.Menu;
import cn.kshost.fastview.backend.pojo.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getChildren(Long parentId);
}
