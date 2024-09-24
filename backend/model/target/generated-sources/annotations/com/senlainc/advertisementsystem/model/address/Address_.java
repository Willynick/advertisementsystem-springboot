package com.senlainc.advertisementsystem.model.address;

import com.senlainc.advertisementsystem.model.address.country.city.City;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<Address, String> address;
	public static volatile SingularAttribute<Address, City> city;
	public static volatile SingularAttribute<Address, Profile> profile;
	public static volatile SingularAttribute<Address, Advertisement> advertisement;

	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String PROFILE = "profile";
	public static final String ADVERTISEMENT = "advertisement";

}

