package com.senlainc.advertisementsystem.model.user.profile.raiting;

import com.senlainc.advertisementsystem.model.user.profile.Profile;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rating.class)
public abstract class Rating_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<Rating, Profile> sender;
	public static volatile SingularAttribute<Rating, Integer> rating;
	public static volatile SingularAttribute<Rating, Profile> recipient;

	public static final String SENDER = "sender";
	public static final String RATING = "rating";
	public static final String RECIPIENT = "recipient";

}

