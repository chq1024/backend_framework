package com.beikei.backend.v2pojo.mapper;


import com.beikei.backend.v2core.core.V2CommentMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCharacterRelationShipMapper extends V2CommentMapper {

    List<String> selectRolesByUid(Long uid);
}
