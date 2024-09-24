package com.senlainc.advertisementsystem.model.order.purchase;

import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.order.Order;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Purchase.class)
public abstract class Purchase_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<Purchase, Advertisement> advertisement;
	public static volatile SingularAttribute<Purchase, Double> earnedMoney;
	public static volatile SingularAttribute<Purchase, Order> order;

	public static final String ADVERTISEMENT = "advertisement";
	public static final String EARNED_MONEY = "earnedMoney";
	public static final String ORDER = "order";

}

