package org.quanta.oos.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.exception.ServiceException;
import org.quanta.oos.config.properties.TencentCOSProperties;
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
public class TencentCOSUtils extends AbstractOOSUtils {
    private final TencentCOSProperties tencentCOSProperties;
    private final COSClient cosClient;

    /**
     * @param inputStream 文件流
     * @param path        文件名(可包含文件夹) /test/test.img
     * @return 资源访问路径(需开启公有读)
     */
    @Override
    public String uploadPhoto(InputStream inputStream, String path) {
        File tempFile = FileUtil.createTempFile();
        IoUtil.copy(inputStream, FileUtil.getOutputStream(tempFile));
        String url = uploadPhoto(tempFile, path);
        tempFile.delete();
        return url;
    }

    @Override
    public String uploadPhoto(File file, String path) {
        if (file == null) {
            log.error("文件为空");
            throw new NullPointerException();
        }
        cosClient.putObject(buildPutObjectRequest(file, path));
        return tencentCOSProperties.getUrlPrefix() + path;
    }

    @Override
    public String uploadPhoto(MultipartFile multipartFile, String path) {
        if (multipartFile == null) {
            log.error("文件为空");
            throw new NullPointerException();
        }
        File tempFile = FileUtil.createTempFile();
        try {
            multipartFile.transferTo(tempFile);
            return uploadPhoto(tempFile, path);
        } catch (Exception e) {
            log.error("文件转换异常");
            throw new ServiceException("文件转换异常");
        } finally {
            tempFile.delete();
        }
    }


    /**
     * @param loaclFile 待上传文件
     * @param path      文件名(可包含文件夹) /test/test.img
     * @return 资源访问路径(需开启公有读)
     */
    private PutObjectRequest buildPutObjectRequest(File loaclFile, String path) {
        return new PutObjectRequest(tencentCOSProperties.getBucketName(), path, loaclFile);
    }
}
