package cn.kshost.fastview.backend.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class VideoTagBindDto {
    private Long videoId;         // 视频id
    private List<Long> tagIds;    // 选中的标签id列表
}
