
package org.gerardo.store.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Gerardo__Cruz
 */
@Entity
@Table(name = "variations")
public class Variations implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "brand")
    private String brand;
    
    @Basic(optional = false)
    @Column(name = "sku")
    private String sku;
    
    @Basic(optional = false)
    @Column(name = "stock")
    private Integer stock;
     
     
    @JoinColumn(name = "products_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productsId; 
    
    
    public Variations(){
        
    }
    
    public Variations(Integer id, String name, String brand, String sku, Integer stock, Products productsId){
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.sku = sku;
        this.stock = stock;
        this.productsId = productsId;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    } 

    public void setProductsId(Products productsId) {
        this.productsId = productsId;
    }
    
}
