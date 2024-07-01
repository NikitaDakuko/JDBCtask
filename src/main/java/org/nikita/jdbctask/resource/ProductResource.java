package org.nikita.jdbctask.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.entity.Product;

@RequestScoped
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductDAO productDAO;

    @GET
    public Response getAll(){
        return Response.ok(productDAO.getAll()).build();
    }

    @GET
    public  Response getProduct(@PathParam("id") Long id){
        return Response.ok(productDAO.findById(id)).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Product product){
        Product updateProduct = productDAO.findById(id);

        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setAvailability(product.getAvailability());

        return Response.ok().build();
    }

    @POST
    public Response create(Product product){
        productDAO.create(product);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id){
        productDAO.delete(productDAO.findById(id));
        return Response.ok().build();
    }
}
