package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.dto.VideoQueryDto;
import cn.kshost.fastview.backend.pojo.po.Video;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IVideoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Tag(name = "视频接口")
public class VideoController {

    private final IVideoService videoService;

    /**
     * 添加视频
     * @param video 视频id
     * @return 添加结果
     */
    @Operation(summary = "添加视频")
    @PostMapping
    public Result<String> addVideo(@RequestBody Video video){
        video.setCreateTime(LocalDateTime.now());
        video.setUpdateTime(LocalDateTime.now());
        boolean result = videoService.save(video);
        return result ? Result.success(FastViewEnum.ADD_SUCCESS) : Result.error(FastViewEnum.ADD_ERROR);
    }

    /**
     * 根据id获取视频信息
     * @param id 视频id
     * @return 视频信息
     */
    @Operation(summary = "根据id获取视频信息")
    @GetMapping("/{id}")
    public Result<Video> getVideoById(@PathVariable Long id){
        Video video = videoService.getById(id);
        return video != null ? Result.success(FastViewEnum.QUERY_SUCCESS,video) : Result.error(FastViewEnum.QUERY_ERROR);
    }

    /**
     * 分页查询视频信息
     * @return 视频分页信息
     */
    @Operation(summary = "分页查询视频信息")
    @PostMapping("/page")
    public Result<Page<Video>> getVideoByPage(@RequestBody VideoQueryDto videoQueryDto){
        Page<Video> videoPageInfo = videoService.getVideoPage(videoQueryDto);
        return Result.success(FastViewEnum.QUERY_SUCCESS,videoPageInfo);
    }

    /**
     * 根据id删除视频信息
     * @param id 视频id
     * @return 删除结果
     */
    @Operation(summary = "根据id删除视频信息")
    @DeleteMapping("/{id}")
    public Result<String> deleteVideoById(@PathVariable Long id){
        boolean result = videoService.removeVideoWithTags(id);
        return result ? Result.success(FastViewEnum.DELETE_SUCCESS) : Result.error(FastViewEnum.DELETE_ERROR);
    }

    /**
     * 根据id修改视频信息
     * @param id 视频id
     * @param video 视频信息
     * @return 修改结果
     */
    @Operation(summary = "根据id修改视频信息")
    @PutMapping("{id}")
    public Result<String> updateVideoById(@PathVariable Long id, @RequestBody Video video){
        video.setId(id);
        boolean result = videoService.updateById(video);
        return result ? Result.success(FastViewEnum.MODIFY_SUCCESS) : Result.error(FastViewEnum.MODIFY_ERROR);
    }
}
