package com.beikei.backend.v2core.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.Duration;

/**
 * redis配置
 * @author bk
 */
@EnableCaching
@Configuration
public class SpringRedisConfig {


    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }


    /**
     * 设置默认redis管理，默认将缓存失效时间设置为12h
     */
    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory connectionFactory) {
        // 设置默认缓存时间12h
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(12));
        return new CustomizerRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory), defaultCacheConfig);
    }

    /**
     * 自定义cache命名规则，扩展SpringCache注解缺少设置缓存时间功能
     */
    public static class CustomizerRedisCacheManager extends RedisCacheManager {
        public CustomizerRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
            super(cacheWriter, defaultCacheConfiguration);
        }
        @NotNull
        @Override
        protected RedisCache createRedisCache(@NotNull String name, @Nullable RedisCacheConfiguration cacheConfig) {
            String[] array = StringUtils.delimitedListToStringArray(name, "#");
            name = array[0];
            if (array.length > 1) {
                long ttl = Long.parseLong(array[1]);
                if (cacheConfig != null) {
                    cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
                }
            }
            return super.createRedisCache(name, cacheConfig);
        }
    }

}
