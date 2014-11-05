package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Insegnanti.class)
public abstract class Insegnanti_ {

	public static volatile SingularAttribute<Insegnanti, Long> idInsegnante;
	public static volatile SingularAttribute<Insegnanti, String> annoScolastico;
	public static volatile SingularAttribute<Insegnanti, String> cognome;
	public static volatile SingularAttribute<Insegnanti, Classi> idClasse;
	public static volatile SingularAttribute<Insegnanti, AnniScolastici> idAnnoScolastico;
	public static volatile SingularAttribute<Insegnanti, String> nome;
	public static volatile CollectionAttribute<Insegnanti, Materie> materieCollection;
	public static volatile CollectionAttribute<Insegnanti, OrarioInsegnante> orarioInsegnanteCollection;

}

