package bean;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Secteur.class)
public abstract class Secteur_ {

	public static volatile SingularAttribute<Secteur, String> code;
	public static volatile SingularAttribute<Secteur, City> city;
	public static volatile SingularAttribute<Secteur, String> libelle;
	public static volatile SingularAttribute<Secteur, Long> id;

}

