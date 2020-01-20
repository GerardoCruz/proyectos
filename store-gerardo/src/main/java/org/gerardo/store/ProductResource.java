
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
import org.gerardo.store.entity.Products;
import org.gerardo.store.control.ProductControl; 

/**
 *
 * @author Gerardo__Cruz
 */
@Path("/products")
public class ProductResource {
    
    @Inject 
    ProductControl productControl;

    @GET
    @Path("/getProducts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Products> getListProducts() {
        return productControl.getListProducts();
    }
    
    @GET
    @Path("/getProduct/{idProduct}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Products getCompanie(@PathParam("idProduct") Integer idProduct) {
        return productControl.getProduct(idProduct);
    }
    
    @POST
    @Path("/addProduct")
    @Produces(MediaType.APPLICATION_JSON)
    public void addProduct(Products product){
        productControl.addProduct(product);
    }
    
    @PUT
    @Path("/editProduct")
    @Produces(MediaType.APPLICATION_JSON)
    public void editProduct(Products product){
        productControl.editProduct(product);
    }
    
    @DELETE
    @Path("/deleteProduct/{idCompanie}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteProduct(@PathParam("idCompanie") Integer idProduct){
        productControl.deleteProduct(idProduct);
    }
}
