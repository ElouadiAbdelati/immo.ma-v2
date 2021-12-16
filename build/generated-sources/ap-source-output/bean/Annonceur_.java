package bean;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Annonceur.class)
public abstract class Annonceur_ {

	public static volatile SingularAttribute<Annonceur, String> firstname;
	public static volatile SingularAttribute<Annonceur, String> password;
	public static volatile SingularAttribute<Annonceur, String> address;
	public static volatile SingularAttribute<Annonceur, City> city;
	public static volatile ListAttribute<Annonceur, Annonce> annonces;
	public static volatile SingularAttribute<Annonceur, String> telephone;
	public static volatile SingularAttribute<Annonceur, Long> id;
	public static volatile SingularAttribute<Annonceur, String> email;
	public static volatile SingularAttribute<Annonceur, String> lastname;

}

