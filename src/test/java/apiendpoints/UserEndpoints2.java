package apiendpoints;
//UserEndpoints.java is a class that will contain all the endpoints related to user module of petstore application.
// Created to perform CRUD (Create, Read, Update, Delete) operations on user module of petstore application using RestAssured framework.


import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import apipayload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints2 {

        // method created for getting URL's from properties file 
          static ResourceBundle getURl()
            {
                ResourceBundle routes = ResourceBundle.getBundle("routes"); //Load properties file from resources // routes is name of properties file (basename: "routes")
                return routes;
            } 

            public static Response createUser(User payload)
            {
                String post_url=getURl().getString("post_url"); // Getting Routes data and passing it as String by setting key from routes properties file.  
                Response response = given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(payload)
                .when()
                    .post(post_url); 

                    return response;
            }


            public static Response readUser(String userName)
            {
                String get_url=getURl().getString("get_url");
                Response response = given()
                    .pathParam("username", userName)
                .when()
                    .get(get_url); 

                    return response;
            }


            public static Response updateUser(String userName, User payload)
            {
                String update_url=getURl().getString("update_url");
                Response response = given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .pathParam("username", userName)    
                    .body(payload)
                .when()
                    .put(update_url); 

                    return response;
            }

            public static Response deleteUser (String userName)
            { 
                String delete_url=getURl().getString("delete_url");
                Response response=given()
                    .pathParam( "username", userName)
                .when ()
                    .delete(delete_url);
                return response;
            }

}
