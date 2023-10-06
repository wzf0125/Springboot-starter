package org.quanta.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.api.entity.User;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
