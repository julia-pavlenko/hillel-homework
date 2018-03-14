package hillelauto.jira;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import hillelauto.WebDriverTestBase;
import hillelauto.testng.*;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class JiraTests extends WebDriverTestBase {
    private LoginPage loginPage;
    private IssuePage issuePage;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface TestData
    {
        int testId() default 0;
    }

    @BeforeClass(alwaysRun = true)
    public void initPages() throws IOException, APIException {
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        issuePage = PageFactory.initElements(browser, IssuePage.class);
        System.out.println("Jira Pages Initialized");
        TestNG.createRun();
        System.out.println("Run is created");
    }


    @Test(description = "Invalid Login", priority = -1)
    @TestData(testId = 50)
    public void failureLogin() {
        loginPage.failureLogin();
    }

    @Test(description = "Valid Login", groups = { "Sanity" })
    @TestData(testId = 51)
    public void successfulLogin() {
        loginPage.successfulLogin();
    }

    @Test(description = "Create issue", dependsOnMethods = { "successfulLogin" }, groups = { "Sanity", "Issues" })
    @TestData(testId = 52)
    public void createIssue() throws InterruptedException {
        issuePage.createIssue();
    }

    @Test(description = "Open issue", dependsOnMethods = { "createIssue" }, groups = { "Sanity", "Issues" })
    @TestData(testId = 53)
    public void openIssue() {
        issuePage.openIssue();
    }

    @Test(description = "Uplaod Attachment", dependsOnMethods = { "openIssue" }, groups = { "Issues.Attachments" })
    @TestData(testId = 54)
    public void uploadAttachment() {
        issuePage.uploadAttachment();
    }

    @Test(description = "Download Attachment", dependsOnMethods = { "uploadAttachment" }, groups = {
            "Issues.Attachments", "disabled" })
    @TestData(testId = 62)
    public void downloadAttachment() {
        // loginPage.downloadAttachment();
    }

    @AfterMethod
    void printResult(ITestResult iTestResult) throws IOException, APIException {
        int testId = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestData.class).testId();
        TestNG.setStatus(testId, iTestResult.isSuccess());
    }

}
