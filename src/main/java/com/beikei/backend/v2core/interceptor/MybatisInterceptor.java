package com.beikei.backend.v2core.interceptor;

import com.beikei.backend.v2util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * mybatis执行拦截器
 * 用于对sql中的version，updateTime,createTime字段的自动修改和填充
 * 并且对入参进行设置version+1,updateTime&createTime的修改和填充
 * @author bk
 */
@Intercepts({
        @Signature(type = Executor.class,method = "update",args = {MappedStatement.class, Object.class})
})
@Slf4j
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
            log.info("SQL打印拦截器参数 = " + parameter);
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        properties.setProperty("updateTime", String.valueOf(DateUtil.now()));
        Interceptor.super.setProperties(properties);
    }
}
