package org.quanta.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.api.entity.User;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
public interface IUserService extends IService<User> {
    String login(String username, String password);

    void register(String username, String password);
}
