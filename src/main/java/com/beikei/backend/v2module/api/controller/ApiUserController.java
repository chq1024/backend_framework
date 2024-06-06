package com.beikei.backend.v2module.api.controller;

import com.beikei.backend.v2core.annotion.V2CreateGroup;
import com.beikei.backend.v2core.core.V2CommentResponse;
import com.beikei.backend.v2module.api.body.GrandRequest;
import com.beikei.backend.v2module.api.body.LoginRequest;
import com.beikei.backend.v2module.api.body.UserRequest;
import com.beikei.backend.v2module.user.service.UserService;
import com.beikei.backend.v2orm.dto.V2Person;
import com.beikei.backend.v2orm.transform.PersonCoverMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 用户接口类
 * @author bk
 */
@RestController
@RequestMapping("/u")
public class ApiUserController {

    private final UserService userService;

    ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public V2CommentResponse<Map<String,Object>> login(@RequestBody @Validated LoginRequest request) {
        return V2CommentResponse.success(userService.login(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/reg")
    public V2CommentResponse<V2Person> register(@RequestBody @Validated(value = V2CreateGroup.class) UserRequest request) {
        return V2CommentResponse.success(userService.register(PersonCoverMapper.INSTANCE.request2Person(request)));
    }

    @PutMapping("/{uid}")
    public V2CommentResponse<V2Person> update(@PathVariable("uid") @NotNull Long uid, @RequestBody @Validated(value = V2CreateGroup.class) UserRequest request) {
        return V2CommentResponse.success(userService.update(uid,PersonCoverMapper.INSTANCE.request2Person(request)));
    }

    @DeleteMapping("/{uid}")
    @PreAuthorize("hasPermission('ROLE','ADMIN')")
    public V2CommentResponse<Void> delete(@PathVariable("uid") @NotNull Long uid) {
        userService.delete(uid);
        return V2CommentResponse.success(null);
    }

    @PostMapping("/{uid}/grand")
    public V2CommentResponse<V2Person> grand(@PathVariable("uid") @NotNull Long uid,@RequestBody @Validated GrandRequest request) {
        return V2CommentResponse.success(userService.grand(uid,request.getRoles(),request.getVersion()));
    }

}
