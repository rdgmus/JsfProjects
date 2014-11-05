package jpa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AssenzeStudenti.class)
public abstract class AssenzeStudenti_ {

	public static volatile SingularAttribute<AssenzeStudenti, Integer> numOra;
	public static volatile SingularAttribute<AssenzeStudenti, String> annoScolastico;
	public static volatile SingularAttribute<AssenzeStudenti, Long> idLezione;
	public static volatile SingularAttribute<AssenzeStudenti, String> nomeClasse;
	public static volatile SingularAttribute<AssenzeStudenti, Long> idStudente;
	public static volatile SingularAttribute<AssenzeStudenti, String> cognome;
	public static volatile SingularAttribute<AssenzeStudenti, String> nome;
	public static volatile SingularAttribute<AssenzeStudenti, Short> assenza;
	public static volatile SingularAttribute<AssenzeStudenti, Date> dataLezione;
	public static volatile SingularAttribute<AssenzeStudenti, String> motivoGiustifica;
	public static volatile SingularAttribute<AssenzeStudenti, Short> giustificaAssenza;
	public static volatile SingularAttribute<AssenzeStudenti, Long> idMateria;
	public static volatile SingularAttribute<AssenzeStudenti, Long> idClasse;
	public static volatile SingularAttribute<AssenzeStudenti, Short> giustificaRitardo;
	public static volatile SingularAttribute<AssenzeStudenti, String> argomento;
	public static volatile SingularAttribute<AssenzeStudenti, String> materia;
	public static volatile SingularAttribute<AssenzeStudenti, Short> ritardo;
	public static volatile SingularAttribute<AssenzeStudenti, Long> id;
	public static volatile SingularAttribute<AssenzeStudenti, Integer> oreLezione;

}

