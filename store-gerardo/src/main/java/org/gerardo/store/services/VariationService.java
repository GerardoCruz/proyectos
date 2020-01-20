
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
public class VariationService {
    
    @Inject
    EntityManager em;
    
    public List<Variations> getListVariations(Integer idProduct){ 
        List<Variations> listVariations = em.createQuery("SELECT v FROM Variations v WHERE v.productId = :idProduct").setParameter("idProduct", idProduct).getResultList();
        return listVariations;
    } 
    
    public boolean validateSku(String sku){
        List<Variations> listVariations = em.createQuery("SELECT v FROM Variations v WHERE v.sku = :sku").setParameter("sku", sku).getResultList();
        return listVariations.isEmpty();
    }
    
    @Transactional
    public void addVariation(Variations variations){
        em.persist(variations);
        em.flush();
    } 
    
    @Transactional
    public void deleteVariation(Integer idVariation){ 
        List<Variations> listVariations = em.createQuery("SELECT v FROM Variations v WHERE v.id = :idVariation").setParameter("idVariation", idVariation).getResultList();
        if(!listVariations.isEmpty()){
            em.remove(listVariations.get(0));
            em.flush(); 
        }   
    }
}
