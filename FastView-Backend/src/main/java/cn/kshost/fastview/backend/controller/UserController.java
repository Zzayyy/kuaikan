package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.pojo.MenuItem;
import cn.kshost.fastview.backend.pojo.User;
import cn.kshost.fastview.backend.pojo.vo.LoginUserVo;
import cn.kshost.fastview.backend.security.LoginUserDetail;
import cn.kshost.fastview.backend.service.IUserService;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.util.FastViewContextUtil;
import cn.kshost.fastview.backend.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-11
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
public class UserController {


    @Autowired
    private IUserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody User user) {

        LoginUserVo loginUserVo=  userService.login(user);
       if (!Objects.isNull(loginUserVo)) {
           return Result.success("success",loginUserVo);
       }
           return Result.error("用户名或密码错误",null);
    }

    @Operation(summary = "根据token获取路由")
    @GetMapping("/getAsyncRoutes")
    public Result getAsyncRoutes(HttpServletRequest request) {
        //获取用户信息
        LoginUserDetail loginUserDetail = FastViewContextUtil.getLoginUserDetail();
        List<MenuItem> menuItemList  =  userService.getMenuItemList(loginUserDetail);

        return Result.success("success", menuItemList);
    }

    @Operation(summary ="刷新token")
    @PostMapping("/refreshToken")
    public  Result refreshToken(@RequestBody LoginUserVo loginUserVo,HttpServletRequest request) {
        String accessToken = TokenUtil.getToken(request);
        LoginUserVo newloginUserVo =  userService.refreshToken(loginUserVo.getRefreshToken(),accessToken);
        if (!Objects.isNull(loginUserVo)) {
            return Result.success("success",newloginUserVo);
        }
        return Result.error("信息错误",null);

    }







}
