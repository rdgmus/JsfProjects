package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LezioniMateria.class)
public abstract class LezioniMateria_ {

	public static volatile SingularAttribute<LezioniMateria, Long> idLezione;
	public static volatile SingularAttribute<LezioniMateria, String> annoScolastico;
	public static volatile SingularAttribute<LezioniMateria, String> nomeClasse;
	public static volatile SingularAttribute<LezioniMateria, String> nomeScuola;
	public static volatile SingularAttribute<LezioniMateria, Date> endDate;
	public static volatile SingularAttribute<LezioniMateria, Short> orale;
	public static volatile SingularAttribute<LezioniMateria, Long> idAnnoScolastico;
	public static volatile SingularAttribute<LezioniMateria, Short> giudizio;
	public static volatile SingularAttribute<LezioniMateria, Long> idScuola;
	public static volatile SingularAttribute<LezioniMateria, Date> dataLezione;
	public static volatile SingularAttribute<LezioniMateria, Short> pratico;
	public static volatile SingularAttribute<LezioniMateria, Long> idInsegnante;
	public static volatile SingularAttribute<LezioniMateria, String> tipoScuolaAcronimo;
	public static volatile SingularAttribute<LezioniMateria, Short> freezeLezione;
	public static volatile SingularAttribute<LezioniMateria, Long> idMateria;
	public static volatile SingularAttribute<LezioniMateria, Long> idClasse;
	public static volatile SingularAttribute<LezioniMateria, String> argomento;
	public static volatile SingularAttribute<LezioniMateria, String> materia;
	public static volatile SingularAttribute<LezioniMateria, Short> scritto;
	public static volatile SingularAttribute<LezioniMateria, Date> startDate;
	public static volatile SingularAttribute<LezioniMateria, Integer> oreLezione;

}

