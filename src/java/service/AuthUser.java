/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonceur;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless 
public class AuthUser {

    private Annonceur curUser;

    public void signOut() {
        curUser = null;
    }

    public void signIn(Annonceur user) {
        curUser = user;
    }

    public Annonceur getCurUser() {
        return curUser;
    }

    public void setCurUser(Annonceur curUser) {
        this.curUser = curUser;
        System.out.println("sadsdsadsdasdasdsa");
        System.out.println(curUser.toString());
    }

}
