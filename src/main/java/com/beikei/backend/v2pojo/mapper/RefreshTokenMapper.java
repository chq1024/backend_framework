package com.beikei.backend.v2pojo.mapper;


import com.beikei.backend.v2core.core.V2CommentMapper;
import com.beikei.backend.v2pojo.entity.V2RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RefreshTokenMapper extends V2CommentMapper {

    V2RefreshToken one(@Param("uid") Long uid);

    int update(@Param("id") Long id,@Param("token") V2RefreshToken before);
}
