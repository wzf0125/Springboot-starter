package org.quanta.pssa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.pssa.entity.User;
import org.quanta.pssa.mapper.UserMapper;
import org.quanta.pssa.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
