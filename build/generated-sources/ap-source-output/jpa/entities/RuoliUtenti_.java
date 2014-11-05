package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RuoliUtenti.class)
public abstract class RuoliUtenti_ {

	public static volatile SingularAttribute<RuoliUtenti, String> ruolo;
	public static volatile CollectionAttribute<RuoliUtenti, RuoliGrantedToUtenti> ruoliGrantedToUtentiCollection;
	public static volatile SingularAttribute<RuoliUtenti, Long> idRuolo;

}

