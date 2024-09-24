package com.senlainc.advertisementsystem.model.address.country;

import com.senlainc.advertisementsystem.model.address.country.city.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Country.class)
public abstract class Country_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile ListAttribute<Country, City> cities;
	public static volatile SingularAttribute<Country, String> name;

	public static final String CITIES = "cities";
	public static final String NAME = "name";

}

