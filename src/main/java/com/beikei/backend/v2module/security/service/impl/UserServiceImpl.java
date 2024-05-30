package com.beikei.backend.v2module.security.service.impl;

import com.beikei.backend.v2module.security.cover.V2UserDetail;
import com.beikei.backend.v2module.security.orm.RefreshTokenHelper;
import com.beikei.backend.v2module.security.orm.UserHelper;
import com.beikei.backend.v2module.security.service.UserService;
import com.beikei.backend.v2pojo.entity.V2RefreshToken;
import com.beikei.backend.v2pojo.entity.V2User;
import com.beikei.backend.v2pojo.transform.UserPersonMapper;
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

    private final RefreshTokenHelper refreshTokenHelper;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(RefreshTokenHelper refreshTokenHelper, AuthenticationManager authenticationManager) {
        this.refreshTokenHelper = refreshTokenHelper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        String decodedPassword = SecurityUtil.passwordDecode(password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, decodedPassword);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        V2UserDetail principal = (V2UserDetail)authenticate.getPrincipal();
        V2User v2User = principal.getV2User();
        V2RefreshToken v2RefreshToken = refreshTokenHelper.queryAndCheck(v2User.getId(),false);
        String accessToken = JwtUtil.genderAccessToken(v2User.getId(), v2User.getAccount());
        String refreshToken = JwtUtil.genderRefreshToken(v2User.getId(), v2User.getAccount(), v2RefreshToken.getKeyword());
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("user", UserPersonMapper.INSTANCE.User2Person(v2User));
        responseMap.put("accessToken",accessToken);
        responseMap.put("refreshToken",refreshToken);
        return responseMap;
    }
}
