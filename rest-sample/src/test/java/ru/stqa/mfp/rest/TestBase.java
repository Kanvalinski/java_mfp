package ru.stqa.mfp.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.Set;

public class TestBase {

  public boolean isIssueOpen(int issueId) {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues/" + issueId + ".json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issuesAsJson = parsed.getAsJsonObject().get("issues");
    Set<Issue> issues = new Gson().fromJson(issuesAsJson, new TypeToken<Set<Issue>>() {
    }.getType());
    Issue issue = issues.iterator().next();
    if (issue.getState().equals("Open")) {
      return true;
    } return false;
  }

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId) == true) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}
