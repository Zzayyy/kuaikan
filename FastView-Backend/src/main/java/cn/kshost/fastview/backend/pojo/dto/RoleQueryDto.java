package cn.kshost.fastview.backend.pojo.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleQueryDto {


    private Integer pageNum =1;

    private Integer pageSize  = 20;

    private String roleName;

    private Integer status;
}
