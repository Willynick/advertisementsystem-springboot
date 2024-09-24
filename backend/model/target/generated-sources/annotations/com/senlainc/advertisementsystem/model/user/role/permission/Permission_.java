package com.senlainc.advertisementsystem.model.user.role.permission;

import com.senlainc.advertisementsystem.model.user.role.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile ListAttribute<Permission, Role> roles;
	public static volatile SingularAttribute<Permission, String> name;

	public static final String ROLES = "roles";
	public static final String NAME = "name";

}

