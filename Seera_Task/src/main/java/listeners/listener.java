package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class listener {
	
	

    public class Listener implements ITestListener {


        public ExtentReports extent;
        public ExtentTest Logger;



        public void onStart(ITestContext context) {
            System.out.println("Testing Is Started");

            extent = new ExtentReports();
            ExtentHtmlReporter HTML_Report = new ExtentHtmlReporter("ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(HTML_Report);
            extent.setSystemInfo("Project", "Seera_Task");
            extent.setSystemInfo("Tester", "Ahmad Malloh");
        }

        public void onFinish(ITestContext context) {
            System.out.println("Testing Is Done");
            extent.flush();
        }

        public void onTestStart(ITestResult result) {
            System.out.println("New Test Started" + result.getName());
            Logger = extent.createTest(result.getName());
        }

        public void onTestSuccess(ITestResult result) {
            System.out.println(result.getName() + "Test Successed");
            Logger.log(Status.PASS, "Passed");
        }

        public void onTestFailure(ITestResult result) {
            System.out.println(result.getName() + "Test Failure");
            System.out.println(result.getThrowable());
            Logger.log(Status.FAIL, "Failure");
        }

        public void onTestSkipped(ITestResult result) {
            System.out.println(result.getName() + "Test Skipped");
            Logger.log(Status.SKIP, "Skipped");
        }

    }

}
