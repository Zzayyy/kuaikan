package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.dto.UserRoleIdsDto;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户-角色关联表 前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@RestController
@Tag(name = "用户角色关联接口")
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    IUserService userService;

    @Operation(summary = "根据用户id获取角色id列表")
    @GetMapping("/getRoleIdsByUserId")
    public Result getRoleIdsByUserId(@Parameter(description = "用户id") @RequestParam(value = "userId") Long userId) {
        List<Long> roleIds =  userService.getRoleIdsByUserId(userId);
        return Result.success(FastViewEnum.QUERY_SUCCESS,roleIds);
    }

    /**
     * description = "不论是新增的角色还是减少都用这个接口，将用户id和最终的角色id列表传入就行"
     * @param userRoleIdsDto
     * @return
     */
    @Operation(summary = "给用户设置角色列表")
    @PostMapping("/modifyUserRole")
    public Result modifyUserRole(@RequestBody @Parameter(description = "传入用户id和角色id列表") UserRoleIdsDto userRoleIdsDto ) {
        userService.modifyUserRole(userRoleIdsDto);
        return Result.success(FastViewEnum.MODIFY_SUCCESS);
    }
}
