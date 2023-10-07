package org.quanta.core.config;

import lombok.RequiredArgsConstructor;
import org.quanta.core.beans.WebMvcConfigProperties;
import org.quanta.core.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: web mvc配置 处理跨域，路径放行，拦截器注册
 * Author: wzf
 * Date: 2023/10/3
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final WebMvcConfigProperties webMvcConfigProperties;
    private final AuthInterceptor authInterceptor;

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns(webMvcConfigProperties.getExcludePath())
                .addPathPatterns(webMvcConfigProperties.getInterceptorPath())
                .order(Ordered.LOWEST_PRECEDENCE);
    }

    /**
     * 添加资源路径映射策略
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        for (WebMvcConfigProperties.ResourceHandler resourceHandler : webMvcConfigProperties.getResourceHandlers()) {
            registry.addResourceHandler(resourceHandler.getHandlerPaths().toArray(new String[0]))
                    .addResourceLocations(resourceHandler.getResourceLocations().toArray(new String[0]));
        }
    }
}
