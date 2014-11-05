package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Aule.class)
public abstract class Aule_ {

	public static volatile SingularAttribute<Aule, String> nomeAula;
	public static volatile CollectionAttribute<Aule, OrarioLezioniAs> orarioLezioniAsCollection;
	public static volatile SingularAttribute<Aule, Long> idAula;
	public static volatile SingularAttribute<Aule, Scuole> idScuola;

}

