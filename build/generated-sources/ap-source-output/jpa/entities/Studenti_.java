package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Studenti.class)
public abstract class Studenti_ {

	public static volatile SingularAttribute<Studenti, String> annoScolastico;
	public static volatile SingularAttribute<Studenti, Date> ritiratoData;
	public static volatile SingularAttribute<Studenti, Long> idStudente;
	public static volatile SingularAttribute<Studenti, String> cognome;
	public static volatile SingularAttribute<Studenti, Date> dataEntrata;
	public static volatile SingularAttribute<Studenti, Classi> idClasse;
	public static volatile SingularAttribute<Studenti, Long> idAnnoScolastico;
	public static volatile SingularAttribute<Studenti, String> nome;
	public static volatile CollectionAttribute<Studenti, VotiLezioniStudente> votiLezioniStudenteCollection;
	public static volatile CollectionAttribute<Studenti, OreAssenze> oreAssenzeCollection;
	public static volatile SingularAttribute<Studenti, Short> attivo;

}

