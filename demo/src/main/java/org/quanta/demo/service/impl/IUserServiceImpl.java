package org.quanta.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quanta.api.constants.LockConstants;
import org.quanta.api.entity.User;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.constants.Role;
import org.quanta.core.utils.MD5Utils;
import org.quanta.core.utils.TokenUtils;
import org.quanta.demo.mapper.UserMapper;
import org.quanta.demo.service.IUserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@Service
@RequiredArgsConstructor
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final TokenUtils tokenUtils;
    private final RedissonClient redissonClient;

    @Override
    public String login(String username, String password) {
        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        if (!user.getPassword().equals(MD5Utils.md5(password, user.getSalt()))) {
            throw new ServiceException("账号或密码错误");
        }
        return tokenUtils.grantToken(user.getId(), Role.codeOf(user.getRole()));
    }

    /**
     * 注册
     */
    @Override
    public void register(String username, String password) {
        RLock lock = redissonClient.getFairLock(String.format(LockConstants.REGISTER_LOCK, username));
        // 对用户名加注册锁 确保同一时间只有一个人注册此用户名
        lock.lock(10, TimeUnit.SECONDS);
        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user != null) {
            // 释放注册锁
            lock.unlock();
            throw new ServiceException("用户已被注册");
        }
        String salt = MD5Utils.getSalt();
        String md5Pwd = MD5Utils.md5(password, salt);
        user = User.builder()
                .username(username)
                .password(md5Pwd)
                .salt(salt)
                .role(Role.USER.getCode())
                .build();
        save(user);
        // 释放注册锁
        lock.unlock();
    }
}
