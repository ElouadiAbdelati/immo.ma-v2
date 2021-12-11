/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author yassine
 */
@Entity
public class Category implements Serializable{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private  Long id;
   
    @Column(unique = true)
    private String name;
   
    @OneToMany(mappedBy = "category")
    private List<Annonce> annonces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    @Override
    public String toString() {
        return "Category{" + "name=" + name + '}';
    }
    
    
    
}
