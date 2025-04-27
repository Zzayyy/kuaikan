package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.mapper.VideoTagMapMapper;
import cn.kshost.fastview.backend.mapper.VideoTagMapper;
import cn.kshost.fastview.backend.pojo.po.VideoTag;
import cn.kshost.fastview.backend.pojo.po.VideoTagMap;
import cn.kshost.fastview.backend.service.IVideoTagMapService;
import cn.kshost.fastview.backend.service.IVideoTagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzay
 * @since 2025-04-25
 */
@Service
public class VideoTagServiceImpl extends ServiceImpl<VideoTagMapper, VideoTag> implements IVideoTagService {

    @Autowired
    private VideoTagMapMapper videoTagMapMapper;

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private IVideoTagMapService videoTagMapService;     // 批量添加标签服务，因为是IService的接口


    /**
     * 查询某个视频的所有标签（不分页）
     */
    @Override
    public List<VideoTag> getByVideo(Long videoId) {
        // 查询这个视频关联了哪些标签ID
        LambdaQueryWrapper<VideoTagMap> mapQuery = new LambdaQueryWrapper<VideoTagMap>()
                .eq(VideoTagMap::getVideoId, videoId);

        List<Long> tagIds = videoTagMapMapper.selectList(mapQuery)
                .stream()
                .map(VideoTagMap::getTagId)
                .toList();

        // 如果没有关联标签，直接返回空列表
        if (tagIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 根据标签ID列表查询标签信息
        return videoTagMapper.selectList(
                new LambdaQueryWrapper<VideoTag>().in(VideoTag::getId, tagIds)
        );
    }

    /**
     * 查询某个视频的标签（分页）
     */
    @Override
    public Page<VideoTag> getByVideoPage(Page<VideoTag> page, Long videoId) {
        // 查询这个视频关联了哪些标签ID
        LambdaQueryWrapper<VideoTagMap> mapQuery = new LambdaQueryWrapper<VideoTagMap>()
                .eq(VideoTagMap::getVideoId, videoId);

        List<Long> tagIds = videoTagMapMapper.selectList(mapQuery)
                .stream()
                .map(VideoTagMap::getTagId)
                .toList();

        if (tagIds.isEmpty()) {
            // 没有标签的话返回空分页
            page.setRecords(Collections.emptyList());
            page.setTotal(0);
            return page;
        }

        // 查询分页标签列表
        LambdaQueryWrapper<VideoTag> tagQuery = new LambdaQueryWrapper<VideoTag>()
                .in(VideoTag::getId, tagIds);

        return this.page(page, tagQuery);
    }

    /**
     * 删除标签并删除标签和视频的关联关系
     */
    @Transactional
    @Override
    public boolean removeTagWithRelation(Long id) {
        // 首先删除 VideoTagMap 表中的关联记录
        LambdaQueryWrapper<VideoTagMap> mapQuery = new LambdaQueryWrapper<VideoTagMap>()
                .eq(VideoTagMap::getTagId, id);
        videoTagMapMapper.delete(mapQuery);
        // 再删除 VideoTag 表中的记录
        return this.removeById(id);
    }

    /**
     * 绑定标签到视频
     */
    @Transactional
    @Override
    public boolean bindTagsToVideo(Long videoId, List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            throw new RuntimeException("标签列表不能为空");
        }

        List<VideoTagMap> mappings = tagIds.stream().map(tagId -> {
            VideoTagMap map = new VideoTagMap();
            map.setVideoId(videoId);
            map.setTagId(tagId);
            return map;
        }).toList();
        // 批量插入
        return videoTagMapService.saveBatch(mappings);
    };
}
