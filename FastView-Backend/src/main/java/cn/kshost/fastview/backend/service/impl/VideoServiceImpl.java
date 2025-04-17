package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.pojo.po.Video;
import cn.kshost.fastview.backend.mapper.VideoMapper;
import cn.kshost.fastview.backend.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
