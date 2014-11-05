package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AnniScolastici.class)
public abstract class AnniScolastici_ {

	public static volatile SingularAttribute<AnniScolastici, String> annoScolastico;
	public static volatile SingularAttribute<AnniScolastici, ParametriOrarioAs> parametriOrarioAs;
	public static volatile CollectionAttribute<AnniScolastici, OrarioLezioniAs> orarioLezioniAsCollection;
	public static volatile SingularAttribute<AnniScolastici, Date> endDate;
	public static volatile SingularAttribute<AnniScolastici, Long> idAnnoScolastico;
	public static volatile CollectionAttribute<AnniScolastici, PeriodiAnnoScolastico> periodiAnnoScolasticoCollection;
	public static volatile SingularAttribute<AnniScolastici, Scuole> idScuola;
	public static volatile CollectionAttribute<AnniScolastici, Classi> classiCollection;
	public static volatile CollectionAttribute<AnniScolastici, ScansioneOrarioAs> scansioneOrarioAsCollection;
	public static volatile CollectionAttribute<AnniScolastici, Insegnanti> insegnantiCollection;
	public static volatile CollectionAttribute<AnniScolastici, Materie> materieCollection;
	public static volatile CollectionAttribute<AnniScolastici, OrarioInsegnante> orarioInsegnanteCollection;
	public static volatile SingularAttribute<AnniScolastici, Date> startDate;

}

