package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.MessageRepository;
import com.senlainc.advertisementsystem.jwtsecutiry.exception.JwtAuthenticationException;
import com.senlainc.advertisementsystem.model.message.Message;
import com.senlainc.advertisementsystem.validatorapi.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageValidatorImpl extends AbstractValidator<Message> implements MessageValidator {

    private final MessageRepository messageRepository;

    @Autowired
    protected MessageValidatorImpl(MessageRepository messageRepository) {
        super("Message");
        this.messageRepository = messageRepository;
    }

    @Override
    public void checkPermissionSender(Long userId, Long id) {
        if (!messageRepository.getSenderUserIdById(id).equals(userId)) {
            throw new JwtAuthenticationException(Constants.ACCESS_DENIED);
        }
    }

    @Override
    public void checkPermissionRecipient(Long userId, Long id) {
        if (!messageRepository.getRecipientUserIdById(id).equals(userId)) {
            throw new JwtAuthenticationException(Constants.ACCESS_DENIED);
        }
    }
}
