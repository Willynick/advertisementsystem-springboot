package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.message.MessageDto;
import com.senlainc.advertisementsystem.model.message.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageMapper extends AbstractMapper<Message, MessageDto, MessageDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public MessageMapper(ModelMapper modelMapper) {
        super(modelMapper, Message.class, MessageDto.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Message.class, MessageDto.class)
                .addMappings(m -> {
                    m.skip(MessageDto::setSenderId);
                    m.skip(MessageDto::setRecipientId);
                }).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapFields(Message source, MessageDto destination) {
        destination.setSenderId(source.getSender().getUser().getId());
        destination.setRecipientId(source.getRecipient().getUser().getId());
    }

    @Override
    protected void mapFields(MessageDto source, Message destination) {
    }
}
