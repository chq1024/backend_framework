package com.beikei.backend.v2core.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义datasource配置文件
 * @author bk
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DataSourceProperties extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties {
    // mapper资源位置
    private String mapperLocation;
    // typeHandler包位置
    private String typeHandlersPackage;
    // entity包位置
    private String typeAliasesPackage;
}
