package com.senlainc.advertisementsystem.model.user.profile;

import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.message.Message;
import com.senlainc.advertisementsystem.model.order.Order;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Profile.class)
public abstract class Profile_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<Profile, LocalDate> date;
	public static volatile ListAttribute<Profile, Rating> sentRatings;
	public static volatile SingularAttribute<Profile, Address> address;
	public static volatile ListAttribute<Profile, Comment> comments;
	public static volatile SingularAttribute<Profile, Boolean> gender;
	public static volatile ListAttribute<Profile, Message> receivedMessages;
	public static volatile ListAttribute<Profile, Message> sentMessages;
	public static volatile SingularAttribute<Profile, Double> amountOfMoney;
	public static volatile SingularAttribute<Profile, String> firstName;
	public static volatile ListAttribute<Profile, Advertisement> advertisements;
	public static volatile SingularAttribute<Profile, Double> averageRating;
	public static volatile ListAttribute<Profile, Rating> receivedRatings;
	public static volatile ListAttribute<Profile, Order> orders;
	public static volatile SingularAttribute<Profile, User> user;
	public static volatile SingularAttribute<Profile, String> email;
	public static volatile SingularAttribute<Profile, String> secondName;

	public static final String DATE = "date";
	public static final String SENT_RATINGS = "sentRatings";
	public static final String ADDRESS = "address";
	public static final String COMMENTS = "comments";
	public static final String GENDER = "gender";
	public static final String RECEIVED_MESSAGES = "receivedMessages";
	public static final String SENT_MESSAGES = "sentMessages";
	public static final String AMOUNT_OF_MONEY = "amountOfMoney";
	public static final String FIRST_NAME = "firstName";
	public static final String ADVERTISEMENTS = "advertisements";
	public static final String AVERAGE_RATING = "averageRating";
	public static final String RECEIVED_RATINGS = "receivedRatings";
	public static final String ORDERS = "orders";
	public static final String USER = "user";
	public static final String EMAIL = "email";
	public static final String SECOND_NAME = "secondName";

}

