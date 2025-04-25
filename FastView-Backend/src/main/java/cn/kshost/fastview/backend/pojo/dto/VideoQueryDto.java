package cn.kshost.fastview.backend.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(name = "video接收前台用户多条件查询")
@Data
public class VideoQueryDto {
        private Integer pageSize=10;
        private Integer pageNum=1;
        private String title;
    private String stars;
    private String status;
    private Integer categoryId;

}
