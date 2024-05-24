package com.beikei.backend.v2core.core;

/**
 * 本层设计得目的是将通用得的helper方法抽象
 * Helper层是orm层的抽象，有两点好处：
 * 1. 可将orm层抽象，组合业务中的多个操作，简化真实业务层的复杂度，将真实业务层尽可能的面向过程，本层注重面向对象
 * 2. 可将orm层框架抽象，可以随意替换orm框架，只需要对本层进行修改，避免对真实业务层的改动
 * @author bk
 */
public class V2CommentDomainHelper {

    public V2CommentMapper commentMapper;

    public V2CommentDomainHelper(V2CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }


}
