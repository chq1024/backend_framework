package com.beikei.backend.v2core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 自定义SpringSecurity需要用到的参数
 * @author bk
 */
@Component
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties implements Serializable{

    // 是否开启SpringSecurity
    private Boolean enabled;

    // 用于jwt生成的secret
    private String secret;

    // 关于refreshToken的配置
    private RefreshTokenProperties refreshToken;

    @Data
    public static class RefreshTokenProperties implements Serializable {
        private Long limit;
        private Long expire;

        public RefreshTokenProperties() {
            // 被限制使用5次
            this.limit = 5L;
            // 7 day
            this.expire = 604800L;
        }
    }
}
