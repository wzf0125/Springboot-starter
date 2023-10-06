package org.quanta.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Description: mybatis-plus配置 仅对mybatis-plus提供的操作生效(对xml的形式无效)
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/10/3
 */
@Configuration
@MapperScan("org.quanta.**.mapper.**")
@PropertySource("classpath:mybatis-plus_config.yml")
@AllArgsConstructor
public class MybatisPlusConfig {
    /**
     * 注册拦截器
     * 自动分页: PaginationInnerInterceptor
     * sql 性能规范: IllegalSQLInnerInterceptor
     * 防止全表更新与删除: BlockAttackInnerInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 自动分页
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor()); // sql性能规范
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor()); // 全表更新与删除拦截
        return interceptor;
    }
}
