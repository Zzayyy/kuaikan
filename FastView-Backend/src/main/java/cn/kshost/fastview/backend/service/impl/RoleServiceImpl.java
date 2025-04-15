package cn.kshost.fastview.backend.service.impl;


import cn.kshost.fastview.backend.mapper.RoleMapper;
import cn.kshost.fastview.backend.pojo.Role;
import cn.kshost.fastview.backend.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
