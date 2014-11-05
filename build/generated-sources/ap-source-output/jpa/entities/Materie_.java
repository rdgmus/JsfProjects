package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Materie.class)
public abstract class Materie_ {

	public static volatile SingularAttribute<Materie, String> annoScolastico;
	public static volatile CollectionAttribute<Materie, OrarioLezioniAs> orarioLezioniAsCollection;
	public static volatile SingularAttribute<Materie, Short> orale;
	public static volatile SingularAttribute<Materie, Short> giudizio;
	public static volatile SingularAttribute<Materie, AnniScolastici> idAnnoScolastico;
	public static volatile SingularAttribute<Materie, Boolean> oraleBool;
	public static volatile SingularAttribute<Materie, Boolean> scrittoBool;
	public static volatile SingularAttribute<Materie, Short> pratico;
	public static volatile SingularAttribute<Materie, Insegnanti> idInsegnante;
	public static volatile SingularAttribute<Materie, Long> idMateria;
	public static volatile SingularAttribute<Materie, Classi> idClasse;
	public static volatile CollectionAttribute<Materie, Lezioni> lezioniCollection;
	public static volatile SingularAttribute<Materie, Boolean> praticoBool;
	public static volatile SingularAttribute<Materie, String> materia;
	public static volatile SingularAttribute<Materie, Short> scritto;
	public static volatile SingularAttribute<Materie, Boolean> giudizioBool;

}

