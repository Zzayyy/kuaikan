package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.exception.DataException;
import cn.kshost.fastview.backend.exception.LoginException;
import cn.kshost.fastview.backend.exception.TokenException;
import cn.kshost.fastview.backend.mapper.MenuMapper;
import cn.kshost.fastview.backend.mapper.RoleMenuMapper;
import cn.kshost.fastview.backend.mapper.UserMapper;
import cn.kshost.fastview.backend.mapper.UserRoleMapper;
import cn.kshost.fastview.backend.pojo.dto.UserQueryDto;
import cn.kshost.fastview.backend.pojo.dto.UserRoleIdsDto;
import cn.kshost.fastview.backend.pojo.po.*;
import cn.kshost.fastview.backend.pojo.vo.LoginUserVo;
import cn.kshost.fastview.backend.security.LoginUserDetail;
import cn.kshost.fastview.backend.service.IUserService;
import cn.kshost.fastview.backend.util.MenuUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuServiceImpl roleMenuServiceImpl;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public LoginUserVo login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        if (!Objects.isNull(authenticate)) {
            String accessToken = UUID.randomUUID().toString().replace("-", "");
            String refreshToken = UUID.randomUUID().toString().replace("-", "");
            LoginUserDetail loginUserDetail = (LoginUserDetail) authenticate.getPrincipal();
            redisTemplate.opsForValue().set("user:login:accessToken:" + accessToken, JSON.toJSONString(loginUserDetail),1, TimeUnit.HOURS);
            redisTemplate.opsForValue().set("user:login:refreshToken:"+refreshToken,accessToken ,1, TimeUnit.HOURS);
            LoginUserVo loginUserVo = LoginUserVo.builder()
                    .username(loginUserDetail.getUsername())
                    .accessToken(accessToken).refreshToken(refreshToken)
                    .avatar(loginUserDetail.getUser().getAvatar())
                    .nickname(loginUserDetail.getUser().getNickName())
                    .expires(LocalDateTime.now().plusHours(1)).
                    build();
            return loginUserVo;
        }
       throw  new LoginException(FastViewEnum.USERNAME_PASSWORD_ERROR);
    }

    @Override
    public List<MenuItem> getMenuItemList(LoginUserDetail loginUserDetail) {
        Long userId = loginUserDetail.getUser().getId();
        //获取该用户角色列表
        List<UserRole> userRolesList = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).eq(UserRole::getStatus,1).eq(UserRole::getIsDelete,0));
        //角色id提取
        List<Long> roleIdList = userRolesList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //获取该角色菜单列表
        List<RoleMenu> roleMenuList = roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, roleIdList).eq(RoleMenu::getStatus,1).eq(RoleMenu::getIsDelete,0));
        //组装菜单id 并去重
        List<Long> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());
        //获取菜单信息
        List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menuIdList).eq(Menu::getStatus,1).eq(Menu::getIsDelete,0));
        List<MenuItem> menuItemList = MenuUtil.buildMenuTree(menuList);
        return menuItemList;
    }

    @Override
    public LoginUserVo refreshToken(String refreshToken,String accessToken) {

        //根据refreshToken查看token
        String refreshTokenValue = redisTemplate.opsForValue().get("user:login:refreshToken:" + refreshToken);
        if (!Objects.isNull(refreshTokenValue) && !refreshTokenValue.equals("")) {
            //通过token值获取用户信息
            String redisUserDetailJson = redisTemplate.opsForValue().get("user:login:accessToken:" + refreshTokenValue);
            if (!Objects.isNull(redisUserDetailJson) && !redisUserDetailJson.equals("")) {
                    //创建新的token和refreshToken
                    String newAccessToken = UUID.randomUUID().toString().replace("-", "");
                    String newRefreshToken = UUID.randomUUID().toString().replace("-", "");
                    redisTemplate.opsForValue().set("user:login:refreshToken:"+newRefreshToken, newAccessToken, 1, TimeUnit.HOURS);
                    redisTemplate.opsForValue().set("user:login:accessToken:"+newAccessToken,redisUserDetailJson ,1, TimeUnit.HOURS);
                    //删除旧的token和refreshtoken
                    redisTemplate.delete("user:login:accessToken:"+accessToken);
                    redisTemplate.delete("user:login:refreshToken:" + refreshToken);
                LoginUserDetail loginUserDetail = JSON.parseObject(redisUserDetailJson, LoginUserDetail.class);
                LoginUserVo loginUserVo = LoginUserVo.builder()
                            .username(loginUserDetail.getUsername())
                            .accessToken(newAccessToken).refreshToken(newRefreshToken)
                            .avatar(loginUserDetail.getUser().getAvatar())
                            .nickname(loginUserDetail.getUser().getNickName())
                            .expires(LocalDateTime.now().plusHours(1)).
                            build();
                    return loginUserVo;

                }
        }
        //抛出刷新失败异常
        throw new TokenException(FastViewEnum.TOKEN_REFRESH_ERROR);
    }

    @Override
    public Page<User> getAllSysUsers(UserQueryDto userQueryDto) {
        Page<User> page = new Page<>(userQueryDto.getPageNum(), userQueryDto.getPageSize());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("is_delete", 0);
        userQueryWrapper.select("id","username","nick_name","phone","avatar","status","create_time","update_time");
        if (userQueryDto.getUsername() != null &&  userQueryDto.getUsername().length() > 0) {
            userQueryWrapper.like("username", userQueryDto.getUsername());
        }
        if (userQueryDto.getNickName() != null &&  userQueryDto.getNickName().length() > 0) {
            userQueryWrapper.like("nick_name", userQueryDto.getNickName());
        }
        if (userQueryDto.getPhone() != null &&  userQueryDto.getPhone().length() > 0) {
            userQueryWrapper.like("phone", userQueryDto.getPhone());
        }
        if (userQueryDto.getStatus() != null) {
            userQueryWrapper.eq("status", userQueryDto.getStatus());
        }
        Page<User> userPage = userMapper.selectPage(page, userQueryWrapper);
       return  userPage;


    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
    }

    @Override
    public void addSysUser(User user) {
        //对密码进行加密并设置
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //设置当前时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).eq(UserRole::getStatus,1).eq(UserRole::getIsDelete,0));
        return userRoles.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());
    }
    @Override
    public void modifyUserRole(UserRoleIdsDto userRoleIdsDto) {
        //根据用户id删除所有角色列表
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userRoleIdsDto.getUserId()));

        //插入角色列表
        if (userRoleIdsDto.getRoleIds() != null && !userRoleIdsDto.getRoleIds().isEmpty()) {
            userRoleMapper.insertUserRole(userRoleIdsDto);
        }
    }

    @Override
    public void modifySysUserById(User user) {

        if (!Objects.isNull(user)) {
            if (user.getPassword() != null && user.getPassword().length() > 0) {
                //加密密码
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUpdateTime(LocalDateTime.now());
                userMapper.updateById(user);
                return;
            }
        }
        throw  new DataException(FastViewEnum.DATA_ERROR);






    }
}
