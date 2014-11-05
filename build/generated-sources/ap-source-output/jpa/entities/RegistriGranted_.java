package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RegistriGranted.class)
public abstract class RegistriGranted_ {

	public static volatile SingularAttribute<RegistriGranted, Long> idGrant;
	public static volatile SingularAttribute<RegistriGranted, String> ruolo;
	public static volatile SingularAttribute<RegistriGranted, Long> idMateria;
	public static volatile SingularAttribute<RegistriGranted, Short> readGranted;
	public static volatile SingularAttribute<RegistriGranted, UtentiScuola> idUtente;
	public static volatile SingularAttribute<RegistriGranted, Short> writeGranted;

}

