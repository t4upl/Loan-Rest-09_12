package com.journaldev.other;

import com.journaldev.manager.ProductManager;
import com.journaldev.manager.ProductSettingManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("/hello")
public class RestService {

    @Autowired
    ProductManager productManager;


    @Autowired
    ProductSettingManager productSettingManager;

    @GET
    @Path("/test1")
    public Response test1() {
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Methods", "GET")
                .entity("test1").build();
    }

    @GET
    @Path("/test2")
    public Response test1(@QueryParam("from") int from) {
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Methods", "GET")
                .entity("test2 " + from).build();
    }

    @GET
    @Path("/apply_for_loan")
    public Response applyForLoan(@QueryParam("amount") int amount,
                                 @QueryParam("term") int term,
                                 @QueryParam("customerId") int customerId,
                                 @QueryParam("productTypeId") int productTypeId) {
        ClientDataWrapper clientDataWrapper = ClientDataWrapper.builder()
                .amount(amount)
                .applicationDate(LocalDateTime.now())
                .term(term)
                .customerId(customerId)
                .productTypeId(productTypeId).build();
        this.productManager.applyForLoan(clientDataWrapper);
        return Response.status(200)
                .entity("apply_for_loan processed")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    @GET
    @Path("/extend_loan")
    public Response extendLoad (@QueryParam("productId") int productId) {
        productSettingManager.getExtensionTerm(productId);
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Methods", "GET")
                .entity("apply_for_loan processed").build();
    }
}
