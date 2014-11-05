package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OreAssenze.class)
public abstract class OreAssenze_ {

	public static volatile SingularAttribute<OreAssenze, String> motivoGiustifica;
	public static volatile SingularAttribute<OreAssenze, Lezioni> lezioni;
	public static volatile SingularAttribute<OreAssenze, OreAssenzePK> oreAssenzePK;
	public static volatile SingularAttribute<OreAssenze, Short> giustificaAssenza;
	public static volatile SingularAttribute<OreAssenze, Short> giustificaRitardo;
	public static volatile SingularAttribute<OreAssenze, Short> ritardo;
	public static volatile SingularAttribute<OreAssenze, Short> assenza;
	public static volatile SingularAttribute<OreAssenze, Studenti> studenti;

}

