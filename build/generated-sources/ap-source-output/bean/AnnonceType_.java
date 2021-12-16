package bean;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AnnonceType.class)
public abstract class AnnonceType_ {

	public static volatile ListAttribute<AnnonceType, Annonce> annonces;
	public static volatile SingularAttribute<AnnonceType, Long> id;
	public static volatile SingularAttribute<AnnonceType, String> type;

}

