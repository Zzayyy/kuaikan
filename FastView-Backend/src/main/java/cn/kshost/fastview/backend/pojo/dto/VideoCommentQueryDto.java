package cn.kshost.fastview.backend.pojo.dto;

import lombok.Data;

@Data
public class VideoCommentQueryDto {
    private Long videoId;   // 按视频ID查询评论
    private Long parentId;  // 按父评论查询（一级评论传null或者0）
    private Integer pageNum = 1;   // 当前页
    private Integer pageSize = 10; // 每页数量
}
