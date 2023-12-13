package org.quanta.oos.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * Description: 对象存储方法抽象类
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/13
 */
@Slf4j
public abstract class AbstractOOSUtils {
    /**
     * @param multipartFile MultipartFile文件
     * @param dir           目标文件夹
     * @param filename      文件名
     * @return 资源访问路径(需开启公有读)
     */
    public String uploadPhoto(MultipartFile multipartFile, String dir, String filename) {
        return uploadPhoto(multipartFile, "/" + dir + "/" + filename);
    }

    /**
     * @param file     file文件
     * @param dir      目标文件夹
     * @param filename 文件名
     * @return 资源访问路径(需开启公有读)
     */
    public String uploadPhoto(File file, String dir, String filename) {
        return uploadPhoto(file, "/" + dir + "/" + filename);
    }

    /**
     * @param multipartFile MultipartFile文件
     * @param path          文件名(可包含文件夹) /test/test.img
     * @return 资源访问路径(需开启公有读)
     */
    public abstract String uploadPhoto(MultipartFile multipartFile, String path);

    /**
     * @param file 上传文件
     * @param path 文件名(可包含文件夹) /test/test.img
     * @return 资源访问路径(需开启公有读)
     */
    public abstract String uploadPhoto(File file, String path);

    /**
     * @param inputStream 文件流
     * @param path        文件名(可包含文件夹) /test/test.img
     * @return 资源访问路径(需开启公有读)
     */
    public abstract String uploadPhoto(InputStream inputStream, String path);
}
