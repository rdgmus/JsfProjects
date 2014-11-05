package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Lezioni.class)
public abstract class Lezioni_ {

	public static volatile SingularAttribute<Lezioni, Long> idLezione;
	public static volatile SingularAttribute<Lezioni, Short> freezeLezione;
	public static volatile SingularAttribute<Lezioni, Materie> idMateria;
	public static volatile SingularAttribute<Lezioni, String> argomento;
	public static volatile CollectionAttribute<Lezioni, VotiLezioniStudente> votiLezioniStudenteCollection;
	public static volatile CollectionAttribute<Lezioni, OreAssenze> oreAssenzeCollection;
	public static volatile SingularAttribute<Lezioni, Date> dataLezione;
	public static volatile SingularAttribute<Lezioni, Integer> oreLezione;

}

