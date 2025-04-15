package cn.kshost.fastview.backend.security;

import cn.kshost.fastview.backend.mapper.UserMapper;
import cn.kshost.fastview.backend.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoginUserDetailService implements UserDetailsService {

    @Autowired
   private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);

        User user = userMapper.selectOne(wrapper);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }
        LoginUserDetail loginUserDetail = new LoginUserDetail(user);



        return loginUserDetail;
    }
}
