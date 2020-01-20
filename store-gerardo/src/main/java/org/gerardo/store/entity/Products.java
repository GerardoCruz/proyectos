
package org.gerardo.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Gerardo__Cruz
 */
@Entity
@Table(name = "products")
public class Products implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "stock")
    private Integer stock;
    
    @Basic(optional = false)
    @Column(name = "cost")
    private Double cost;
    
    @Basic(optional = false)
    @Column(name = "price")
    private Double price;
    
    @Basic(optional = false)
    @Column(name = "has_iva")
    private boolean hasIva; 
     
    @JoinColumn(name = "companies_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private Companies companiesId;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "productsId", orphanRemoval = true)
    private List<Variations> listVariations = new ArrayList<>();
    
    public Products(){
        
    }
    
    public Products(Integer id, String name, Integer stock, Double cost, Double price, boolean hasIva, Companies companiesId){
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.cost = cost;
        this.price = price;
        this.hasIva = hasIva;
        this.companiesId = companiesId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isHasIva() {
        return hasIva;
    }

    public void setHasIva(boolean hasIva) {
        this.hasIva = hasIva;
    }
 

    public List<Variations> getListVariations() {
        return listVariations;
    }

    public void setListVariations(List<Variations> listVariations) {
        this.listVariations = listVariations;
    }

    public void setCompaniesId(Companies companiesId) {
        this.companiesId = companiesId;
    }
 
    public void addVariation(Variations variation) {
        listVariations.add(variation);
        variation.setProductsId(this);
    }
 
    public void removeVariation(Variations variation) {
        variation.setProductsId(null);
        this.listVariations.remove(variation);
    }
    
}
