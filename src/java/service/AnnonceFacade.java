/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonce;
import bean.AnnonceStatus;
import bean.AnnonceType;
import bean.Annonce_;
import bean.Annonceur;
import bean.Category;
import bean.City;
import bean.Secteur;
import bean.helper.AnnonceVilleScteurCategoryAndType;
import bean.helper.CategoryAnnonce;
import bean.helper.CityAnnonce;
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
import javax.persistence.criteria.ParameterExpression;

/**
 *
 * @author ok
 */
@Stateless
public class AnnonceFacade extends AbstractFacade<Annonce> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;

    public List<Annonce> finActivedAndApproved() {
        final String query = "SELECT item FROM Annonce item WHERE item.active=1 and item.annonceStatus='APPROVE' ";
        System.out.println("query = " + query);
        return getAllAnnonces(query);
    }

    private List<Annonce> getAllAnnonces(String query) {
        List<Annonce> list = getEntityManager().createQuery(query).getResultList();
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    private List<Annonce> getLimit(int limit, String query) {
        List<Annonce> list = getEntityManager().createQuery(query).setMaxResults(limit).getResultList();
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

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
            query += "WHERE active=1 and ANNONCESTATUS='APPROVE' and 1=1 ";
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

            AnnonceType annonceType1 = getEntityManager().find(AnnonceType.class, (Long) line[19]);
            annonce.setAnnonceType(annonceType1);

            Category category = getEntityManager().find(Category.class, (Long) line[20]);
            annonce.setCategory(category);

            Secteur secteur = getEntityManager().find(Secteur.class, (Long) line[21]);
            annonce.setSecteur(secteur);

            Annonceur annonceur = getEntityManager().find(Annonceur.class, (Long) line[22]);
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

    public List<Annonce> findLastInserted(int[] range) {
        final String query = "SELECT item FROM Annonce item WHERE item.active=1 and item.annonceStatus='APPROVE' ORDER BY item.id DESC ";
        System.out.println("query = " + query);
        return getLimit(3, query);
    }

    public List<AnnonceVilleScteurCategoryAndType> getAnnonceVilleScteurCategoryAndType(City citySearch, Category categorAdminySearch, int type) {
        String typeAnnocne = "";
        if (type == 1) {
            typeAnnocne = "vente";
        }
        if (type == 2) {
            typeAnnocne = "louer";
        }

        Query query = getEntityManager().createNativeQuery(
                "SELECT  c.name as ville, s.LIBELLE as secteur, category.NAME as category, annoncetype.TYPE as type, COUNT(*) as nombre "
                + "FROM category , annoncetype ,city c, annonce a,  secteur  s WHERE a.SECTEUR_ID=s.ID and s.CITY_ID=c.id "
                + " and a.ACTIVE=1  and ANNONCESTATUS='APPROVE' and a.CATEGORY_ID = category.ID "
                + "and a.ANNONCETYPE_ID = annoncetype.ID "
                + "and c.name  like " + "'%" + citySearch.getName() + "%'"
                + "and category.name  like" + "'%" + categorAdminySearch.getName() + "%'"
                + "and annoncetype.type like " + "'%" + typeAnnocne + "%'"
                + "GROUP BY c.name ,"
                + " s.LIBELLE ,category.NAME , annoncetype.TYPE;"
        );
        List<Object[]> res = query.getResultList();

        List<AnnonceVilleScteurCategoryAndType> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            AnnonceVilleScteurCategoryAndType result = new AnnonceVilleScteurCategoryAndType();
            result.setVille((String) line[0]);
            result.setSecteur((String) line[1]);
            result.setCategory((String) line[2]);
            result.setType((String) line[3]);
            result.setNombre((Long) line[4]);
            list.add(result);
        }
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }
 
     public List<AnnonceVilleScteurCategoryAndType> getAnnonceVilleScteurCategoryAndType1(City citySearch, Category categorAdminySearch, int type) {
        String typeAnnocne = "";
        if (type == 1) {
            typeAnnocne = "vente";
        }
        if (type == 2) {
            typeAnnocne = "louer";
        }

        Query query = getEntityManager().createNativeQuery(
                "SELECT  c.name as ville, s.LIBELLE as secteur, category.NAME as category, annoncetype.TYPE as type, COUNT(*) as nombre "
                + "FROM category , annoncetype ,city c, annonce a,  secteur  s WHERE a.SECTEUR_ID=s.ID and s.CITY_ID=c.id "
                + " and a.ACTIVE=0  and ANNONCESTATUS='APPROVE' and a.CATEGORY_ID = category.ID "
                + "and a.ANNONCETYPE_ID = annoncetype.ID "
                + "and c.name  like " + "'%" + citySearch.getName() + "%'"
                + "and category.name  like" + "'%" + categorAdminySearch.getName() + "%'"
                + "and annoncetype.type like " + "'%" + typeAnnocne + "%'"
                + "GROUP BY c.name ,"
                + " s.LIBELLE ,category.NAME , annoncetype.TYPE;"
        );
        List<Object[]> res = query.getResultList();

        List<AnnonceVilleScteurCategoryAndType> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            AnnonceVilleScteurCategoryAndType result = new AnnonceVilleScteurCategoryAndType();
            result.setVille((String) line[0]);
            result.setSecteur((String) line[1]);
            result.setCategory((String) line[2]);
            result.setType((String) line[3]);
            result.setNombre((Long) line[4]);
            list.add(result);
        }
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }
}
