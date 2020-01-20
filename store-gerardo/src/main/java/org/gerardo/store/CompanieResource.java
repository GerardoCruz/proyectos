package org.gerardo.store;

import org.gerardo.store.control.CompanieControl; 
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType; 
import org.gerardo.store.entity.Companies;

@Path("/companie")
public class CompanieResource {
    
    @Inject
    CompanieControl companieControl;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Companies> getListCompanies() { 
        return companieControl.getListCompanies(); 
    }
    
    @GET
    @Path("{idCompanie}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Companies getCompanie(@PathParam("idCompanie") Integer idCompanie) {
        return companieControl.getCompanie(idCompanie);
    }
    
    @POST
    @Path("/addCompanie")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCompanie(Companies companie){ 
        companieControl.addCompanie(companie);
    }
    
    @PUT
    @Path("/editCompanie")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void editCompanie(Companies companie){ 
        companieControl.editCompanie(companie);
    }
    
    @DELETE
    @Path("/deleteCompanie/{idCompanie}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCompanie(@PathParam("idCompanie") Integer idCompanie){
        companieControl.deleteCompanie(idCompanie);
    }
}