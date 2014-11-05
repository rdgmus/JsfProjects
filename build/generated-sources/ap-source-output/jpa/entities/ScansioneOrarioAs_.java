package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScansioneOrarioAs.class)
public abstract class ScansioneOrarioAs_ {

	public static volatile SingularAttribute<ScansioneOrarioAs, Long> idScansione;
	public static volatile SingularAttribute<ScansioneOrarioAs, Short> intervallo;
	public static volatile SingularAttribute<ScansioneOrarioAs, Boolean> lezioneAsBool;
	public static volatile SingularAttribute<ScansioneOrarioAs, Date> inizia;
	public static volatile CollectionAttribute<ScansioneOrarioAs, OrarioLezioniAs> orarioLezioniAsCollection;
	public static volatile SingularAttribute<ScansioneOrarioAs, String> giornoSettimana;
	public static volatile SingularAttribute<ScansioneOrarioAs, Integer> numOraLezione;
	public static volatile SingularAttribute<ScansioneOrarioAs, AnniScolastici> idAnnoScolastico;
	public static volatile SingularAttribute<ScansioneOrarioAs, Short> lezione;
	public static volatile SingularAttribute<ScansioneOrarioAs, Date> finisce;

}

