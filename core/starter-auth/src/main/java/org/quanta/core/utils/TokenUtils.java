package org.quanta.core.utils;

import lombok.RequiredArgsConstructor;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.constants.Role;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.UUID;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/3
 */
@Component
@RequiredArgsConstructor
public class TokenUtils {
    private final RedisUtils redisUtils;

    /**
     * 默认为不可重复登录
     */
    public String grantToken(Long uid, Role role) {
        return this.grantToken(uid, role, false);
    }

    /**
     * 发放token
     *
     * @param uid     用户id
     * @param role    权限
     * @param replace 是否保留上一token
     * @return 获取到的token
     */
    public String grantToken(Long uid, Role role, boolean replace) {
        HashMap<String, Object> identity = new HashMap<>();
        identity.put("uid", uid);
        identity.put("role", role.getCode());

        String token = UUID.randomUUID().toString().replace("-", "");
        // token有效期为1周
        redisUtils.set(token, identity, 60 * 60 * 24 * 7L);

        String prefix = role.getPrefix();
        if (!replace) {
            // 删掉之前如果有效的token
            String formerToken = redisUtils.get(String.format(prefix, uid));
            if (formerToken != null) {
                destroyToken(formerToken);
            }
        }
        redisUtils.set(String.format(prefix, uid), token);
        return token;
    }

    /**
     * 获取token信息
     *
     * @param key token
     * @return uid和role的map
     */
    private HashMap<String, Object> retrieveToken(String key) {
        return redisUtils.get(key);
    }

    /**
     * 获取token对应的权限
     *
     * @param key token
     * @return 权限
     */
    public int getTokenRole(String key) {
        int role;
        try {
            role = (int) retrieveToken(key).get("role");
        } catch (NullPointerException e) {
            throw new ServiceException("token无效");
        }
        return role;
    }

    /**
     * 获取token对应的id
     *
     * @param key token
     * @return id
     */
    public long getTokenUid(String key) {
        return (long) retrieveToken(key).get("uid");
    }

    /**
     * 删除token
     *
     * @param key 缓存key
     */
    private void destroyToken(String key) {
        redisUtils.del(key);
    }

    /**
     * 刷新token时间
     */
    public void refreshToken(String token, long uid, Role role) {
        Long last = redisUtils.getExpire(token); // 获取token剩余时间
        if (last <= (60 * 60 * 24)) { // 有效期小于一天
            // 更新有效期
            redisUtils.setExpire(token, 60 * 60 * 24 * 7);
            String prefix = role.getPrefix();
            redisUtils.setExpire(String.format(prefix, uid), 60 * 60 * 24 * 7);
        }
    }

    /**
     * 安全退出
     *
     * @param uid uid
     */
    public void safeExit(long uid, Role role) {
        String prefix = role.getPrefix();
        String formerToken = (String) redisUtils.get(String.format(prefix, uid));
        redisUtils.del(String.format(prefix, uid));
        destroyToken(formerToken);
    }
}
