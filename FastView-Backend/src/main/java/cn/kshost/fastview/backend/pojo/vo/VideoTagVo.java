package cn.kshost.fastview.backend.pojo.vo;

import cn.kshost.fastview.backend.pojo.po.VideoTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoTagVo {
    private Long id;

    private String name;

    private List<VideoTag> videoTagList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
