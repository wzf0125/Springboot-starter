package org.quanta.pssa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.pssa.entity.User;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
