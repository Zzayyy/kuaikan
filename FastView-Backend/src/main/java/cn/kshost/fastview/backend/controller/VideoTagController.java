package cn.kshost.fastview.backend.controller;


import cn.kshost.fastview.backend.pojo.dto.VideoTagDto;
import cn.kshost.fastview.backend.pojo.po.VideoTag;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IVideoTagService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzay
 * @since 2025-04-25
 */
@RestController
@RequestMapping("/video-tag")
@Tag(name = "视频标签接口")
public class VideoTagController {
    @Autowired
    private IVideoTagService videoTagService;

    /**
     * 添加标签
     * @param videoTag 标签信息
     * @return 添加结果
     */
    @Operation(summary = "添加标签")
    @PostMapping
    public Result<String> addTag(@RequestBody VideoTag videoTag) {
        boolean result = videoTagService.save(videoTag);
        return result ? Result.success("标签添加成功") : Result.error(500, "标签添加失败");
    }

    /**
     * 根据id获取标签信息
     * @param id 标签id
     * @return 标签信息
     */
    @Operation(summary = "根据id获取标签信息")
    @GetMapping("/{id}")
    public Result<VideoTag> getTagById(@PathVariable Long id) {
        VideoTag tag = videoTagService.getById(id);
        return tag != null ? Result.success(tag) : Result.error(500, "标签获取失败");
    }

    /**
     * 查询所有标签信息
     * @return 标签列表
     */
    @Operation(summary = "查询所有标签信息")
    @GetMapping("/list")
    public Result<List<VideoTag>> listTags() {
        List<VideoTag> tagList = videoTagService.list();
        return Result.success(tagList);
    }

    /**
     * 根据id删除标签信息
     * @param id 标签id
     * @return 删除结果
     */
    @Operation(summary = "根据id删除标签信息")
    @DeleteMapping("/{id}")
    public Result<String> deleteTagById(@PathVariable Long id) {
        boolean result = videoTagService.removeById(id);
        return result ? Result.success("标签删除成功") : Result.error(500, "标签删除失败");
    }

    /**
     * 根据id修改标签信息
     * @param id 标签id
     * @param videoTag 标签信息
     * @return 修改结果
     */
    @Operation(summary = "根据id修改标签信息")
    @PutMapping("/{id}")
    public Result<String> updateTagById(@PathVariable Long id, @RequestBody VideoTag videoTag) {
        videoTag.setId(id);
        boolean result = videoTagService.updateById(videoTag);
        return result ? Result.success("标签修改成功") : Result.error(500, "标签修改失败");
    }

    /**
     * 分页查询标签信息
     * @RequestBody
     * @return
     */
    @Operation(summary = "分页查询标签信息")
    @PostMapping("/page")
    public Result<Page<VideoTag>> getTagByPage(@RequestBody VideoTagDto videoTagDto) {
        Page<VideoTag> page = new Page<>(videoTagDto.getPageNum(),videoTagDto.getPageSize()) ;
        Page<VideoTag> pageList = videoTagService.page(page);
        return pageList != null ? Result.success(pageList) : Result.error(500, "视频标签查询失败");
    }
}
