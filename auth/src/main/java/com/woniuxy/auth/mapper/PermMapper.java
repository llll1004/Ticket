package com.woniuxy.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.auth.entity.Perm;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-03-03
 */
public interface PermMapper extends BaseMapper<Perm> {
    List<Perm> selectMenuByUserId(Integer id);

    @Select("select * from user_perm_view where user_id = #{id} and type = 'a'")
    List<Perm> selectPermsByUserId(Integer id);
}
