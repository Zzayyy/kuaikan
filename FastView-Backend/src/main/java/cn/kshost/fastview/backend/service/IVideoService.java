package cn.kshost.fastview.backend.service;

import cn.kshost.fastview.backend.pojo.dto.VideoQueryDto;
import cn.kshost.fastview.backend.pojo.po.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
public interface IVideoService extends IService<Video> {

    PageInfo<Video> getVideoPage(VideoQueryDto videoQueryDto);
}
