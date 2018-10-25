package com;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Main {

    public static void main(String[] args) {
        org.glassfish.jersey.client.ClientConfig config = new org.glassfish.jersey.client.ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        //Now printing the server code of different media type
        System.out.println(target.path("/").request().accept(MediaType.TEXT_PLAIN).get(String.class));
    }

    private static URI getBaseURI() {
        //here server is running on 4444 port number and project name is restfuljersey
        return UriBuilder.fromUri("http://localhost:4444/restfuljersey").build();
    }
}
