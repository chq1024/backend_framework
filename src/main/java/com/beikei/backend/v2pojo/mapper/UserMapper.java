package com.beikei.backend.v2pojo.mapper;

import com.beikei.backend.v2core.core.V2CommentMapper;
import com.beikei.backend.v2pojo.entity.V2User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends V2CommentMapper {

    V2User one(@Param("account") String account);
}
