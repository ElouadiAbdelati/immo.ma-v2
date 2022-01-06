/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonceur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ok
 */
@Stateless
public class AnnonceurFacade extends AbstractFacade<Annonceur> {

    @PersistenceContext(unitName = "immo.maPU")
    private EntityManager em;
   
    
    public Annonceur findBylogin(String username) {
        return findBy("email", username);
    }

    public void save(Annonceur annonceur) {
        create(annonceur);
    }

    public int seConnecter(String email, String password) {
        Annonceur loadedAnnonceur = findBylogin(email);
        if (loadedAnnonceur == null) {
            return -1;
        } else if (!loadedAnnonceur.getPassword().equals(password)) {
            return -2;
        } else {
            return 1;
        }
    }

    public int seEnregister(Annonceur annonceur) {
        System.out.println(annonceur.toString());
        create(annonceur);
        Annonceur loadedAnnonceur = findBylogin(annonceur.getEmail());
        if (loadedAnnonceur == null) {
            return -1;
        } else {
            return 1;
        }
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnnonceurFacade() {
        super(Annonceur.class);
    }
    
}
