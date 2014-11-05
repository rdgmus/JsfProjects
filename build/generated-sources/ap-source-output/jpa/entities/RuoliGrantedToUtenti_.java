package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RuoliGrantedToUtenti.class)
public abstract class RuoliGrantedToUtenti_ {

	public static volatile SingularAttribute<RuoliGrantedToUtenti, String> ruolo;
	public static volatile SingularAttribute<RuoliGrantedToUtenti, RuoliUtenti> idRuolo;
	public static volatile SingularAttribute<RuoliGrantedToUtenti, UtentiScuola> idUtente;
	public static volatile SingularAttribute<RuoliGrantedToUtenti, Long> idRuoliGranted;

}

