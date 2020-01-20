
package org.gerardo.store.services;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.gerardo.store.entity.Products;
import org.gerardo.store.entity.Variations;

/**
 *
 * @author Gerardo__Cruz
 */
@ApplicationScoped
public class ProductService {
    
    @Inject
    EntityManager em;
    
    public List<Products> getListProducts(){
        List<Products> listProducts = em.createQuery("SELECT p FROM Products p").getResultList();
        return listProducts;
    }
    
    public Products getProduct(Integer idProduct){
        List<Products> listProducts = em.createQuery("SELECT p FROM Products p WHERE p.id = :idProduct").setParameter("idProduct", idProduct).getResultList();
        return listProducts.isEmpty() ? null : listProducts.get(0);
    }
    
    @Transactional
    public void addProduct(Products product){
        em.persist(product);
        em.flush();
    }
    
    @Transactional
    public void editProduct(Products product){
        em.merge(product);
        em.flush();
    }
    
    @Transactional
    public void deleteProduct(Integer idProduct){ 
        List<Variations> listVariations = em.createQuery("SELECT v FROM Variations v WHERE v.productsId.id = :idProduct").setParameter("idProduct", idProduct).getResultList();
        if(!listVariations.isEmpty()){
            em.remove(listVariations.get(0));
        }  
        List<Products> listProducts = em.createQuery("SELECT p FROM Products p WHERE p.id = :idProduct").setParameter("idProduct", idProduct).getResultList();
        if(!listProducts.isEmpty()){
            em.remove(listProducts.get(0));
            em.flush();
        }
    }
}
