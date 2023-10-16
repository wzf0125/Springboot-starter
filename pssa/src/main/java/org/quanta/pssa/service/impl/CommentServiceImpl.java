package org.quanta.pssa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.pssa.entity.Comment;
import org.quanta.pssa.mapper.CommentMapper;
import org.quanta.pssa.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
