package com.beikei.backend.v2module.security.service.impl;

import com.beikei.backend.v2module.security.cover.V2Token;
import com.beikei.backend.v2module.security.orm.UserHelper;
import com.beikei.backend.v2module.security.service.UserService;
import com.beikei.backend.v2pojo.entity.V2User;
import com.beikei.backend.v2util.JwtUtil;
import com.beikei.backend.v2util.SecurityUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户服务类
 * 处理登录、信息查询等任务
 * @author bk
 */
@Service
@Transactional("primaryTransactionManager")
public class UserServiceImpl implements UserService {

    private final UserHelper userHelper;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserHelper userHelper, AuthenticationManager authenticationManager) {
        this.userHelper = userHelper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Map<String, String> login(String username, String password) {
        String decodedPassword = SecurityUtil.passwordDecode(password);
        V2User user = userHelper.selectByUserName(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), decodedPassword);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        String roles = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("#"));
        V2Token v2Token = JwtUtil.genderToken(user.getId(),user.getAccount(),roles);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("accessToken",v2Token.getAccessToken());
        responseMap.put("refreshToken",v2Token.getRefreshToken());
        return responseMap;
    }
}
