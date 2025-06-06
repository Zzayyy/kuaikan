package cn.kshost.fastview.backend.service;

import cn.kshost.fastview.backend.pojo.dto.UserQueryDto;
import cn.kshost.fastview.backend.pojo.dto.UserRoleIdsDto;
import cn.kshost.fastview.backend.pojo.po.MenuItem;
import cn.kshost.fastview.backend.pojo.po.User;
import cn.kshost.fastview.backend.pojo.vo.LoginUserVo;
import cn.kshost.fastview.backend.security.LoginUserDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-11
 */
public interface IUserService extends IService<User> {

    LoginUserVo login(User user);

    List<MenuItem> getMenuItemList(LoginUserDetail loginUserDetail);

    LoginUserVo refreshToken(String refreshToken,String accessToken);

    Page<User> getAllSysUsers(UserQueryDto userQueryDto);

    void deleteByIds(List<Integer> ids);

    void addSysUser(User user);

    List<Long> getRoleIdsByUserId(Long userId);

    void modifyUserRole(UserRoleIdsDto userRoleIdsDto);

    void modifySysUserById(User user);

    User getSysUserById(Integer id);
}
