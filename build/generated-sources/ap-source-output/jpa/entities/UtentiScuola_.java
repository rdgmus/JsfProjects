package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UtentiScuola.class)
public abstract class UtentiScuola_ {

	public static volatile SingularAttribute<UtentiScuola, String> password;
	public static volatile SingularAttribute<UtentiScuola, String> cognome;
	public static volatile CollectionAttribute<UtentiScuola, UtentiLogger> utentiLoggerCollection;
	public static volatile CollectionAttribute<UtentiScuola, RuoliGrantedToUtenti> ruoliGrantedToUtentiCollection;
	public static volatile SingularAttribute<UtentiScuola, String> nome;
	public static volatile SingularAttribute<UtentiScuola, Long> idUtente;
	public static volatile SingularAttribute<UtentiScuola, String> email;
	public static volatile CollectionAttribute<UtentiScuola, RegistriGranted> registriGrantedCollection;

}

