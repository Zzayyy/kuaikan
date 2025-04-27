package cn.kshost.fastview.backend.service;

import cn.kshost.fastview.backend.pojo.po.VideoTag;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzay
 * @since 2025-04-25
 */
public interface IVideoTagService extends IService<VideoTag> {

    List<VideoTag> getByVideo(Long videoId);

    Page<VideoTag> getByVideoPage(Page<VideoTag> page, Long videoId);

    boolean removeTagWithRelation(Long id);

    boolean bindTagsToVideo(Long videoId, List<Long> tagIds);
}
