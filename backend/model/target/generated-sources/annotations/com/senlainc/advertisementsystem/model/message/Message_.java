package com.senlainc.advertisementsystem.model.message;

import com.senlainc.advertisementsystem.model.user.profile.Profile;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ extends com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel_ {

	public static volatile SingularAttribute<Message, Boolean> readed;
	public static volatile SingularAttribute<Message, LocalDateTime> uploadDate;
	public static volatile SingularAttribute<Message, Profile> sender;
	public static volatile SingularAttribute<Message, Profile> recipient;
	public static volatile SingularAttribute<Message, String> message;

	public static final String READED = "readed";
	public static final String UPLOAD_DATE = "uploadDate";
	public static final String SENDER = "sender";
	public static final String RECIPIENT = "recipient";
	public static final String MESSAGE = "message";

}

