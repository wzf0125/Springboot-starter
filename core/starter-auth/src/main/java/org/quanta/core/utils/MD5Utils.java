package org.quanta.core.utils;

import io.netty.util.internal.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/3
 */
@Slf4j
public class MD5Utils {
    /**
     * MD5加密基础方法
     *
     * @param string 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(string.getBytes());
            return DigestUtils.md5DigestAsHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error(ThrowableUtil.stackTraceToString(e));
        }
        return null;
    }

    /**
     * MD5加密基础方法
     *
     * @param string 需要加密的字符串
     * @param salt   加密盐值
     * @return 加密后的字符串
     */
    public static String md5(String string, String salt) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(string).append(salt);
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(sb.toString().getBytes());
            return DigestUtils.md5DigestAsHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error(ThrowableUtil.stackTraceToString(e));
        }
        return null;
    }


    /**
     * 生成随机的盐值
     */
    public static String getSalt() {
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int temp = (int) (Math.random() * (str.length()));
            sb.append(str.charAt(temp));
        }
        return sb.toString();
    }
}
