package cn.kshost.fastview.backend.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "接收前台用户多条件查询")
@Data
public class UserQueryDto {
    private Integer pageSize=10;
    private Integer pageNum=1;
    public String username;
    public String nickName;
    public String phone;
    public Integer status;
}
