/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonce;
//import bean.Annonce_;
import bean.AnnonceStatus;
import bean.AnnonceType;
import bean.Annonceur;
import bean.Category;
import bean.City;
import bean.Secteur;
import bean.helper.CategoryAnnonce;
import com.mchange.v2.c3p0.C3P0Registry;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.json.JSONObject;
import javax.ejb.EJB;

/**
 *
 * @author ok
 */
@Stateless
public class AnnonceFacade extends AbstractFacade<Annonce> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;


    public List<Annonce> search(City city, AnnonceType annonceType,
            int nbrchambresSearch, int tailleMinimaleSearch, int tailleMaxSearch, int nbrThermesSearch) {

        String query = "SELECT annonce.* FROM annonce ";
        boolean whereAdded = false;

        if (city != null && annonceType != null) {
            whereAdded = true;
            query += " , annoncetype ,city ,secteur  WHERE annonce.ANNONCETYPE_ID = annoncetype.ID and annonce.SECTEUR_ID=secteur.ID and secteur.CITY_ID=city.ID and city.NAME =" + '"' + city.getName() + '"' + " and annoncetype.TYPE =" + '"' + annonceType.getType() + '"';
        } else if (city != null) {
            whereAdded = true;
            query += " , city ,secteur  WHERE annonce.SECTEUR_ID=secteur.ID and secteur.CITY_ID=city.ID and city.NAME =" + '"' + city.getName() + '"';

        } else if (annonceType != null) {
            whereAdded = true;
            query += " , annoncetype WHERE annonce.ANNONCETYPE_ID = annoncetype.ID and annoncetype.TYPE =" + '"' + annonceType.getType() + '"';

        }
        if (!whereAdded) {
            query += "WHERE active=1 and 1=1 ";
        }

        if (nbrchambresSearch > 0) {
            query += " and annonce.CHAMBER=" + nbrchambresSearch;

        }
        if (tailleMinimaleSearch > 0) {
            query += " AND annonce.SURFACE >=" + tailleMinimaleSearch;

        }
        if (tailleMaxSearch > 0) {
            query += " AND  annonce.SURFACE<=" + tailleMaxSearch;

        }

        if (nbrThermesSearch > 0) {
            query += " and  annonce.TOILET=" + nbrThermesSearch;

        }

        System.out.println("service.AnnonceFacade.search()   " + query);

        Query query2 = getEntityManager().createNativeQuery(query);

        List<Object[]> res = query2.getResultList();

        List<Annonce> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            Annonce annonce = new Annonce();
            annonce.setId((Long) line[0]);
            annonce.setActive((Boolean) line[1]);
            annonce.setAddress((String) line[2]);
            annonce.setAnnonceStatus((String) line[3]);
            annonce.setChamber((Integer) line[4]);
            annonce.setDepartment((String) line[5]);
            annonce.setDescription((String) line[6]);
            annonce.setDevise((String) line[7]);
            annonce.setImagePath((String) line[8]);
            annonce.setLatitude((String) line[9]);
            annonce.setLongitude((String) line[10]);
            annonce.setPieces((Integer) line[11]);
            annonce.setPiscine((Boolean) line[12]);
            annonce.setPrix((BigDecimal) line[13]);
            annonce.setReference((String) line[14]);
          
            annonce.setSurface((Double) line[16]);
            annonce.setTitle((String) line[17]);
            annonce.setToilet((Integer) line[18]);

            AnnonceType annonceType1 =  getEntityManager().find(AnnonceType.class,(Long) line[19]);
            annonce.setAnnonceType(annonceType1);

            Category category = getEntityManager().find(Category.class,(Long) line[20]);
            annonce.setCategory(category);

            Secteur secteur = getEntityManager().find(Secteur.class,(Long) line[21]);
            annonce.setSecteur(secteur);

            Annonceur annonceur = getEntityManager().find(Annonceur.class,(Long) line[22]);
            annonce.setAnnonceur(annonceur);

            list.add(annonce);
        }

        System.err.println("list " + list.size());

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        return list;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnnonceFacade() {
        super(Annonce.class);
    }

//       public List<Annonce> findLastInserted(int[] range) {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(Annonce.class)).orderBy(getEntityManager().getCriteriaBuilder().desc(cq.from(Annonce.class).get(Annonce_.id)))
//                ;
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        q.setMaxResults(range[1] - range[0] + 1);
//        q.setFirstResult(range[0]);
//        return q.getResultList();
//    }
    
}
