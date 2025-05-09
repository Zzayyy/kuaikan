package cn.kshost.fastview.backend.pojo.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserRoleIdsDto {
    private List<Long> roleIds;
    private Long userId;
}
