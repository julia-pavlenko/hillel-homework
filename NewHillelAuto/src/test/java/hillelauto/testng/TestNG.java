package hillelauto.testng;

import hillelauto.WebDriverTools;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterTest;

import java.io.IOException;


public class TestNG {
    static APIClient client;
    static Long runId;

    private static void authorise() {
        client = new APIClient(TestNGVars.baseTestRailURL);
        client.setUser(TestNGVars.login);
        client.setPassword(TestNGVars.password);
    }

    public static void createRun(String projectId, String runName) throws IOException, APIException {
        authorise();
        JSONObject runJSON = new JSONObject();
        runJSON.put("name", runName + WebDriverTools.getCurrentTime());
        runJSON.put("include_all", true);
        JSONObject run = (JSONObject) client.sendPost("/add_run/" + projectId, runJSON);
        TestNG.runId = (Long) run.get("id");
    }

    public static JSONObject setStatus(int caseId, boolean isSuccess) throws IOException, APIException {
        JSONObject issueStatusJSON = new JSONObject();
        issueStatusJSON.put("status_id", isSuccess ? new Integer(1) : new Integer(5));
        return (JSONObject) client.sendPost("add_result_for_case/" + runId + "/" + caseId, issueStatusJSON);
    }

    public static JSONObject closeRun() throws IOException, APIException {
        return (JSONObject) client.sendPost("close_run/" + runId, new Object());
    }

}
