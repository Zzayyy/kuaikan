package cn.kshost.fastview.backend.mapper;

import cn.kshost.fastview.backend.pojo.dto.VideoQueryDto;
import cn.kshost.fastview.backend.pojo.po.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 视频表 Mapper 接口
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    Page<Video> selectVideo(IPage<Video> iPage, @Param("videoQueryDto") VideoQueryDto videoQueryDto);
}

