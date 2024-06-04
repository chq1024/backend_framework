package com.beikei.backend.v2module.user.service;

import com.beikei.backend.v2orm.dto.V2Person;

import java.util.List;
import java.util.Map;

/**
 * 用户基本操作
 * @author bk
 */
public interface UserService {

    Map<String,Object> login(String username,String password);

    V2Person update(Long uid,V2Person v2Person);

    V2Person register(V2Person v2Person);

    V2Person grand(Long uid, List<String> roles, Long version);

    int delete(Long uid);
}
