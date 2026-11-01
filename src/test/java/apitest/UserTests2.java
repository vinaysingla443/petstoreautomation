package apitest;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import apiendpoints.UserEndpoints2;
import apipayload.User;
import io.restassured.response.Response;


public class UserTests2 {

        Faker faker; 
        User userPayload;

        public Logger logger; // FOR LOGS

        @BeforeClass 
        public void setup() 
        {
            faker = new Faker();
            userPayload = new User();

            userPayload.setId(faker.idNumber().hashCode());
            userPayload.setUsername(faker.name().username());
            userPayload.setFirstName(faker.name().firstName());
            userPayload.setLastName(faker.name().lastName());
            userPayload.setEmail(faker.internet().safeEmailAddress());
            userPayload.setPassword(faker.internet().password(5,10));
            userPayload.setPhone(faker.phoneNumber().cellPhone());

            //logs
            
            logger= LogManager.getLogger(this.getClass());

             
        }

            @Test (priority=1)

            public void testPostUser()
            { 

                logger.info ("**** Creating User *****");
                Response response = UserEndpoints2.createUser(userPayload);
                response.then().log().all();

                //System.out.println("The status code is: " + response.getStatusCode());
                Assert.assertEquals(response.getStatusCode(), 200);
                logger.info ("**** User is Created *****");
               // System.out.println(userPayload.getId());
               //  System.out.println(userPayload.getUsername());
               // System.out.println(userPayload.getFirstName());
               // System.out.println(userPayload.getLastName());
               // System.out.println(userPayload.getEmail());
               // System.out.println(userPayload.getPassword());
               // System.out.println(userPayload.getPhone());
            }


            @Test (priority=2)
            public void testGetUserByName()
            {

                logger.info ("**** Getting User info *****");
                Response response = UserEndpoints2.readUser(this.userPayload.getUsername());
                response.then().log().all();    

                //response.statusCode();
                Assert.assertEquals(response.getStatusCode(), 200); 
                logger.info ("**** User info is displayed *****");
                //Assert.assertEquals(response.getBody().path("id"), userPayload.getId());
                //Assert.assertEquals(response.getBody().path("username"), userPayload.getUsername());
                //Assert.assertEquals(response.getBody().path("firstName"), userPayload.getFirstName());
                //Assert.assertEquals(response.getBody().path("lastName"), userPayload.getLastName());
                //Assert.assertEquals(response.getBody().path("email"), userPayload.getEmail());
                //Assert.assertEquals(response.getBody().path("password"), userPayload.getPassword());
                //Assert.assertEquals(response.getBody().path("phone"), userPayload.getPhone());

                Assert.assertEquals(response.jsonPath().getInt("id"), userPayload.getId());
                Assert.assertEquals(response.jsonPath().getString("username"), userPayload.getUsername());                
                Assert.assertEquals(response.jsonPath().getString("firstName"), userPayload.getFirstName());
                Assert.assertEquals(response.jsonPath().getString("lastName"), userPayload.getLastName());
                Assert.assertEquals(response.jsonPath().getString("email"), userPayload.getEmail());
                Assert.assertEquals(response.jsonPath().getString("password"), userPayload.getPassword());
                Assert.assertEquals(response.jsonPath().getString("phone"), userPayload.getPhone());


            }

                @Test (priority=3)
                public void testUpdateUserByName()
                {

                    logger.info ("**** Updating User info *****");
                    //update data using payload
                    userPayload.setFirstName(faker.name().firstName());
                    userPayload.setLastName(faker.name().lastName());
                    userPayload.setEmail(faker.internet().safeEmailAddress());


                    Response response = UserEndpoints2.updateUser(this.userPayload.getUsername(),userPayload);
                    response.then().log().all();
                    //response.then().log().body().statusCode(200);
                //System.out.println("The status code is: " + response.getStatusCode());
                //Assert.assertEquals(response.getStatusCode(), 200);
                    logger.info ("**** User info is updated *****");
                //Checking data after update
                Response responseAfterupdate=UserEndpoints2.readUser(this.userPayload.getUsername());
                responseAfterupdate.then().log().body();
                
                Assert.assertEquals(responseAfterupdate.getStatusCode(),200);
                Assert.assertEquals(responseAfterupdate.jsonPath().getString("firstName"), this.userPayload.getFirstName());
                Assert.assertEquals(responseAfterupdate.jsonPath().getString("lastName"), this.userPayload.getLastName());
                Assert.assertEquals(responseAfterupdate.jsonPath().getString("email"), this.userPayload.getEmail());
                }


                @Test (priority = 4)
                public void testDeleteUserByName()

                {
                    logger.info ("**** Deleting User *****");
                    Response response= UserEndpoints2.deleteUser(this.userPayload.getUsername()) ;
                    Assert.assertEquals(response.getStatusCode(), 200);
                    logger.info ("**** User is deleted *****");
                }

}
