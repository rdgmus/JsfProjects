package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Scuole.class)
public abstract class Scuole_ {

	public static volatile SingularAttribute<Scuole, String> nomeScuola;
	public static volatile SingularAttribute<Scuole, String> indirizzo;
	public static volatile CollectionAttribute<Scuole, PeriodiAnnoScolastico> periodiAnnoScolasticoCollection;
	public static volatile SingularAttribute<Scuole, Long> idScuola;
	public static volatile SingularAttribute<Scuole, String> provincia;
	public static volatile CollectionAttribute<Scuole, Classi> classiCollection;
	public static volatile SingularAttribute<Scuole, String> tipoScuolaAcronimo;
	public static volatile SingularAttribute<Scuole, String> cap;
	public static volatile CollectionAttribute<Scuole, Aule> auleCollection;
	public static volatile SingularAttribute<Scuole, String> web;
	public static volatile CollectionAttribute<Scuole, AnniScolastici> anniScolasticiCollection;
	public static volatile SingularAttribute<Scuole, String> telefono;
	public static volatile SingularAttribute<Scuole, String> fax;
	public static volatile SingularAttribute<Scuole, String> città;
	public static volatile SingularAttribute<Scuole, String> email;

}

