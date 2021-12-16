/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author yassine
 */
@Entity
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String codePostal;
    @OneToMany(mappedBy = "city")
    private List<Annonceur> annonceurs;

    @OneToMany(mappedBy = "city")
    private List<Secteur> secteurs;

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

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

    public List<Annonceur> getAnnonceurs() {
        return annonceurs;
    }

    public void setAnnonceurs(List<Annonceur> annonceurs) {
        this.annonceurs = annonceurs;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof City) && (id != null)
                ? id.equals(((City) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

}
