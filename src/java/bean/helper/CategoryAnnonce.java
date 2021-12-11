/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.helper;

/**
 *
 * @author ok
 */
public class CategoryAnnonce {
     private String name;
     private  Long nomber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNomber() {
        return nomber;
    }

    public void setNomber(Long nomber) {
        this.nomber = nomber;
    }

   

    @Override
    public String toString() {
        return "CategoryAnnonce{" + "name=" + name + '}';
    }
    
    
     
     
}
