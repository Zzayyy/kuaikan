package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.emus.FastViewEnum;
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

           return Result.success("success",loginUserVo);
    }

    @Operation(summary = "根据token获取路由")
    @GetMapping("/getAsyncRoutes")
    public Result getAsyncRoutes(HttpServletRequest request) {
        //获取用户信息
        LoginUserDetail loginUserDetail = FastViewContextUtil.getLoginUserDetail();
        //获取路由树
        List<MenuItem> menuItemList  =  userService.getMenuItemList(loginUserDetail);
        return Result.success(FastViewEnum.QUERY_SUCCESS, menuItemList);
    }

    @Operation(summary ="刷新token")
    @PostMapping("/refreshToken")
    public  Result refreshToken(@RequestBody LoginUserVo loginUserVo,HttpServletRequest request) {
        String accessToken = TokenUtil.getToken(request);
        LoginUserVo newloginUserVo =  userService.refreshToken(loginUserVo.getRefreshToken(),accessToken);
        return Result.success(FastViewEnum.TOKEN_REFRESH_SUCCESS,newloginUserVo);
    }

    @Operation(summary = "根据用户id查询用户")
    @GetMapping("/getSysUserById/{id}“")
    public Result getSysUserById(@PathVariable Integer id) {
        User user = userService.getById(id);
       return   Result.success(FastViewEnum.QUERY_SUCCESS, user);
    }

    @Operation(summary = "根据用户id修改用户")
    @PostMapping("/modifySysUserById")
    public Result modifySysUserById(User user) {
        userService.modifySysUserById(user);
        return   Result.success(FastViewEnum.MODIFY_SUCCESS);
    }
    @Operation(summary = "分页查询系统用户")
    @PostMapping("/getAllSysUsers")
    public Result getAllSysUsers(@RequestBody UserQueryDto userQueryDto) {
        Page<User> allSysUsers = userService.getAllSysUsers(userQueryDto);
        return Result.success(FastViewEnum.QUERY_SUCCESS,allSysUsers);
    }

    @Operation(summary = "根据用户id列表删除用户")
    @DeleteMapping("/deleteByIds")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        userService.deleteByIds(ids);
        return Result.success(FastViewEnum.DELETE_SUCCESS);
    }
    @Operation(summary = "添加系统用户")
    @PostMapping("/addSysUser")
    public Result addSysUser(@RequestBody User user) {
        userService.addSysUser(user);
        return Result.success(FastViewEnum.ADD_SUCCESS);
    }

    @Operation(summary = "根据用户id获取角色id列表")
    @GetMapping("/getRoleIdsByUserId")
    public Result getRoleIdsByUserId(@Parameter(description = "用户id") @RequestParam(value = "userId") Long userId) {
        List<Long> roleIds =  userService.getRoleIdsByUserId(userId);
        return Result.success(FastViewEnum.QUERY_SUCCESS,roleIds);
    }

    @Operation(summary = "给用户设置角色列表",description = "不论是新增的角色还是减少都用这个接口，将用户id和最终的角色id列表传入就行")
    @PostMapping("/modifyUserRole")
    public Result modifyUserRole(@RequestBody @Parameter(description = "传入用户id和角色id列表") UserRoleIdsDto userRoleIdsDto ) {
        userService.modifyUserRole(userRoleIdsDto);
        return Result.success(FastViewEnum.MODIFY_SUCCESS);
    }
}
