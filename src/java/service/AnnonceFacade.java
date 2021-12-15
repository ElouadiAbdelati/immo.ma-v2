/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonce;
import bean.Annonce_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ok
 */
@Stateless
public class AnnonceFacade extends AbstractFacade<Annonce> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnnonceFacade() {
        super(Annonce.class);
    }
       public List<Annonce> findLastInserted(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Annonce.class)).orderBy(getEntityManager().getCriteriaBuilder().desc(cq.from(Annonce.class).get(Annonce_.id)))
                ;
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
   
}
