package cn.kshost.fastview.backend.service;

import cn.kshost.fastview.backend.pojo.dto.VideoCommentQueryDto;
import cn.kshost.fastview.backend.pojo.po.VideoComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 视频评论表 服务类
 * </p>
 *
 * @author Zzay
 * @since 2025-04-28
 */
public interface IVideoCommentService extends IService<VideoComment> {

    Page<VideoComment> getCommentPage(VideoCommentQueryDto commentQueryDto);

    boolean likeComment(Long id);

}
