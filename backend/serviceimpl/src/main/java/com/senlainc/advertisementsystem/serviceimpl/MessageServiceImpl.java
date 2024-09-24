package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.MessageRepository;
import com.senlainc.advertisementsystem.dao.ProfileRepository;
import com.senlainc.advertisementsystem.dao.UserRepository;
import com.senlainc.advertisementsystem.dto.message.MessageDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.MessageMapper;
import com.senlainc.advertisementsystem.model.message.Message;
import com.senlainc.advertisementsystem.model.message.Message_;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.serviceapi.MessageService;
import com.senlainc.advertisementsystem.validatorapi.MessageValidator;
import com.senlainc.advertisementsystem.validatorapi.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final MessageValidator messageValidator;
    private final ProfileValidator profileValidator;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ProfileRepository profileRepository, UserRepository userRepository, MessageMapper messageMapper,
                              MessageValidator messageValidator, ProfileValidator profileValidator) {
        this.messageRepository = messageRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
        this.messageValidator = messageValidator;
        this.profileValidator = profileValidator;
    }

    @Override
    public MessageDto add(Object principal, Long recipientId, String message) {
        Profile sender = profileRepository.getByUserId(((JwtUser) principal).getId());
        Profile recipient = profileRepository.getByUserId(recipientId);
        profileValidator.checkNotNull(sender);
        profileValidator.checkNotNull(recipient);

        Message entityMessage = new Message(message, LocalDateTime.now(), false, sender, recipient);
        messageRepository.save(entityMessage);
        return messageMapper.toDto(entityMessage);
    }

    @Override
    public MessageDto update(Object principal, Long id, String message) {
        messageValidator.checkPermissionSender(((JwtUser) principal).getId(), id);
        Message messageEntity = messageRepository.getOne(id);
        messageValidator.checkNotNull(messageEntity);

        messageEntity.setMessage(message);
        messageRepository.save(messageEntity);
        return messageMapper.toDto(messageEntity);
    }

    @Override
    public MessageDto partiallyUpdate(Object principal, Long id, ViewUpdateParameter parameter) {
        messageValidator.checkPermissionSender(((JwtUser) principal).getId(), id);

        List<ViewUpdateParameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        messageRepository.partiallyUpdate(Message_.id, id, Converter.convertUpdateParameter(Message_.class, parameters));
        return messageMapper.toDto(messageRepository.getOne(id));
    }

    @Override
    public Boolean delete(Object principal, Long id) {
        messageValidator.checkPermissionSender(((JwtUser) principal).getId(), id);
        return delete(id);
    }

    @Override
    public Boolean delete(Long id) {
        Message message = messageRepository.getOne(id);
        messageValidator.checkNotNull(message);

        messageRepository.delete(message);
        return true;
    }

    @Override
    public MessageDto getById(Object principal, Long id) {
        messageValidator.checkPermissionSender(((JwtUser) principal).getId(), id);
        return getById(id);
    }

    @Override
    public MessageDto getById(Long id) {
        Message message = messageRepository.getOne(id);
        messageValidator.checkNotNull(message);

        return messageMapper.toDto(message);
    }

    @Override
    public List<MessageDto> get(List<ViewSortParameter> parameters) {
        return messageMapper.toDtoList(
                messageRepository.findAll(Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<MessageDto> getSent(Object principal, List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getSent(userId, parameters);
    }

    @Override
    public List<MessageDto> getSent(Long userId, List<ViewSortParameter> parameters) {
        return messageMapper.toDtoList(
                messageRepository.getBySenderUserId(userId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<MessageDto> getReceived(Object principal, List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getReceived(userId, parameters);
    }

    @Override
    public List<MessageDto> getReceived(Long userId, List<ViewSortParameter> parameters) {
        return messageMapper.toDtoList(
                messageRepository.getByRecipientUserId(userId, Converter.convertSortParameter(parameters)));
    }


    @Override
    public List<MessageDto> getSentInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                          List<ViewSortParameter> sortParameters) {
        Long userId = ((JwtUser) principal).getId();
        return getSentInTime(userId, from, to, sortParameters);
    }

    @Override
    public List<MessageDto> getSentInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                          List<ViewSortParameter> sortParameters) {
        return messageMapper.toDtoList(
                messageRepository.getBySenderUserIdAndUploadDateBetween(userId, from, to,
                        Converter.convertSortParameter(sortParameters)));
    }

    @Override
    public List<MessageDto> getReceivedInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                              List<ViewSortParameter> sortParameters) {
        Long userId = ((JwtUser) principal).getId();
        return getReceivedInTime(userId, from, to, sortParameters);
    }

    @Override
    public List<MessageDto> getReceivedInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                              List<ViewSortParameter> sortParameters) {
        return messageMapper.toDtoList(
                messageRepository.getByRecipientUserIdAndUploadDateBetween(userId, from, to,
                        Converter.convertSortParameter(sortParameters)));
    }

    @Override
    public MessageDto setReaded(Object principal, Long id) {
        messageValidator.checkPermissionRecipient(((JwtUser) principal).getId(), id);
        Message message = messageRepository.getOne(id);
        messageValidator.checkNotNull(message);

        message.setReaded(true);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Override
    public String getHelloMessage(Object principal) {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "http://localhost:8081/information/hello_message?name={name}";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", userRepository.getByPK(((JwtUser) principal).getId()).getUsername());
        return restTemplate
                .getForObject(resourceUrl, String.class, queryParams);
    }
}
