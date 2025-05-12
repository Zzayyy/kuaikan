package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.mapper.VideoCommentMapper;
import cn.kshost.fastview.backend.pojo.dto.VideoCommentQueryDto;
import cn.kshost.fastview.backend.pojo.po.VideoComment;
import cn.kshost.fastview.backend.service.IVideoCommentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频评论表 服务实现类
 * </p>
 *
 * @author Zzay
 * @since 2025-04-28
 */
@Service
public class VideoCommentServiceImpl extends ServiceImpl<VideoCommentMapper, VideoComment> implements IVideoCommentService {

    @Override
    public Page<VideoComment> getCommentPage(VideoCommentQueryDto commentQueryDto) {

        Page<VideoComment> page = new Page<>(commentQueryDto.getPageNum(), commentQueryDto.getPageSize());

        LambdaQueryWrapper<VideoComment> queryWrapper = new LambdaQueryWrapper<>();

        // 如果传了 videoId，按视频id查
        if (commentQueryDto.getVideoId() != null) {
            queryWrapper.eq(VideoComment::getVideoId, commentQueryDto.getVideoId());
        }

        // 如果传了 parentId，按父id查（用于查询回复）
        if (commentQueryDto.getParentId() != null) {
            queryWrapper.eq(VideoComment::getParentId, commentQueryDto.getParentId());
        } else {
            queryWrapper.isNull(VideoComment::getParentId); // 默认查一级评论（无父评论）
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(VideoComment::getCreateTime);

        return this.page(page, queryWrapper);
    }

    @Override
    public boolean likeComment(Long commentId) {
        VideoComment comment = this.getById(commentId);
        if (comment == null) {
            return false;
        }
        // 点赞数 +1
        comment.setLikeCount(comment.getLikeCount() + 1);
        return this.updateById(comment);
    }
}
