package bean;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ {

	public static volatile ListAttribute<Category, Annonce> annonces;
	public static volatile SingularAttribute<Category, String> name;
	public static volatile SingularAttribute<Category, Long> id;

}

