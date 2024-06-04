package com.beikei.backend.v2orm.helper;

import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2orm.mapper.CharacterMapper;
import org.springframework.stereotype.Component;

/**
 * @author bk
 */
@Component
public class CharacterHelper extends V2CommentDomainHelper {

    private final CharacterMapper characterMapper;

    public CharacterHelper(CharacterMapper characterMapper) {
        super(characterMapper);
        this.characterMapper = characterMapper;
    }

}