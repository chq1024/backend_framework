package com.beikei.backend.v2module.security.service;

import java.util.Map;

/**
 * @author bk
 */
public interface UserService {

    Map<String,Object> login(String username,String password);
}
