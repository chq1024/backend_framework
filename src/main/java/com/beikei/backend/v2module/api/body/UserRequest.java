package com.beikei.backend.v2module.api.body;

import com.beikei.backend.v2core.annotion.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class UserRequest {

    @UidCheck(check = false,groups = {V2CreateGroup.class})
    private Long uid;
    @NotEmpty(groups = {V2CreateGroup.class},message = "账号不能为空!")
    private String username;
    @NotEmpty(groups = {V2CreateGroup.class,V2UpdateGroup.class},message = "用户名不能为空!")
    private String uname;
    @NotEmpty(groups = {V2UpdateGroup.class},message = "昵称不能为空!")
    private String unick;
    private String email;
    private String remark;
    private List<String> roles;
    @VersionCheck(groups = {V2UpdateGroup.class},message = "Version参数不能为空!")
    private String version;
}
