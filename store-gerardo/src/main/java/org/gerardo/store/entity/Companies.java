
package org.gerardo.store.entity;

import java.io.Serializable;
import java.util.List; 
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;   

/**
 *
 * @author Gerardo__Cruz
 */
@Entity
@Table(name = "companies") 
public class Companies implements Serializable{ 
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name; 
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "companiesId", orphanRemoval = true)
    private List<Products> listProducts;
        
    public Companies(){
        
    }
    
    public Companies(Integer id, String name){
        this.id = id;
        this.name = name;
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
    
    public List<Products> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Products> listProducts) {
        this.listProducts = listProducts;
    }
    
}
