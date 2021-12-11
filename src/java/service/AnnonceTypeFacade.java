/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.AnnonceType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ok
 */
@Stateless
public class AnnonceTypeFacade extends AbstractFacade<AnnonceType> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnnonceTypeFacade() {
        super(AnnonceType.class);
    }
    
}
