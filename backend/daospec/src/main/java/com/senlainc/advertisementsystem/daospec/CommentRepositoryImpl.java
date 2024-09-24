package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.CommentRepository;
import com.senlainc.advertisementsystem.daospec.exception.DatabaseException;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement_;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.comment.Comment_;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.User_;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.model.user.profile.Profile_;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Log4j2
public class CommentRepositoryImpl extends JpaAbstractDao<Comment, Long> implements CommentRepository {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    private final String UPDATE_QUERY = "UPDATE comments SET text = ?, upload_date = ?, profile_id = ?, advertisement_id = ? WHERE id = ?";
    private final String GET_BY_PK_QUERY = "SELECT * FROM comments WHERE id = ?";
    private final String GET_USER_ID_BY_PROFILE_ID = "SELECT user_id FROM profiles WHERE id = ?";

    @Autowired
    public CommentRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        super(Comment.class);
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(Comment object) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE_QUERY);
            statement.setLong(5, object.getId());

            statement.setString(1, object.getText());
            statement.setString(2, object.getUploadDate().toString());
            statement.setLong(3, object.getProfile().getId());
            statement.setLong(4, object.getAdvertisement().getId());
            statement.execute();

        } catch (Exception ex) {
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public Comment getByPK(Long id) {
        return jdbcTemplate.queryForObject(GET_BY_PK_QUERY, (resultSet, rowNum) -> {
            Profile profile = new Profile();
            profile.setId(resultSet.getLong("profile_id"));
            User user = new User();
            user.setId(getUserId(id));
            profile.setUser(user);
            Advertisement advertisement = new Advertisement();
            advertisement.setId(resultSet.getLong("advertisement_id"));
            Comment comment = Comment.builder().text(resultSet.getString("text"))
                    .uploadDate(LocalDateTime.parse(resultSet.getString("upload_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .profile(profile).advertisement(advertisement).build();
            comment.setId(resultSet.getLong("id"));
            return comment;
        }, id);
    }

    private Long getUserId(Long profileId) {
        return jdbcTemplate.queryForObject(GET_USER_ID_BY_PROFILE_ID, (resultSet, rowNum) -> resultSet.getLong("user_id"), profileId);
    }

    @Override
    public List<Comment> getByProfileUserId(Long userId, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> message = cq.from(Comment.class);
        Predicate predicate = cb.equal(message.get(Comment_.profile).get(Profile_.user).get(User_.id), userId);
        return getSortedBy(cb, cq, message, message, predicate, sortParameters, this.getClass(),"getByUserId");
    }

    @Override
    public List<Comment> getByAdvertisementId(Long advertisementId, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> message = cq.from(Comment.class);
        Predicate predicate = cb.equal(message.get(Comment_.advertisement).get(Advertisement_.id), advertisementId);
        return getSortedBy(cb, cq, message, message, predicate, sortParameters, this.getClass(),"getByAdvertisementId");
    }

    @Override
    public List<Comment> getByProfileUserIdAndUploadDateBetween(Long userId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> message = cq.from(Comment.class);
        Predicate predicate = cb.and(
                cb.equal(message.get(Comment_.profile).get(Profile_.user).get(User_.id), userId),
                cb.greaterThan(message.get(Comment_.uploadDate), from),
                cb.lessThan(message.get(Comment_.uploadDate), to)
        );
        return getSortedBy(cb, cq, message, message, predicate, sortParameters, this.getClass(),"getByUserIdInTime");
    }

    @Override
    public List<Comment> getByAdvertisementIdAndUploadDateBetween(Long advertisementId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> message = cq.from(Comment.class);
        Predicate predicate = cb.and(
                cb.equal(message.get(Comment_.advertisement).get(Advertisement_.id), advertisementId),
                cb.greaterThan(message.get(Comment_.uploadDate), from),
                cb.lessThan(message.get(Comment_.uploadDate), to)
        );
        return getSortedBy(cb, cq, message, message, predicate, sortParameters, this.getClass(),
                "getByAdvertisementIdInTime");
    }

    @Override
    public Long getProfileUserIdById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Comment> comment = cq.from(Comment.class);
        Predicate predicate = cb.equal(comment.get(Comment_.id), id);
        Selection<Long> selection = comment.get(Comment_.profile).get(Profile_.user).get(User_.id);
        return getUserId(cb, cq, predicate, selection, this.getClass());
    }
}
