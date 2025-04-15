package cn.kshost.fastview.backend.mapper;



import cn.kshost.fastview.backend.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-角色关联表 Mapper 接口
 * </p>
 *
 * @author 杨文胜
 * @since 2025-04-15
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

