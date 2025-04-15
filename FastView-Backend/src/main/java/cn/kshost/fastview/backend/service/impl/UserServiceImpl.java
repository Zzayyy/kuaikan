package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.mapper.UserMapper;
import cn.kshost.fastview.backend.pojo.User;
import cn.kshost.fastview.backend.pojo.vo.LoginUserVo;
import cn.kshost.fastview.backend.security.LoginUserDetail;
import cn.kshost.fastview.backend.service.IUserService;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    AuthenticationManager authenticationManager;
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
        return null;
    }
}
