package com.beikei.backend.v2orm.helper;

import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2orm.mapper.UserCharacterRelationShipMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关系helper
 * @author bk
 */
@Component
public class UserCharacterRelationShipHelper extends V2CommentDomainHelper {

    private final UserCharacterRelationShipMapper shipMapper;

    public UserCharacterRelationShipHelper(UserCharacterRelationShipMapper shipMapper) {
        super(shipMapper);
        this.shipMapper = shipMapper;
    }

    public List<GrantedAuthority> queryByUid(Long uid) {
        List<String> roles = shipMapper.roles(uid);
        return roles.stream().map(r->{
            String nr = "ROLE_" + r.trim();
            return new SimpleGrantedAuthority(nr);
        }).collect(Collectors.toList());
    }
}
