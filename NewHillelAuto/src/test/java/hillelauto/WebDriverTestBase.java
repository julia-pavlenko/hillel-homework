package hillelauto;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import hillelauto.jira.JiraTests;
import hillelauto.testng.APIException;
import hillelauto.testng.TestNG;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class WebDriverTestBase {
    protected static WebDriver browser;

    static {
        System.setProperty("webdriver.chrome.driver", "/Users/julia/src/web_drivers/chromedriver");
    }

    @BeforeTest(alwaysRun = true)
    public static void setUp() {
        browser = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized", "--incognito"));
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebDriverTools.setDriver(browser);
    }

    @AfterTest(alwaysRun = true)
    public static void finish() throws IOException, APIException {
        browser.close();
    }

    @Parameters({ "testRailProjectId", "testRailRunName" })
    @BeforeTest(groups = { "TestRailReport" })
    public void prepareTestRailRun(String testRailProjectId, String testRailRunName) throws IOException, APIException {
        TestNG.createRun(testRailProjectId,testRailRunName);
        System.out.println("Run is created");
    }

    @AfterTest(groups = {"TestRailReport"})
    public static void closeTestRailRun() throws IOException, APIException {
        TestNG.closeRun();
    }

    @AfterMethod(groups = { "TestRailReport" })
    protected void printResult(ITestResult iTestResult) throws IOException, APIException {
        int testId = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraTests.TestData.class).testId();
        TestNG.setStatus(testId, iTestResult.isSuccess());
    }

}