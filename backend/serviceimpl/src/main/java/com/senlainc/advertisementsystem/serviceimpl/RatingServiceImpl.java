package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.ProfileRepository;
import com.senlainc.advertisementsystem.dao.RatingRepository;
import com.senlainc.advertisementsystem.dto.user.profile.rating.RatingDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.RatingMapper;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating;
import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating_;
import com.senlainc.advertisementsystem.serviceapi.RatingService;
import com.senlainc.advertisementsystem.validatorapi.ProfileValidator;
import com.senlainc.advertisementsystem.validatorapi.RatingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ProfileRepository profileRepository;
    private final RatingMapper ratingMapper;
    private final RatingValidator ratingValidator;
    private final ProfileValidator profileValidator;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, ProfileRepository profileRepository, RatingMapper ratingMapper,
                             RatingValidator ratingValidator, ProfileValidator profileValidator) {
        this.ratingRepository = ratingRepository;
        this.profileRepository = profileRepository;
        this.ratingMapper = ratingMapper;
        this.ratingValidator = ratingValidator;
        this.profileValidator = profileValidator;
    }

    @Override
    public RatingDto add(Object principal, Long recipientId, int rating) {
        Long userId = ((JwtUser) principal).getId();

        Profile sender = profileRepository.getByUserId(userId);
        Profile recipient = profileRepository.getByUserId(recipientId);
        profileValidator.checkNotNull(sender);
        profileValidator.checkNotNull(recipient);

        ratingValidator.checkUserAlreadyRated(recipient.getReceivedRatings(), userId);

        Rating ratingEntity = new Rating(rating, sender, recipient);
        ratingRepository.save(ratingEntity);
        setAverageRating(profileRepository.getByUserId(recipientId));
        return ratingMapper.toDto(ratingEntity);
    }

    @Override
    public RatingDto update(Object principal, Long id, int rating) {
        ratingValidator.checkPermission(((JwtUser) principal).getId(), id);
        Rating ratingEntity = ratingRepository.getOne(id);
        ratingValidator.checkNotNull(ratingEntity);

        ratingEntity.setRating(rating);
        ratingRepository.save(ratingEntity);
        setAverageRating(ratingEntity.getRecipient());
        return ratingMapper.toDto(ratingEntity);
    }

    @Override
    public RatingDto partiallyUpdate(Object principal, Long id, ViewUpdateParameter parameter) {
        ratingValidator.checkPermission(((JwtUser) principal).getId(), id);

        int rating = (int) parameter.getValue();
        ratingValidator.checkRatingValue(rating);
        List<ViewUpdateParameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        ratingRepository.partiallyUpdate(Rating_.id, id, Converter.convertUpdateParameter(Rating_.class, parameters));

        Rating ratingFromDb = ratingRepository.getOne(id);
        ratingValidator.checkNotNull(ratingFromDb);
        setAverageRating(ratingFromDb.getRecipient());
        return ratingMapper.toDto(ratingFromDb);
    }

    @Override
    public Boolean delete(Object principal, Long id) {
        ratingValidator.checkPermission(((JwtUser) principal).getId(), id);
        return delete(id);
    }

    @Override
    public Boolean delete(Long id) {
        Rating rating = ratingRepository.getOne(id);
        ratingValidator.checkNotNull(rating);

        ratingRepository.delete(rating);
        Profile profile = rating.getRecipient();
        profile.getReceivedRatings().remove(rating);
        setAverageRating(rating.getRecipient());
        return true;
    }

    private void setAverageRating(Profile recipient) {
        List<Rating> ratings = recipient.getReceivedRatings();
        double averageRating = ratings.stream().mapToDouble(Rating::getRating).sum() / ratings.size();

        recipient.setAverageRating(averageRating);
        profileRepository.save(recipient);
    }

    @Override
    public RatingDto getById(Long id) {
        Rating rating = ratingRepository.getOne(id);
        ratingValidator.checkNotNull(rating);

        return ratingMapper.toDto(rating);
    }

    @Override
    public List<RatingDto> get(ViewSortParameter parameter) {
        List<ViewSortParameter> sortParameters = new ArrayList<>();
        sortParameters.add(parameter);
        return ratingMapper.toDtoList(ratingRepository.findAll(Converter.convertSortParameter(sortParameters)));
    }

    @Override
    public List<RatingDto> getSent(Object principal, ViewSortParameter parameter) {
        List<ViewSortParameter> sortParameters = new ArrayList<>();
        sortParameters.add(parameter);
        return ratingMapper.toDtoList(
                ratingRepository.getBySenderUserId(((JwtUser) principal).getId(),
                        Converter.convertSortParameter(sortParameters)));
    }

    @Override
    public List<RatingDto> getReceived(Object principal, ViewSortParameter parameter) {
        List<ViewSortParameter> sortParameters = new ArrayList<>();
        sortParameters.add(parameter);
        return ratingMapper.toDtoList(
                ratingRepository.getByRecipientUserId(((JwtUser) principal).getId(),
                        Converter.convertSortParameter(sortParameters)));
    }
}
