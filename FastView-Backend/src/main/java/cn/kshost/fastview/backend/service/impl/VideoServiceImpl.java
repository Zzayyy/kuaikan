package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.mapper.VideoMapper;
import cn.kshost.fastview.backend.pojo.dto.VideoQueryDto;
import cn.kshost.fastview.backend.pojo.po.Video;
import cn.kshost.fastview.backend.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageInfo<Video> getVideoPage(VideoQueryDto videoQueryDto) {
        PageHelper.startPage(videoQueryDto.getPageNum(), videoQueryDto.getPageSize());
      List<Video> videoList= videoMapper.selectVideo(videoQueryDto);
        PageInfo<Video> videoPageInfo = new PageInfo<>(videoList);
        return videoPageInfo;
    }
}
