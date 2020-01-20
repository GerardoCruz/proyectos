
package org.gerardo.store;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.gerardo.store.control.VariationControl; 
import org.gerardo.store.entity.Variations;

/**
 *
 * @author Gerardo__Cruz
 */
@Path("/variations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VariationResource {
    
    @Inject 
    VariationControl variationControl;

    @GET
    @Path("{idProduct}")
    public List<Variations> getListVariations(@PathParam("idProduct") Integer idProduct) {
        return variationControl.getListVariations(idProduct);
    }
    
    @GET
    @Path("/validateSku/{sku}") 
    public boolean validateSku(@PathParam("sku") String sku){
        return variationControl.validateSku(sku);
    }
        
    @POST
    @Path("/addVariation") 
    public void addVariation(Variations variation){
        variationControl.addVariation(variation);
    }
    
    @DELETE
    @Path("/deleteVariation/{idVariation}") 
    public void deleteVariation(@PathParam("idVariation") Integer idVariation){
        variationControl.deleteVariation(idVariation);
    }
}
