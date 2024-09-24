package com.senlainc.advertisementsystem.validatorapi;

import com.senlainc.advertisementsystem.model.message.Message;

public interface MessageValidator extends Validator<Message> {

    void checkPermissionSender(Long userId, Long id);
    void checkPermissionRecipient(Long userId, Long id);
}
