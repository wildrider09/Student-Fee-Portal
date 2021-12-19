package com.example.studentfeepayment.controller;

import com.example.studentfeepayment.bean.Students;
import com.example.studentfeepayment.utils.GetInstances;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

@Path("register")
public class StudentRegisterController {

    @POST
    @Path("/student")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerStudent(Students request) throws URISyntaxException, JsonProcessingException {
        Students response = GetInstances.getInstanceOfStudentOperationService().registerStudent(request);
        if (response == null) return Response.status(Response.Status.NOT_FOUND).build();

        System.out.println("Response from service and DAO for registering a new student: " + response.toString());

        ObjectMapper mapper = new ObjectMapper();
        String studentAsJsonMessage = mapper.writeValueAsString(response);
        System.out.println("Json Message: " + studentAsJsonMessage);
        return Response.ok().entity(studentAsJsonMessage).build();
    }

}
