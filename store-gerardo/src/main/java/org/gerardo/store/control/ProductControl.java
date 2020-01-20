
package org.gerardo.store.control;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject; 
import org.gerardo.store.services.ProductService;
import org.gerardo.store.entity.Products;
import org.gerardo.store.entity.Variations;
import org.gerardo.store.services.VariationService;

/**
 *
 * @author Gerardo__Cruz
 */
@ApplicationScoped
public class ProductControl {
    
    @Inject
    ProductService productService;
    
    @Inject
    VariationService variationService;
    
    public List<Products> getListProducts(){
        return productService.getListProducts();
    }
    
     public Products getProduct(Integer idProduct){
        return productService.getProduct(idProduct); 
    }
    
    public void addProduct(Products product){
        productService.addProduct(product);
    }
    
    public void editProduct(Products product){
        productService.editProduct(product);  
    }
    
    public void deleteProduct(Integer idProduct){
        productService.deleteProduct(idProduct);
    }
}
