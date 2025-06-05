package cn.kshost.fastview.backend.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCategoryDto {
    private Integer pageSize;

    private Integer pageNum;

    private String categoryName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Byte status;

    private Byte isDelete;
}
