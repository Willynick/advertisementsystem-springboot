package com.senlainc.advertisementsystem.model.category;

import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile ListAttribute<Category, Advertisement> advertisements;
	public static volatile SingularAttribute<Category, String> name;
	public static volatile SingularAttribute<Category, Category> parentCategory;
	public static volatile ListAttribute<Category, Category> categories;

	public static final String ADVERTISEMENTS = "advertisements";
	public static final String NAME = "name";
	public static final String PARENT_CATEGORY = "parentCategory";
	public static final String CATEGORIES = "categories";

}

