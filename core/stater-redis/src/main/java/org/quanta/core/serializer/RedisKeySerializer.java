package org.quanta.core.serializer;

import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Description: Redis序列化
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/9/17
 */
public class RedisKeySerializer implements RedisSerializer<Object> {
    private final Charset charset;
    private final ConversionService converter;

    public RedisKeySerializer() {
        this(StandardCharsets.UTF_8);
    }

    public RedisKeySerializer(Charset charset) {
        this.charset = charset;
        this.converter = DefaultConversionService.getSharedInstance();
    }

    // 序列化
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        String key;
        if (o instanceof SimpleKey) {
            key = "";
        } else if (o instanceof String) {
            key = (String) o;
        } else {
            key = converter.convert(o, String.class);
        }
        return key.getBytes(this.charset);
    }

    // 反序列化化
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, charset);
    }
}
