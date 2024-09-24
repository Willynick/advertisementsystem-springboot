package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.AdvertisementRepository;
import com.senlainc.advertisementsystem.dao.CommentRepository;
import com.senlainc.advertisementsystem.dao.ProfileRepository;
import com.senlainc.advertisementsystem.dto.comment.CommentDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.CommentMapper;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.comment.Comment_;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.serviceapi.CommentService;
import com.senlainc.advertisementsystem.validatorapi.AdvertisementValidator;
import com.senlainc.advertisementsystem.validatorapi.CommentValidator;
import com.senlainc.advertisementsystem.validatorapi.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final AdvertisementRepository advertisementDao;
    private final CommentMapper commentMapper;
    private final CommentValidator commentValidator;
    private final ProfileValidator profileValidator;
    private final AdvertisementValidator advertisementValidator;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ProfileRepository profileRepository,
                              AdvertisementRepository advertisementDao,
                              CommentMapper commentMapper, CommentValidator commentValidator,
                              ProfileValidator profileValidator, AdvertisementValidator advertisementValidator) {
        this.commentRepository = commentRepository;
        this.profileRepository = profileRepository;
        this.advertisementDao = advertisementDao;
        this.commentMapper = commentMapper;
        this.commentValidator = commentValidator;
        this.profileValidator = profileValidator;
        this.advertisementValidator = advertisementValidator;
    }

    @Override
    public CommentDto add(Object principal, Long advertisementId, String text) {
        Profile profile = profileRepository.getByUserId(((JwtUser) principal).getId());
        Advertisement advertisement = advertisementDao.getOne(advertisementId);
        profileValidator.checkNotNull(profile);
        advertisementValidator.checkNotNull(advertisement);

        Comment comment = new Comment(text, LocalDateTime.now(), profile, advertisement);
        commentRepository.create(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto update(Object principal, Long id, String text) {
        commentValidator.checkPermission(((JwtUser) principal).getId(), id);
        return update(id, text);
    }

    @Override
    public CommentDto update(Long id, String text) {
        Comment comment =  commentRepository.getByPK(id);
        commentValidator.checkNotNull(comment);

        comment.setText(text);
        commentRepository.update(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto partiallyUpdate(Object principal, Long id, ViewUpdateParameter parameter) {
        commentValidator.checkPermission(((JwtUser) principal).getId(), id);
        return partiallyUpdate(id, parameter);
    }

    @Override
    public CommentDto partiallyUpdate(Long id, ViewUpdateParameter parameter) {
        List<ViewUpdateParameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        commentRepository.partiallyUpdate(Comment_.id, id, Converter.convertUpdateParameter(Comment_.class, parameters));
        return commentMapper.toDto(commentRepository.getByPK(id));
    }

    @Override
    public Boolean delete(Object principal, Long id) {
        commentValidator.checkPermission(((JwtUser) principal).getId(), id);
        return delete(id);
    }

    @Override
    public Boolean delete(Long id) {
        Comment comment = commentRepository.getByPK(id);
        commentValidator.checkNotNull(comment);

        commentRepository.delete(comment);
        return true;
    }

    @Override
    public CommentDto getById(Long id) {
        Comment comment = commentRepository.getByPK(id);
        commentValidator.checkNotNull(comment);

        return commentMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> get(List<ViewSortParameter> parameters) {
        return commentMapper.toDtoList(
                commentRepository.getAll(Converter.convertSortParameter(Comment_.class, parameters)));
    }

    @Override
    public List<CommentDto> getByUser(Long userId, List<ViewSortParameter> parameters) {
        return commentMapper.toDtoList(
                commentRepository.getByProfileUserId(userId, Converter.convertSortParameter(Comment_.class, parameters)));
    }

    @Override
    public List<CommentDto> getByAdvertisement(Long advertisementId, List<ViewSortParameter> parameters) {
        return commentMapper.toDtoList(
                commentRepository.getByAdvertisementId(advertisementId, Converter.convertSortParameter(Comment_.class, parameters)));
    }

    @Override
    public List<CommentDto> getByUserInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                            List<ViewSortParameter> sortParameters) {
        return commentMapper.toDtoList(
                commentRepository.getByProfileUserIdAndUploadDateBetween(userId, from, to,
                        Converter.convertSortParameter(Comment_.class, sortParameters)));
    }

    @Override
    public List<CommentDto> getByAdvertisementInTime(Long advertisementId, LocalDateTime from, LocalDateTime to,
                                                     List<ViewSortParameter> sortParameters) {
        return commentMapper.toDtoList(
                commentRepository.getByAdvertisementIdAndUploadDateBetween(advertisementId, from, to,
                        Converter.convertSortParameter(Comment_.class, sortParameters)));
    }
}
