package hillelauto;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hillelauto.testng.APIException;
import hillelauto.testng.TestNG;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

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
        TestNG.closeRun();
    }


}