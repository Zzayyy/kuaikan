package cn.kshost.fastview.backend.service.impl;
import cn.kshost.fastview.backend.mapper.UserRoleMapper;
import cn.kshost.fastview.backend.pojo.po.UserRole;
import cn.kshost.fastview.backend.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联表 服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
