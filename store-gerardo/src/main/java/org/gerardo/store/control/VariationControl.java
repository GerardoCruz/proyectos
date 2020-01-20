
package org.gerardo.store.control;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject; 
import org.gerardo.store.entity.Variations;
import org.gerardo.store.services.VariationService;

/**
 *
 * @author Gerardo__Cruz
 */
@ApplicationScoped
public class VariationControl {
    
    @Inject
    VariationService variationService;
    
    public List<Variations> getListVariations(Integer idProduct){
        return variationService.getListVariations(idProduct);
    } 
    
    public boolean validateSku(String sku){
        return variationService.validateSku(sku);
    }
    
    public void addVariation(Variations variation){
        variationService.addVariation(variation);
    } 
    
    public void deleteVariation(Integer idVariation){
        variationService.deleteVariation(idVariation);
    }
}
