/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author yassine
 */
@Entity()
public class Annonce implements Serializable {
    
    
    private static final long serialVersionUID = 6659221496655031009L;
  
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    private String title;
    private String reference;
    private String description;
    private String address; 
    private boolean piscine; 
    private int pieces; 
    private boolean active;  
    private double surface;
    private String devise; 
    private BigDecimal prix; 
    private int chamber; 
    private String latitude; 
    private String longitude; 
    private String department; 
    private int toilet;   
    private AnnonceStatus status;

    @OneToOne()
    private Annonceur annonceur;

    @ManyToOne()
    private Category category;

    @ManyToOne()
    private AnnonceType annonceType;

    @ManyToOne
    private Secteur secteur;
    
    private String imagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPiscine() {
        return piscine;
    }

    public void setPiscine(boolean piscine) {
        this.piscine = piscine;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public int getChamber() {
        return chamber;
    }

    public void setChamber(int chamber) {
        this.chamber = chamber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getToilet() {
        return toilet;
    }

    public void setToilet(int toilet) {
        this.toilet = toilet;
    }

    public AnnonceStatus getStatus() {
        return status;
    }

    public void setStatus(AnnonceStatus status) {
        this.status = status;
    }

    public Annonceur getAnnonceur() {
        return annonceur;
    }

    public void setAnnonceur(Annonceur annonceur) {
        this.annonceur = annonceur;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AnnonceType getAnnonceType() {
        return annonceType;
    }

    public void setAnnonceType(AnnonceType annonceType) {
        this.annonceType = annonceType;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Annonce{" + "id=" + id + ", title=" + title + ", reference=" + reference + ", description=" + description + ", address=" + address + ", piscine=" + piscine + ", pieces=" + pieces + ", active=" + active + ", surface=" + surface + ", devise=" + devise + ", prix=" + prix + ", chamber=" + chamber + ", latitude=" + latitude + ", longitude=" + longitude + ", department=" + department + ", toilet=" + toilet + ", status=" + status + ", annonceur=" + annonceur + ", category=" + category + ", annonceType=" + annonceType + ", secteur=" + secteur + ", imagePath=" + imagePath + '}';
    }

    
    
}
