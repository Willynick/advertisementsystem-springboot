package com.senlainc.advertisementsystem.model.advertisement;

import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.category.Category;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Advertisement.class)
public abstract class Advertisement_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile ListAttribute<Advertisement, Address> addresses;
	public static volatile ListAttribute<Advertisement, Comment> comments;
	public static volatile ListAttribute<Advertisement, Purchase> purchases;
	public static volatile SingularAttribute<Advertisement, Profile> profile;
	public static volatile SingularAttribute<Advertisement, String> description;
	public static volatile SingularAttribute<Advertisement, String> title;
	public static volatile SingularAttribute<Advertisement, String> manufacturer;
	public static volatile SingularAttribute<Advertisement, ProductCondition> productCondition;
	public static volatile SingularAttribute<Advertisement, String> phoneNumber;
	public static volatile SingularAttribute<Advertisement, LocalDateTime> uploadDate;
	public static volatile SingularAttribute<Advertisement, Double> price;
	public static volatile SingularAttribute<Advertisement, LocalDateTime> changeDate;
	public static volatile SingularAttribute<Advertisement, Category> category;
	public static volatile SingularAttribute<Advertisement, AdvertisementStatus> advertisementStatus;

	public static final String ADDRESSES = "addresses";
	public static final String COMMENTS = "comments";
	public static final String PURCHASES = "purchases";
	public static final String PROFILE = "profile";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String MANUFACTURER = "manufacturer";
	public static final String PRODUCT_CONDITION = "productCondition";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String UPLOAD_DATE = "uploadDate";
	public static final String PRICE = "price";
	public static final String CHANGE_DATE = "changeDate";
	public static final String CATEGORY = "category";
	public static final String ADVERTISEMENT_STATUS = "advertisementStatus";

}

