/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.AnnonceType;
import bean.helper.AnnonceTypeAnnonce;
import bean.helper.CategoryAnnonce;
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
public class AnnonceTypeFacade extends AbstractFacade<AnnonceType> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;
    public List<AnnonceTypeAnnonce> countAnnonceActiveByType() {
        Query query = getEntityManager().createNativeQuery("SELECT "
                + " atype.TYPE, COUNT(*) as nomber"
                + " FROM annoncetype atype, annonce a "
                + " WHERE a.ANNONCETYPE_ID=atype.ID and a.ACTIVE=1  and ANNONCESTATUS='APPROVE'"
        );
        List<Object[]> res =  query.getResultList();

        List<AnnonceTypeAnnonce> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            AnnonceTypeAnnonce annonceTypeAnnonce = new AnnonceTypeAnnonce();
            annonceTypeAnnonce.setType((String) line[0]);
            annonceTypeAnnonce.setNomber((Long) line[1]);
            list.add(annonceTypeAnnonce);
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

    public AnnonceTypeFacade() {
        super(AnnonceType.class);
    }

}
