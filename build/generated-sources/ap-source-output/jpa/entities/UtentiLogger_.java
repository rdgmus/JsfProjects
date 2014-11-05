package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UtentiLogger.class)
public abstract class UtentiLogger_ {

	public static volatile SingularAttribute<UtentiLogger, String> msgType;
	public static volatile SingularAttribute<UtentiLogger, Date> whenRegistered;
	public static volatile SingularAttribute<UtentiLogger, Date> msgSentTime;
	public static volatile SingularAttribute<UtentiLogger, String> message;
	public static volatile SingularAttribute<UtentiLogger, UtentiScuola> idUtente;
	public static volatile SingularAttribute<UtentiLogger, Short> msgToUtente;
	public static volatile SingularAttribute<UtentiLogger, Long> idLog;

}

