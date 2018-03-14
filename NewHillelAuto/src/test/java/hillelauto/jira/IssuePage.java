package hillelauto.jira;

import hillelauto.WebDriverTools;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class IssuePage {
    private final By inputProject = By.cssSelector("input#project-field");
    private final By inputSummary = By.cssSelector("input#summary");
    private final WebDriver browser;
    private String newIssuePath;
    private String attachmentLink;
    @FindBy(css = "a#create_link")
    private WebElement buttonCreateIssue;
    @FindBy(css = "a.issue-created-key")
    private List<WebElement> linkNewIssues;
    @FindBy(css = "input.issue-drop-zone__file")
    private WebElement inputUploadAttachment;
    @FindBy(css = "a.attachment-title")
    private WebElement linkAttachmentName;

    public IssuePage(WebDriver browser) {
        this.browser = browser;
    }

    public void createIssue() {
        buttonCreateIssue.click();

        WebDriverTools.clearAndFill(inputProject, "General QA Robert (GQR)\n");

        new FluentWait<>(browser).withTimeout(5, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(InvalidElementStateException.class).until(browser -> WebDriverTools.clearAndFill(inputSummary, JiraVars.newIssueSummary)).submit();

        // ((JavascriptExecutor) browser).executeScript("window.scrollBy(0,250)");

        Assert.assertTrue(linkNewIssues.size() != 0);

        newIssuePath = linkNewIssues.get(0).getAttribute("href");
    }

    public void openIssue() {
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(JiraVars.newIssueSummary));
    }

    public void uploadAttachment() {
        inputUploadAttachment.sendKeys(JiraVars.attachmentFileLocation + JiraVars.attachmentFileName);

        WebElement linkAttachment = new FluentWait<>(browser).withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
                .until(browser -> linkAttachmentName);

        Assert.assertEquals(JiraVars.attachmentFileName, linkAttachment.getText());

        attachmentLink = linkAttachment.getText();

        // return JiraVars.attachmentFileName.equals(linkAttachment.getText());
    }

    public void downloadAttachment() {
        browser.get(attachmentLink);

        // https://stackoverflow.com/questions/304268/getting-a-files-md5-checksum-in-java
    }
}