package apiutilities;

//Extent report 5.x

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener
{
public ExtentSparkReporter sparkReporter; // Responsible for UI/look and feel of Report
public ExtentReports extent; // Project some common data like env name, username, os name etc.
public ExtentTest test; // Creating entries in report like PASS, FAIL, and Skip etc.

String repName;

public void onStart (ITestContext testContext)
    {
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
    repName="Test-Report-"+timeStamp+".html";

Path reportDirectory = Path.of(System.getProperty("user.dir"), "reports");
try {
    Files.createDirectories(reportDirectory);
} catch (IOException e) {
    throw new IllegalStateException("Could not create report directory: " + reportDirectory.toAbsolutePath(), e);
}
sparkReporter=new ExtentSparkReporter(reportDirectory.resolve(repName).toString());//specify location of the report

    sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // Title of report 
    sparkReporter.config().setReportName("Pet Store Users API"); // name of the report 
    sparkReporter.config().setTheme(Theme.DARK);

    extent=new ExtentReports();
    extent.attachReporter (sparkReporter); 
    extent.setSystemInfo("Application", "Pest Store Users API");
    extent.setSystemInfo("Operating System", System.getProperty("os.name")); 
    extent.setSystemInfo("User Name", System.getProperty("user.name"));
    extent.setSystemInfo("Environment","QA");
    extent.setSystemInfo("user","akshay");
    }

    public void onTestSuccess (ITestResult result)
    {
    test=extent.createTest (result.getName());
    test.assignCategory(result.getMethod() .getGroups());
    test.createNode(result.getName());
    test.log(Status.PASS, "Teft Passed");
    }

    public void onTestFailure(ITestResult result)
    {
    test=extent.createTest(result.getName());
    test.createNode(result.getName()) ;
    test.assignCategory(result.getMethod().getGroups());
    test.log(Status.FAIL, "Test Failed");
    test.log(Status.FAIL, result.getThrowable().getMessage()) ;
    }

    public void onTestSkipped(ITestResult result)
    {
    test=extent.createTest(result.getName());
    test.createNode(result. getName());
    test.assignCategory(result.getMethod().getGroups()) ;
    test.log(Status.SKIP, "Test Skipped");
    test.log(Status.SKIP, result.getThrowable().getMessage()) ;
    }

    public void onFinish(ITestContext testContext)
    {
        extent. flush();
    }

}
