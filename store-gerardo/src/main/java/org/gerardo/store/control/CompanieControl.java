
package org.gerardo.store.control;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.gerardo.store.entity.Companies;
import org.gerardo.store.services.CompanieService;

/**
 *
 * @author Gerardo__Cruz
 */
@ApplicationScoped
public class CompanieControl {
 
    @Inject
    CompanieService companieService;
    
    public List<Companies> getListCompanies(){
        return companieService.getListCompanies();
    }
    
    public Companies getCompanie(Integer idCompanie){
        return companieService.getCompanie(idCompanie);
    }
    
    public void addCompanie(Companies companie){
        companieService.addCompanie(companie);  
    }
    
    public void editCompanie(Companies companie){
        companieService.editCompanie(companie); 
    }
    
    public void deleteCompanie(Integer idCompanie){
        companieService.deleteCompanie(idCompanie); 
    }
}
