package com.senlainc.advertisementsystem.model.order;

import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<Order, LocalDateTime> date;
	public static volatile ListAttribute<Order, Purchase> purchases;
	public static volatile ListAttribute<Order, OrderStatusModel> orderStatuses;
	public static volatile SingularAttribute<Order, Profile> profile;
	public static volatile SingularAttribute<Order, DeliveryType> deliveryType;

	public static final String DATE = "date";
	public static final String PURCHASES = "purchases";
	public static final String ORDER_STATUSES = "orderStatuses";
	public static final String PROFILE = "profile";
	public static final String DELIVERY_TYPE = "deliveryType";

}

