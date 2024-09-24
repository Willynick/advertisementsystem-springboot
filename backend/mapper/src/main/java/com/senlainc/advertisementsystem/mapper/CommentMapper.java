package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.comment.CommentDto;
import com.senlainc.advertisementsystem.model.comment.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CommentMapper extends AbstractMapper<Comment, CommentDto, CommentDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public CommentMapper(ModelMapper modelMapper) {
        super(modelMapper, Comment.class, CommentDto.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Comment.class, CommentDto.class)
                .addMappings(m -> {
                    m.skip(CommentDto::setAdvertisementId);
                    m.skip(CommentDto::setUserId);
                }).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapFields(Comment source, CommentDto destination) {
        destination.setAdvertisementId(source.getAdvertisement().getId());
        destination.setUserId(source.getProfile().getUser().getId());
    }

    @Override
    protected void mapFields(CommentDto source, Comment destination) {
    }
}
