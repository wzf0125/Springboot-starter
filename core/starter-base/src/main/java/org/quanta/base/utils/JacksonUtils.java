package org.quanta.base.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.exception.ServiceException;

/**
 * Description: jackson封装
 * Author: wzf
 * Date: 2023/10/5
 */
@Slf4j
public class JacksonUtils {
    public static <T> String toJsonStr(T bean) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("json转换错误");
        }
    }
}
