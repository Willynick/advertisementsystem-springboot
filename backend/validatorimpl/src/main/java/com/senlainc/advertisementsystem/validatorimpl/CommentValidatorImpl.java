package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.CommentRepository;
import com.senlainc.advertisementsystem.jwtsecutiry.exception.JwtAuthenticationException;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.validatorapi.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentValidatorImpl extends AbstractValidator<Comment> implements CommentValidator {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentValidatorImpl(CommentRepository commentRepository) {
        super("Comment");
        this.commentRepository = commentRepository;
    }

    @Override
    public void checkPermission(Long userId, Long id) {
        if (!commentRepository.getProfileUserIdById(id).equals(userId)) {
            throw new JwtAuthenticationException(Constants.ACCESS_DENIED);
        }
    }
}
