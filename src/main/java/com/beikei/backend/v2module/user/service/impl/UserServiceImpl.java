package com.beikei.backend.v2module.user.service.impl;

import com.beikei.backend.v2module.user.cover.V2UserDetail;
import com.beikei.backend.v2module.user.service.UserService;
import com.beikei.backend.v2orm.dto.V2Person;
import com.beikei.backend.v2orm.entity.V2RefreshToken;
import com.beikei.backend.v2orm.entity.V2User;
import com.beikei.backend.v2orm.helper.RefreshTokenHelper;
import com.beikei.backend.v2orm.transform.PersonCoverMapper;
import com.beikei.backend.v2util.JwtUtil;
import com.beikei.backend.v2util.SecurityUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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
        V2Person v2Person = PersonCoverMapper.INSTANCE.user2Person(v2User);
        v2Person.setRoles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        responseMap.put("user", v2Person);
        responseMap.put("accessToken",accessToken);
        responseMap.put("refreshToken",refreshToken);
        return responseMap;
    }

    @Override
    public V2Person update(Long uid,V2Person v2Person) {

        return null;
    }

    @Override
    public V2Person register(V2Person v2Person) {
        return null;
    }

    @Override
    public V2Person grand(Long uid, List<String> roles, Long version) {
        return null;
    }

    @Override
    public int delete(Long uid) {

        return 0;
    }
}
