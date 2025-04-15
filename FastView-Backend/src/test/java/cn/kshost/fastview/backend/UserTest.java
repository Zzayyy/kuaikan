package cn.kshost.fastview.backend;

import cn.kshost.fastview.backend.mapper.UserMapper;
import cn.kshost.fastview.backend.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class UserTest {
    @Autowired
    UserMapper userMapper;
    @Test
    public void insetUserTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("Qq2548706536@");
        User user = new User();
        user.setUsername("yws");
        user.setPassword(encode);
        int insert = userMapper.insert(user);
        System.out.println("insert = " + insert);
    }
    @Test
    public void findUserByUsername() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, "yws");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }
}
