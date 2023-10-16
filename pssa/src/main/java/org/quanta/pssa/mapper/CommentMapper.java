package org.quanta.pssa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.pssa.entity.Comment;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/17
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
