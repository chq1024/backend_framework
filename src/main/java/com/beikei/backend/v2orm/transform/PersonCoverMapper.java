package com.beikei.backend.v2orm.transform;

import com.beikei.backend.v2module.api.body.UserRequest;
import com.beikei.backend.v2orm.dto.V2Person;
import com.beikei.backend.v2orm.entity.V2User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Person转化类
 * @author bk
 */
@Mapper
public interface PersonCoverMapper {

    PersonCoverMapper INSTANCE = Mappers.getMapper(PersonCoverMapper.class);

    @Mapping(source = "id",target = "uid")
    V2Person user2Person(V2User v2User);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    V2User person2User(V2Person person);

    @Mapping(target = "uid",ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "username",ignore = true)
    V2Person request2Person(UserRequest request);

}
