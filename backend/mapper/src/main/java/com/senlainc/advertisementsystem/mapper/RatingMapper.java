package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.user.profile.rating.RatingDto;
import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RatingMapper extends AbstractMapper<Rating, RatingDto, RatingDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public RatingMapper(ModelMapper modelMapper) {
        super(modelMapper, Rating.class, RatingDto.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Rating.class, RatingDto.class)
                .addMappings(m -> {
                    m.skip(RatingDto::setSenderId);
                    m.skip(RatingDto::setRecipientId);
                }).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapFields(Rating source, RatingDto destination) {
        destination.setSenderId(source.getSender().getUser().getId());
        destination.setRecipientId(source.getRecipient().getUser().getId());
    }

    @Override
    protected void mapFields(RatingDto source, Rating destination) {

    }
}
