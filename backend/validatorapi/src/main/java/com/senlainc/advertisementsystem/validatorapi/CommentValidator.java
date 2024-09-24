package com.senlainc.advertisementsystem.validatorapi;

import com.senlainc.advertisementsystem.model.comment.Comment;

public interface CommentValidator extends Validator<Comment> {

    void checkPermission(Long userId, Long id);
}
