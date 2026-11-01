package apitest;

import org.testng.Assert;
import org.testng.annotations.Test;

import apiendpoints.UserEndpoints;
import apipayload.User;
import apiutilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

@Test (priority=1, dataProvider="Data", dataProviderClass = DataProviders.class)
    public void testPostuser(String userID, String userName, String fname, String lname, String userEmail, String pwd, String ph)
{

User userPayload=new User();

    userPayload.setId(Integer.parseInt(userID));
    userPayload.setUsername(userName);
    userPayload.setFirstName(fname);
    userPayload.setLastName(lname);
    userPayload.setEmail(userEmail);
    userPayload.setPassword(pwd);
    userPayload.setPhone(ph);  

    Response response = UserEndpoints.createUser(userPayload);
    //response.then().log().all();
    //System.out.println("The status code is: " + response.getStatusCode());
    Assert.assertEquals(response.getStatusCode(), 200);

}

@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)

public void getUserByName(String UserName)
{
                Response response = UserEndpoints.readUser(UserName);
                //response.then().log().all();    
                //response.statusCode();
                Assert.assertEquals(response.getStatusCode(), 200); 

}

@Test (priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
public void testDeleteUserByName(String UserName)

{
        Response response= UserEndpoints.deleteUser(UserName) ;
        Assert.assertEquals(response.getStatusCode(), 200);

}

}
