package org.quanta.oos.utils;

import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.OSSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.exception.ServiceException;
import org.quanta.oos.config.properties.AliyunOSSProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * Description: 阿里云oos对象存储工具类
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AliyunOSSUtils extends AbstractOOSUtils {
    private final OSSClient ossClient;
    private final AliyunOSSProperties aliyunOSSProperties;

    /**
     * @param inputStream 文件流
     * @param path        文件名(可包含文件夹) /test/test.img
     * @return 资源访问路径(需开启公有读)
     */
    public String uploadPhoto(InputStream inputStream, String path) {
        if (inputStream == null) {
            log.error("输入流为空");
            throw new NullPointerException();
        }
        ossClient.putObject(aliyunOSSProperties.getBucketName(), path, inputStream);
        return aliyunOSSProperties.getUrlPrefix() + path;
    }
    @Override
    public String uploadPhoto(MultipartFile multipartFile, String path) {
        if (multipartFile == null) {
            log.error("文件为空");
            throw new NullPointerException();
        }
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (Exception e) {
            log.error("文件读取异常");
            throw new ServiceException("文件读取异常");
        }
        return uploadPhoto(inputStream, path);
    }

    @Override
    public String uploadPhoto(File file, String path) {
        if (file == null) {
            log.error("文件为空");
            throw new NullPointerException();
        }
        InputStream inputStream = FileUtil.getInputStream(file);
        return uploadPhoto(inputStream, path);
    }

}
