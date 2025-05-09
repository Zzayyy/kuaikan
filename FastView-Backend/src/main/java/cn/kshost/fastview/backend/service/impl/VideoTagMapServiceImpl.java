package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.mapper.VideoTagMapMapper;
import cn.kshost.fastview.backend.pojo.po.VideoTagMap;
import cn.kshost.fastview.backend.service.IVideoTagMapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VideoTagMapServiceImpl extends ServiceImpl<VideoTagMapMapper, VideoTagMap> implements IVideoTagMapService {
}
