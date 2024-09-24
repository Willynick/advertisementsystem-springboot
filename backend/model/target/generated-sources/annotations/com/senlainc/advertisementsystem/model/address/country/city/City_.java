package com.senlainc.advertisementsystem.model.address.country.city;

import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.address.country.Country;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<City, Country> country;
	public static volatile ListAttribute<City, Address> addresses;
	public static volatile SingularAttribute<City, String> name;

	public static final String COUNTRY = "country";
	public static final String ADDRESSES = "addresses";
	public static final String NAME = "name";

}

