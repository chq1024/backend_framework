package com.beikei.backend.v2pojo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录警告记录
 * 用于用户
 * 1. 登录IP变动记录
 * 2. 角色变动
 * @author bk
 */
@Data
@NoArgsConstructor
public class V2UserWarningRecord {

    private Long id;
    private Long uid;
    private String before;
    private String after;
}
