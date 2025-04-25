package cn.kshost.fastview.backend.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoTagDto {
    private Integer pageSize;

    private Integer pageNum;

    private Long id;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
