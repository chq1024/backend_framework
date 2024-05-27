package com.beikei.backend.v2module.security.cover;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * accessToken和refreshToken
 * 两者不是一一对应
 * refreshToken是可以被重复使用的，与当前版本号用户及大版本号有关，且暂时只有1次刷新计会
 * @author bk
 */
@Getter
@Setter
public class V2Token implements Serializable {

    private String accessToken;
    private String refreshToken;
}
