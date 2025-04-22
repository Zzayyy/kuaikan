package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.pojo.po.VideoCategory;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IVideoCategoryService;
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
     * 获取视频分类树
     * @return 分类列表
     */
    @Operation(summary = "获取视频分类树")
    @GetMapping("/tree")
    public Result<List<VideoCategory>> getVideoCategoryTree() {
        return Result.success(videoCategoryService.list());
    }

    /**
     * 添加视频分类
     * @param videoCategory 视频分类对象
     * @return 添加结果
     */
    @Operation(summary = "添加视频分类")
    @PostMapping
    public Result<String> addVideoCategory(VideoCategory videoCategory) {
        boolean result = videoCategoryService.save(videoCategory);
        return result ? Result.success("分类添加成功") : Result.error(500,"分类添加失败");
    }

    /**
     * 根据id删除视频分类
     * @param id 视频分类的ID
     * @return 删除结果
     */
    @Operation(summary = "根据id删除视频分类")
    @DeleteMapping("/{id}")
    public Result<String> deleteVideoCategoryById(@PathVariable Long id) {
        boolean result = videoCategoryService.removeById(id);
        return result ? Result.success("分类删除成功") : Result.error(500,"分类删除失败");
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
}
