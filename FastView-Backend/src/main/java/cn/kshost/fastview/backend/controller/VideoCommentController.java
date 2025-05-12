package cn.kshost.fastview.backend.controller;


import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.dto.VideoCommentQueryDto;
import cn.kshost.fastview.backend.pojo.po.VideoComment;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.IVideoCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 视频评论表 前端控制器
 * </p>
 *
 * @author Zzay
 * @since 2025-04-28
 */
@RestController
@RequestMapping("/video-comment")
public class VideoCommentController {

    @Autowired
    private IVideoCommentService videoCommentService;

    /**
     * 添加评论
     * @param videoComment 评论内容
     * @return 添加结果
     */
    @Operation(summary = "添加评论")
    @PostMapping
    public Result<String> addComment(@RequestBody VideoComment videoComment) {
        boolean result = videoCommentService.save(videoComment);
        return result ? Result.success(FastViewEnum.ADD_SUCCESS) : Result.error(FastViewEnum.ADD_ERROR);
    }

    /**
     * 删除评论
     * @param id 评论id
     * @return 删除结果
     */
    @Operation(summary = "根据id删除评论")
    @DeleteMapping("/{id}")
    public Result<String> deleteComment(@PathVariable Long id) {
        boolean result = videoCommentService.removeById(id);
        return result ? Result.success(FastViewEnum.DELETE_SUCCESS) : Result.error(FastViewEnum.DELETE_ERROR);
    }

    /**
     * 分页查询评论
     * @param commentQueryDto 分页查询条件
     * @return 评论分页数据
     */
    @Operation(summary = "分页查询评论")
    @PostMapping("/page")
    public Result<Page<VideoComment>> getCommentPage(@RequestBody VideoCommentQueryDto commentQueryDto) {
        Page<VideoComment> commentPage = videoCommentService.getCommentPage(commentQueryDto);
        return Result.success(FastViewEnum.QUERY_SUCCESS,commentPage);
    }

    /**
     * 点赞评论
     * @param id 评论id
     * @return 点赞结果
     */
    @Operation(summary = "点赞评论")
    @PostMapping("/{id}/like")
    public Result<String> likeComment(@PathVariable Long id){
        boolean result = videoCommentService.likeComment(id);
        return result ? Result.success(FastViewEnum.LIKE_SUCCESS) : Result.error(FastViewEnum.LIKE_ERROR);
    }

    /**
     * 查询评论的回复列表
     * @param parentId 父评论id
     * @param queryDto 查询参数（分页）
     * @return 回复分页列表
     */
    @Operation(summary = "查询评论的回复列表")
    @PostMapping("/{parentId}/replies")
    public Result<Page<VideoComment>> getReplies(@PathVariable Long parentId, @RequestBody VideoCommentQueryDto queryDto){
        queryDto.setParentId(parentId); // 设置parentId条件
        Page<VideoComment> page = videoCommentService.getCommentPage(queryDto);
        return Result.success(FastViewEnum.QUERY_SUCCESS, page);
    }

    /**
     * 回复评论
     * @param parentId 父评论id
     * @param comment 回复内容
     * @return 回复结果
     */
    @Operation(summary = "回复评论")
    @PostMapping("/{parentId}/reply")
    public Result<String> replyComment(@PathVariable Long parentId, @RequestBody VideoComment comment){
        comment.setParentId(parentId);
        boolean result = videoCommentService.save(comment);
        return result ? Result.success(FastViewEnum.REPLY_SUCCESS) : Result.error(FastViewEnum.REPLY_ERROR);
    }
}
