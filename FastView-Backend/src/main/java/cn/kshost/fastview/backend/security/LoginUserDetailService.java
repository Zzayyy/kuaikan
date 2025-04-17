package cn.kshost.fastview.backend.security;

import cn.kshost.fastview.backend.mapper.RoleMapper;
import cn.kshost.fastview.backend.mapper.UserMapper;
import cn.kshost.fastview.backend.mapper.UserRoleMapper;
import cn.kshost.fastview.backend.pojo.Role;
import cn.kshost.fastview.backend.pojo.User;
import cn.kshost.fastview.backend.pojo.UserRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LoginUserDetailService implements UserDetailsService {

    @Autowired
   private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }

        //查询用户的角色角色列表
        List<UserRole> userRoleList = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()).eq(UserRole::getStatus,1).eq(UserRole::getIsDelete,0));
        List<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, roleIds).eq(Role::getStatus, 1).eq(Role::getIsDelete, 0));
        List<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        //创建信息用户信息和角色列表给springSecurity授权
        LoginUserDetail loginUserDetail = new LoginUserDetail(roleNames,user);
        return loginUserDetail;
    }
}
