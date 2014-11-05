package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Classi.class)
public abstract class Classi_ {

	public static volatile SingularAttribute<Classi, String> annoScolastico;
	public static volatile SingularAttribute<Classi, String> nomeClasse;
	public static volatile CollectionAttribute<Classi, OrarioLezioniAs> orarioLezioniAsCollection;
	public static volatile SingularAttribute<Classi, Long> idClasse;
	public static volatile CollectionAttribute<Classi, Insegnanti> insegnantiCollection;
	public static volatile SingularAttribute<Classi, AnniScolastici> idAnnoScolastico;
	public static volatile CollectionAttribute<Classi, Materie> materieCollection;
	public static volatile SingularAttribute<Classi, Scuole> idScuola;
	public static volatile CollectionAttribute<Classi, Studenti> studentiCollection;
	public static volatile SingularAttribute<Classi, String> specializzazione;

}

