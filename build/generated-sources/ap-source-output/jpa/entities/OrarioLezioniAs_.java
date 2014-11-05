package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrarioLezioniAs.class)
public abstract class OrarioLezioniAs_ {

	public static volatile SingularAttribute<OrarioLezioniAs, String> nomeAula;
	public static volatile SingularAttribute<OrarioLezioniAs, ScansioneOrarioAs> idScansione;
	public static volatile SingularAttribute<OrarioLezioniAs, String> nomeClasse;
	public static volatile SingularAttribute<OrarioLezioniAs, Materie> idMateria;
	public static volatile SingularAttribute<OrarioLezioniAs, Classi> idClasse;
	public static volatile SingularAttribute<OrarioLezioniAs, String> giorno;
	public static volatile SingularAttribute<OrarioLezioniAs, AnniScolastici> idAnnoScolastico;
	public static volatile SingularAttribute<OrarioLezioniAs, Aule> idAula;
	public static volatile SingularAttribute<OrarioLezioniAs, Long> idOrarioLezioni;
	public static volatile SingularAttribute<OrarioLezioniAs, String> materia;
	public static volatile SingularAttribute<OrarioLezioniAs, Date> oraInizio;
	public static volatile SingularAttribute<OrarioLezioniAs, Date> oraFine;

}

