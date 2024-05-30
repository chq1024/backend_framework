package com.beikei.backend.v2pojo.transform;

import com.beikei.backend.v2pojo.dto.V2Person;
import com.beikei.backend.v2pojo.entity.V2User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author bk
 */
@Mapper
public interface UserPersonMapper {

    UserPersonMapper INSTANCE = Mappers.getMapper(UserPersonMapper.class);

    V2Person User2Person(V2User v2User);

}
