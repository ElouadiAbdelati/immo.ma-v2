package bean;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Annonce.class)
public abstract class Annonce_ {

	public static volatile SingularAttribute<Annonce, String> address;
	public static volatile SingularAttribute<Annonce, Double> surface;
	public static volatile SingularAttribute<Annonce, BigDecimal> prix;
	public static volatile SingularAttribute<Annonce, String> latitude;
	public static volatile SingularAttribute<Annonce, String> description;
	public static volatile SingularAttribute<Annonce, Boolean> active;
	public static volatile SingularAttribute<Annonce, AnnonceType> annonceType;
	public static volatile SingularAttribute<Annonce, String> title;
	public static volatile SingularAttribute<Annonce, String> devise;
	public static volatile SingularAttribute<Annonce, String> reference;
	public static volatile SingularAttribute<Annonce, Integer> pieces;
	public static volatile SingularAttribute<Annonce, Secteur> secteur;
	public static volatile SingularAttribute<Annonce, Integer> toilet;
	public static volatile SingularAttribute<Annonce, Integer> chamber;
	public static volatile SingularAttribute<Annonce, Long> id;
	public static volatile SingularAttribute<Annonce, String> department;
	public static volatile SingularAttribute<Annonce, Annonceur> annonceur;
	public static volatile SingularAttribute<Annonce, Category> category;
	public static volatile SingularAttribute<Annonce, Boolean> piscine;
	public static volatile SingularAttribute<Annonce, String> longitude;
	public static volatile SingularAttribute<Annonce, AnnonceStatus> status;

}

