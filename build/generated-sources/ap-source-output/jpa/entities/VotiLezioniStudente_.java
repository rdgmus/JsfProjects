package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VotiLezioniStudente.class)
public abstract class VotiLezioniStudente_ {

	public static volatile SingularAttribute<VotiLezioniStudente, Lezioni> lezioni;
	public static volatile SingularAttribute<VotiLezioniStudente, Character> tipoVoto;
	public static volatile SingularAttribute<VotiLezioniStudente, String> votoString;
	public static volatile SingularAttribute<VotiLezioniStudente, Short> giudizio;
	public static volatile SingularAttribute<VotiLezioniStudente, VotiLezioniStudentePK> votiLezioniStudentePK;
	public static volatile SingularAttribute<VotiLezioniStudente, Double> votoNum;
	public static volatile SingularAttribute<VotiLezioniStudente, Studenti> studenti;

}

