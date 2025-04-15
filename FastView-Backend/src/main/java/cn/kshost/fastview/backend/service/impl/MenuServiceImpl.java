package cn.kshost.fastview.backend.service.impl;
import cn.kshost.fastview.backend.mapper.MenuMapper;
import cn.kshost.fastview.backend.pojo.Menu;
import cn.kshost.fastview.backend.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
