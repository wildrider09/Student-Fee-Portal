package com.example.studentfeepayment.controller;

import com.example.studentfeepayment.bean.Bills;
import com.example.studentfeepayment.bean.StudentPayment;
import com.example.studentfeepayment.bean.Students;
import com.example.studentfeepayment.service.BillsOperationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

@Path("bills")
public class ShowBillsController {

    /**
     * @param request -- Student Object
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @POST
    @Path("/show")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response showBills(Students request) throws URISyntaxException, JsonProcessingException {
        List<Bills> response = new BillsOperationServiceImpl().getBills(request);
        System.out.println("Show Bills response: ");
        response.forEach(System.out::println);
        return Response.ok().entity(response).build();
    }

    /**
     * @param request -- {"queryString":"userName=MT2020001&name=Vinayak&Registration Fees=500&Library Fees=1000"}
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @POST
    @Path("/pay")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response payBills(String request) throws URISyntaxException, JsonProcessingException {
        System.out.println("Request for paying bills: " + request);
        boolean response = new BillsOperationServiceImpl().payBills(request);
        return Response.ok().build();
    }

    /**
     * @param request -- Student Object
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @POST
    @Path("/paid")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response paidBills(Students request) throws URISyntaxException, JsonProcessingException {
        List<StudentPayment> response = new BillsOperationServiceImpl().paidBills(request);
        System.out.println("Paid Bills response: ");
        response.forEach(System.out::println);
        return Response.ok().entity(response).build();
    }

}
