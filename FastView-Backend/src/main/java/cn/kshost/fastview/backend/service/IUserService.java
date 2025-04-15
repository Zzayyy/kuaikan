package cn.kshost.fastview.backend.service;

import cn.kshost.fastview.backend.pojo.User;
import cn.kshost.fastview.backend.pojo.vo.LoginUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
