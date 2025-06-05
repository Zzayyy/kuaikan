package cn.kshost.fastview.backend.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private Long id;
    private String path;
    private String name;
    private Byte status;
    private Long parentId;
    private Integer orderNum; //越小越靠前
    private Map<String, String> meta;
    private List<MenuItem> children = new ArrayList<MenuItem>();

}
