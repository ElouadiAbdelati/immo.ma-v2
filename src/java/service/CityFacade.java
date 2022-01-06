/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.City;
import bean.helper.AnnonceTypeAnnonce;
import bean.helper.CityAnnonce;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class CityFacade extends AbstractFacade<City> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;
    
   public List<CityAnnonce> countAnnonceActiveByCity() {
        Query query = getEntityManager().createNativeQuery("SELECT "
                + " c.name, COUNT(*) as nomber"
                + " FROM city c, annonce a,  secteur  s "
                + " WHERE a.SECTEUR_ID=s.ID and s.CITY_ID=c.id and a.ACTIVE=1  and ANNONCESTATUS='APPROVE'"
        );
        List<Object[]> res =  query.getResultList();

        List<CityAnnonce> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            CityAnnonce cityAnnonce = new CityAnnonce();
            cityAnnonce.setName((String) line[0]);
            cityAnnonce.setNomber((Long) line[1]);
            list.add(cityAnnonce);
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

    public CityFacade() {
        super(City.class);
    }
    
}
