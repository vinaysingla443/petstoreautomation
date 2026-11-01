package apiutilities;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;

public class DataProviders {

    private String getWorkbookPath()
    {
        return Paths.get(System.getProperty("user.dir"), "testData", "Userdata.xlsx").toString();
    }

    @DataProvider(name="Data")
    public String[][] getAllData() throws IOException
    {
        String path=getWorkbookPath();
        XLUtility x1=new XLUtility(path);

        int rownum=x1.getRowCount("Sheet1");
        int colcount=x1.getCellcount("Sheet1",1);

        String apidata[][]=new String[rownum][ colcount];
        for (int i=1; i<=rownum;i++)
        {
         for (int j=0;j<colcount;j++)
        {
            apidata[i-1][j]= x1.getcelldata("Sheet1",i, j);
        }
        }
        return apidata;
}

    @DataProvider (name="UserNames")        
    public String[] getUserNames () throws IOException
    {
        String path=getWorkbookPath();
        XLUtility x1=new XLUtility(path);

        int rownum=x1.getRowCount("Sheet1");

        String apidata[]=new String[rownum];

        for (int i=1;i<=rownum;i++)
        {
            apidata[i-1]= x1.getcelldata("Sheet1",i, 1);
        }

        return apidata;

}

}
