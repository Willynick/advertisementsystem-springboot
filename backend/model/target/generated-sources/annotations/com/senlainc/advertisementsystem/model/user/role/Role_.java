package com.senlainc.advertisementsystem.model.user.role;

import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.role.permission.Permission;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile ListAttribute<Role, Permission> permissions;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile ListAttribute<Role, User> users;

	public static final String PERMISSIONS = "permissions";
	public static final String NAME = "name";
	public static final String USERS = "users";

}

