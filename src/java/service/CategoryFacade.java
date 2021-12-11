/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonce;
import bean.Category;
import bean.helper.CategoryAnnonce;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.json.JSONObject;

/**
 *
 * @author ok
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;

    public List<CategoryAnnonce> coutAnnonceActiveByCategory() {
       // final String query = "SELECT c.name ,COUNT(c) as nomber FROM  Category  c,  Annonce a  WHERE a.category.id=c.id  GROUP BY c.name";
        //System.out.println("query = " + query);
        Query query = getEntityManager().createNativeQuery("SELECT"
                + " c.NAME as name,"
                + " COUNT(*) as nomber"
                + " FROM category c , annonce a "
                + " WHERE a.CATEGORY_ID =c.ID and a.ACTIVE=1 GROUP BY c.NAME "
                );
        
        List<Object[]> res =  query.getResultList();

        List<CategoryAnnonce> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            CategoryAnnonce categoryAnnonce = new CategoryAnnonce();
            categoryAnnonce.setName((String) line[0]);
            categoryAnnonce.setNomber((Long) line[1]);
             System.out.println("service "+ categoryAnnonce.getName());
             System.out.println("service "+ categoryAnnonce.getNomber());
            list.add(categoryAnnonce);
        }
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }

}
