package hillelauto.jira;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface JiraVars {
    String baseURL = "http://jira.hillel.it:8080/";
    String username = "autorob";
    String password = "forautotests";

    String newIssueSummary = "AutoTest " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    String attachmentFileLocation = "/Users/Julia/Downloads/";
    String attachmentFileName = "TEST1.pdf";
}