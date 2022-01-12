/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.helper;

/**
 *
 * @author soulaimane
 */
public class AnnonceVilleScteurCategoryAndType {

    private String ville;
    private String secteur;
    private String category;
    private String type;
    private Long nombre;

    public AnnonceVilleScteurCategoryAndType() {
    }
    
    public AnnonceVilleScteurCategoryAndType(String ville, String secteur, String category, String type, Long nombre) {
        this.ville = ville;
        this.secteur = secteur;
        this.category = category;
        this.type = type;
        this.nombre = nombre;
    }
    
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNombre() {
        return nombre;
    }

    public void setNombre(Long nombre) {
        this.nombre = nombre;
    }
    
    
}
