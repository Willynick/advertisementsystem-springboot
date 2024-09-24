package com.senlainc.advertisementsystem.model.order;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderStatusModel.class)
public abstract class OrderStatusModel_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<OrderStatusModel, LocalDateTime> changeDate;
	public static volatile SingularAttribute<OrderStatusModel, OrderStatus> status;
	public static volatile SingularAttribute<OrderStatusModel, Order> order;

	public static final String CHANGE_DATE = "changeDate";
	public static final String STATUS = "status";
	public static final String ORDER = "order";

}

