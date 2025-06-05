package cn.kshost.fastview.backend.controller.video;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.dto.VideoCategoryDto;
import cn.kshost.fastview.backend.pojo.dto.VideoQueryDto;
import cn.kshost.fastview.backend.pojo.po.VideoCategory;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IVideoCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 视频分类表 前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@RestController
@RequestMapping("/videoCategory")
@Tag(name = "视频分类接口")
public class VideoCategoryController {

    @Autowired
    private IVideoCategoryService videoCategoryService;


    /**
     * 添加视频分类
     *
     * @param videoCategory 视频分类对象
     * @return 添加结果
     */
    @Operation(summary = "添加视频分类")
    @PostMapping
    public Result<String> addVideoCategory(@RequestBody VideoCategory videoCategory) {
        boolean result = videoCategoryService.save(videoCategory);
        return result ? Result.success("分类添加成功") : Result.error(500, "分类添加失败");
    }

    /**
     * 根据id列表删除视频分类
     *
     * @param ids
     * @return 删除结果
     */
    @Operation(summary = "根据id列表删除视频分类")
    @DeleteMapping("/removeByIds")
    public Result<String> deleteVideoCategoryById(@RequestBody List<Long> ids) {
        boolean result = videoCategoryService.removeByIds(ids);
        return Result.success(FastViewEnum.DELETE_SUCCESS);
    }

    /**
     * 分页查询视频分类
     *
     * @param videoCategoryDto
     * @return 分页结果
     */
    @Operation(summary = "分页查询视频分类")
    @PostMapping("/page")
    public Result getVideoCategoryByPage(@RequestBody VideoCategoryDto videoCategoryDto) {
//        Page<VideoCategory> videoCategoryPage =;
        LambdaQueryWrapper<VideoCategory> queryWrapper = new LambdaQueryWrapper<>();

        if (videoCategoryDto.getStatus() != null) {
            queryWrapper.eq(VideoCategory::getStatus, videoCategoryDto.getStatus());
        }
        if (videoCategoryDto.getStartTime() != null) {
            queryWrapper.gt(VideoCategory::getCreateTime, videoCategoryDto.getStartTime());
        }
        if (videoCategoryDto.getEndTime() != null) {
            queryWrapper.lt(VideoCategory::getCreateTime, videoCategoryDto.getEndTime());
        }
        if (videoCategoryDto.getCategoryName() != null) {
            queryWrapper.like(VideoCategory::getCategoryName, videoCategoryDto.getCategoryName());
        }
        queryWrapper.orderByAsc(VideoCategory::getOrderNum);

        Page<VideoCategory> videoCategoryList = videoCategoryService.page(new Page<>(videoCategoryDto.getPageNum(), videoCategoryDto.getPageSize()), queryWrapper);
        return videoCategoryList != null ? Result.success(videoCategoryList) : Result.error(500, "分类查询失败");
    }

    /**
     * 根据id修改视频分类
     * @param id 视频分类的ID
     * @param videoCategory 视频分类对象
     * @return 修改结果
     */
    @Operation(summary = "根据id修改视频分类")
    @PutMapping("{id}")
    public Result<String> updateVideoCategoryById(@PathVariable Long id, @RequestBody VideoCategory videoCategory) {
        videoCategory.setId(id);
        boolean result = videoCategoryService.updateById(videoCategory);
        return result ? Result.success("分类修改成功") : Result.error(500,"分类修改失败");
    }

    /**
     * 获取所有可用分类
     * @return
     */
    @Operation(summary = "获取可用分类列表")
    @GetMapping("/getVideoCategoryList")
    public Result<List<VideoCategory>> getVideoCategoryList() {
        LambdaQueryWrapper<VideoCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(VideoCategory::getOrderNum).eq(VideoCategory::getStatus,1).eq(VideoCategory::getIsDelete,0);
        List<VideoCategory> videoCategoryList = videoCategoryService.list(queryWrapper);
        return Result.success(videoCategoryList);
    }
}
