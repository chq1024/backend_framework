package com.beikei.backend.v2module.api.body;

import com.beikei.backend.v2core.annotion.VersionCheck;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class GrandRequest {

    @Size(min = 1,message = "权限不能为空!")
    private List<String> roles;
    @VersionCheck(check = true,message = "version不能为空!")
    private Long version;
}
