package ru.stqa.mfp.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.mfp.mantis.model.MailMessage;
import ru.stqa.mfp.mantis.model.UserData;
import ru.stqa.mfp.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetUserPasswordTests extends TestBase{

  @BeforeMethod //Only for MailServer
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testResetUserPassword() throws IOException, MessagingException {
    Users users = app.db().enabledUsers();
    UserData user = users.iterator().next();
    String username = user.getName();
    String email = user.getEmail();
    Integer id = user.getId();
    String newPassword = "newpassword";

    /*//Create User in James server to receive email
    Boolean userExist = app.james().doesUserExist(username); //Only for James
    if (!userExist) {
      app.james().createUser(username, password);
    }
    */

    //Login to Mantis bugtracker as Admin
    app.uiSession().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.goTo().manageUsers();
    app.user().selectUser(username, id);

    long now = System.currentTimeMillis();
    app.user().initPasswordReset(now);

    List<MailMessage> mailMessages = app.mail().waitForMail(2, 60000); //Only for MailServer
    //List<MailMessage> mailMessages = app.james().waitForMail(username, password, 600000); //Only for James
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, newPassword);
    assertTrue(app.newSession().login(username, newPassword));
  }


  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true) //Only for MailServer
  public void stopMailServer() {
    app.mail().stop();
  }
}
