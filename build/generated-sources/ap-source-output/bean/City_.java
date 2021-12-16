package bean;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ {

	public static volatile SingularAttribute<City, String> name;
	public static volatile ListAttribute<City, Secteur> secteurs;
	public static volatile SingularAttribute<City, Long> id;
	public static volatile SingularAttribute<City, String> codePostal;
	public static volatile ListAttribute<City, Annonceur> annonceurs;

}

