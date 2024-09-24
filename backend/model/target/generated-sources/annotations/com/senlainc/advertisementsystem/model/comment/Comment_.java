package com.senlainc.advertisementsystem.model.comment;

import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<Comment, LocalDateTime> uploadDate;
	public static volatile SingularAttribute<Comment, Profile> profile;
	public static volatile SingularAttribute<Comment, Advertisement> advertisement;
	public static volatile SingularAttribute<Comment, String> text;

	public static final String UPLOAD_DATE = "uploadDate";
	public static final String PROFILE = "profile";
	public static final String ADVERTISEMENT = "advertisement";
	public static final String TEXT = "text";

}

