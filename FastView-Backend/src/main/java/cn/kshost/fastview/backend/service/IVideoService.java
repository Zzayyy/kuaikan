package cn.kshost.fastview.backend.service;

import cn.kshost.fastview.backend.pojo.po.Video;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
public interface IVideoService extends IService<Video> {

    IPage<Video> getVideoPage(Page<Video> page);
}
