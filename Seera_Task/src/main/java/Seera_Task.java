import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Seera_Task {
	
	
	
	 private String End_point = "https://www.almosafer.com";




	    @Test()
	    public void Get_API()
	    {
	        // I choose this API "currency/list" to validate it.


	        // define some values to validate it from the Response

	        String Expected_SAR_Name = "Saudi Riyal";
	        String Expected_UAE_Name = "United Arab Emirates Dirham";
	        String Expected_QAR_Name = "Qatari Rial";
	        int Expected_currency_Count = 11;

	        baseURI = End_point; 


	        String Response = given().header("x-currency", "SAR").header("Referer", "https://www.almosafer.com/en/hotels-home")
	                .when().get("/api/system/currency/list")
	                .then().assertThat().statusCode(200).extract().response().asString();


	        // Parsing the JSON
	        JsonPath JS = new JsonPath(Response);
	        int Actual_currency_Count = JS.getInt("equivalent.size()");
	        String Actual_SAR_Name = JS.get("equivalent.name[0]");
	        String Actual_UAE_Name = JS.get("equivalent.name[1]");
	        String Actual_QAR_Name = JS.get("equivalent.name[2]");


	        // Comparing between the expected and actual data
	        Assert.assertEquals(Actual_SAR_Name,Expected_SAR_Name);
	        Assert.assertEquals(Actual_UAE_Name,Expected_UAE_Name);
	        Assert.assertEquals(Actual_QAR_Name,Expected_QAR_Name);
	        Assert.assertEquals(Actual_currency_Count,Expected_currency_Count);



	    }


	    @Test(enabled = false)
	    public void Post_API() throws IOException {

	        // the Configurations to make the code read the data from the Excel sheet

	        File file = new File("the path of the sheet that have the data");
	        FileInputStream FIS = new FileInputStream(file);

	        XSSFWorkbook workbook = new XSSFWorkbook(FIS);
	        Sheet sheet = workbook.getSheet("sheet name");
	        String template = sheet.getRow(0).getCell(1).toString(); // read the value of template from Excel sheet to use it in the Body request
	        String country = sheet.getRow(1).getCell(2).toString(); // read the value of country from Excel sheet to use it in the Body request
	        String media = sheet.getRow(2).getCell(3).toString(); // read the value of media from Excel sheet to use it in the Body request
	        String locale = sheet.getRow(3).getCell(4).toString(); // read the value of locale from Excel sheet to use it in the Body request
	        String Token = sheet.getRow(4).getCell(5).toString(); // read the value of Token from Excel sheet to use it in the Header request


	        // I choose this API "api/cms/page" to validate it.

	        baseURI = End_point;

	        given().header("x-tz","Asia/Amman").header("token",Token)
	                .body("{\"template\":\""+template+"\",\"country\":\""+country+"\",\"media\":\""+media+"\",\"locale\":\""+locale+"\"}")
	                .when().post("/api/cms/page")
	                .then().assertThat().statusCode(200);

	    }



}
