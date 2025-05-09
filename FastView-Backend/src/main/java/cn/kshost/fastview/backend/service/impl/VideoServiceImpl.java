package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.mapper.VideoMapper;
import cn.kshost.fastview.backend.mapper.VideoTagMapMapper;
import cn.kshost.fastview.backend.pojo.dto.VideoQueryDto;
import cn.kshost.fastview.backend.pojo.po.Video;
import cn.kshost.fastview.backend.pojo.po.VideoTagMap;
import cn.kshost.fastview.backend.service.IVideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoTagMapMapper videoTagMapMapper;

    @Override
    public Page<Video> getVideoPage(VideoQueryDto videoQueryDto) {
        Page<Video> videoPage = new Page<>(videoQueryDto.getPageNum(), videoQueryDto.getPageSize());
        videoPage = videoMapper.selectVideo(videoPage,videoQueryDto);
        return videoPage;
    }

    @Transactional
    @Override
    public boolean removeVideoWithTags(Long id) {
        // 1. 删除视频-标签映射表中该视频的所有记录
        videoTagMapMapper.delete(
                new LambdaQueryWrapper<VideoTagMap>()
                        .eq(VideoTagMap::getVideoId, id)
        );
        // 2. 删除视频本身
        return this.removeById(id);
    }
}
