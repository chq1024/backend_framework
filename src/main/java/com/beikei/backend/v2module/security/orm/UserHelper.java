package com.beikei.backend.v2module.security.orm;

import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2pojo.entity.V2User;
import com.beikei.backend.v2pojo.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * 用户orm辅助类
 * @author bk
 */
@Component
public class UserHelper extends V2CommentDomainHelper {

    private final UserMapper userMapper;

    public UserHelper(UserMapper userMapper) {
        super(userMapper);
        this.userMapper = userMapper;
    }

    public V2User selectByUserName(String username) {
        V2User v2User = userMapper.one(username);
        if (v2User == null) {
            throw new V2GameException(ResponseEnum.USER_NOT_MATCH);
        }
        return v2User;
    }
}
