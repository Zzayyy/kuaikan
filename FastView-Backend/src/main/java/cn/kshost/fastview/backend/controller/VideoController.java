package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.pojo.po.Video;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "添加视频")
    @PostMapping
    public Result<String> addVideo(@RequestBody Video video){
        boolean result = videoService.save(video);
        // TODO 待添加到Elasticsearch
        return result ? Result.success("添加成功") : Result.error(500,"添加失败");
    }


    /**
     * 根据id获取视频信息
     * @param id
     * @return
     */
    @Operation(summary = "根据id获取视频信息")
    @GetMapping("/{id}")
    public Result<Video> getVideoById(@PathVariable Long id){
        Video video = videoService.getById(id);
        return video != null ? Result.success(video) : Result.error(500,"获取失败");
    }
}
