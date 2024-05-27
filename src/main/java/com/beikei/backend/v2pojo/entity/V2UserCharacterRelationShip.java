package com.beikei.backend.v2pojo.entity;


import com.beikei.backend.v2core.core.V2RecordCommentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 角色和玩家关联表
 * @author bk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class V2UserCharacterRelationShip extends V2RecordCommentEntity {

    public Long id;
    public Long uid;
    public Long cid;
}
