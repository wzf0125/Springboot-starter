package org.quanta.oos.config;

import com.aliyun.oss.OSSClient;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.RequiredArgsConstructor;
import org.quanta.oos.config.properties.AliyunOSSProperties;
import org.quanta.oos.config.properties.TencentCOSProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Description: 腾讯云COS对象存储配置
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/11
 */
@Component
@RequiredArgsConstructor
public class OOSConfig {
    private final AliyunOSSProperties aliyunOOSProperties;
    private final TencentCOSProperties tencentCOSProperties;


    /**
     * 腾讯云对象存储客户端
     */
    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(tencentCOSProperties.getSecretId(), tencentCOSProperties.getSecretKey());
        Region region = new Region(tencentCOSProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }

    /**
     * 阿里云对象存储客户端
     */
    @Bean
    public OSSClient ossClient() {
        return new OSSClient(aliyunOOSProperties.getEndPoint(),
                aliyunOOSProperties.getSecretId(),
                aliyunOOSProperties.getSecretKey());
    }


}
