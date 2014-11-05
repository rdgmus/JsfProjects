package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PeriodiAnnoScolastico.class)
public abstract class PeriodiAnnoScolastico_ {

	public static volatile SingularAttribute<PeriodiAnnoScolastico, PeriodiAnnoScolasticoPK> periodiAnnoScolasticoPK;
	public static volatile SingularAttribute<PeriodiAnnoScolastico, AnniScolastici> anniScolastici;
	public static volatile SingularAttribute<PeriodiAnnoScolastico, Scuole> scuole;
	public static volatile SingularAttribute<PeriodiAnnoScolastico, String> periodo;
	public static volatile SingularAttribute<PeriodiAnnoScolastico, Date> endDate;
	public static volatile SingularAttribute<PeriodiAnnoScolastico, Date> startDate;

}

