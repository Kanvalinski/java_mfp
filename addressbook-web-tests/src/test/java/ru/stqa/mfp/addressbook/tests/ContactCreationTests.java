package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Name test 1", "Name test 2", "Lastnametest3", "nicktest", "QA", "VRTtest", "Minsk", "+375445555555", "email@domain.com", "emailtest@domain.com", "test.com", "10", "November", "2000"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToHomePage();
    app.logout();
  }


}
