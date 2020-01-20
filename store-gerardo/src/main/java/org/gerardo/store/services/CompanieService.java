/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gerardo.store.services;
 
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject; 
import javax.persistence.EntityManager; 
import javax.transaction.Transactional;
import org.gerardo.store.entity.Companies;
import org.gerardo.store.entity.Products;
import org.gerardo.store.entity.Variations;


/**
 *
 * @author Gerardo__Cruz
 */
@ApplicationScoped
public class CompanieService {
    
    @Inject
    EntityManager em;
    
    public List<Companies> getListCompanies(){
        List<Companies> listCompaines = em.createQuery("SELECT c FROM Companies c", Companies.class).getResultList();
        return listCompaines;
    }
    
    public Companies getCompanie(Integer idCompanie){
        List<Companies> listCompaines = em.createQuery("SELECT c FROM Companies c WHERE c.id = :idCompanie").setParameter("idCompanie", idCompanie).getResultList();
        return listCompaines.isEmpty() ? null : listCompaines.get(0);
    }
    
    @Transactional
    public void addCompanie(Companies companie){
        Companies newCompanie = new Companies();
        newCompanie.setName(companie.getName());
        em.persist(newCompanie);
        em.flush();
    }
    
    @Transactional
    public void editCompanie(Companies companie){
        List<Companies> listCompaines = em.createQuery("SELECT c FROM Companies c WHERE c.id = :idCompanie").setParameter("idCompanie", companie.getId()).getResultList();
        if(!listCompaines.isEmpty()){
            Companies editCompanie = listCompaines.get(0);
            editCompanie.setName(companie.getName()); 
            em.merge(editCompanie);
            em.flush();
        }
    }
    
    @Transactional
    public void deleteCompanie(Integer idCompanie){ 
        List<Products> listProducts = em.createQuery("SELECT p FROM Products p WHERE p.companiesId.id = :idCompanie").setParameter("idCompanie", idCompanie).getResultList();
        if(!listProducts.isEmpty()){
            List<Variations> listVariations = em.createQuery("SELECT v FROM Variations v WHERE v.productsId.id = :idProduct").setParameter("idProduct", listProducts.get(0).getId()).getResultList();
            if(!listVariations.isEmpty()){
                em.remove(listVariations.get(0));
            }
            em.remove(listProducts.get(0));
        }
        List<Companies> listCompanies = em.createQuery("SELECT c FROM Companies c WHERE c.id = :idCompanie").setParameter("idCompanie", idCompanie).getResultList();
        if(!listCompanies.isEmpty()){
            em.remove(listCompanies.get(0));
            em.flush();
        }
    }
}
