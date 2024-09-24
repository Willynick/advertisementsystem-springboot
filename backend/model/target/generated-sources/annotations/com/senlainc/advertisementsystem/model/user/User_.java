package com.senlainc.advertisementsystem.model.user;

import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.model.user.role.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Profile> profile;
	public static volatile SingularAttribute<User, Language> language;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, UserStatus> status;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String PROFILE = "profile";
	public static final String LANGUAGE = "language";
	public static final String USERNAME = "username";
	public static final String STATUS = "status";

}

