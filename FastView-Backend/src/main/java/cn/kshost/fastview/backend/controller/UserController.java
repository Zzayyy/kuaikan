package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.pojo.dto.UserQueryDto;
import cn.kshost.fastview.backend.pojo.dto.UserRoleIdsDto;
import cn.kshost.fastview.backend.pojo.po.MenuItem;
import cn.kshost.fastview.backend.pojo.po.User;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.pojo.vo.LoginUserVo;
import cn.kshost.fastview.backend.security.LoginUserDetail;
import cn.kshost.fastview.backend.service.IUserService;
import cn.kshost.fastview.backend.util.FastViewContextUtil;
import cn.kshost.fastview.backend.util.TokenUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "分页查询系统用户")
    @PostMapping("/getAllSysUsers")
    public Result getAllSysUsers(@RequestBody UserQueryDto userQueryDto) {
        Page<User> allSysUsers = userService.getAllSysUsers(userQueryDto);
        return Result.success("success",allSysUsers);
    }

    @Operation(summary = "根据用户id列表删除用户")
    @DeleteMapping("/deleteByIds")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        userService.deleteByIds(ids);
        return Result.success("success");
    }

    @Operation(summary = "添加系统用户")
    @PostMapping("/addSysUser")
    public Result addSysUser(@RequestBody User user) {
        userService.addSysUser(user);
        return Result.success("success");
    }
    @Operation(summary = "根据用户id获取角色id列表")
    @GetMapping("/getRoleIdsByUserId")
    public Result getRoleIdsByUserId(@Parameter(description = "用户id") @RequestParam(value = "userId") Long userId) {
        List<Long> roleId =  userService.getRoleIdsByUserId(userId);
        return Result.success("success",roleId);
    }

    @Operation(summary = "设置用户角色列表")
    @PostMapping("/modifyUserRole")
    public Result modifyUserRole(@RequestBody @Parameter(description = "传入用户id和角色id列表") UserRoleIdsDto userRoleIdsDto ) {
        userService.modifyUserRole(userRoleIdsDto);
        return Result.success("设置角色成功");
    }




}
