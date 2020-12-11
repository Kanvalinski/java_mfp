package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Name test 1 new", "Name test 2 new", "Lastnametest3 new", "nicktest new", "QA new", "VRTtest new", "Minsk new", "+375445555555 new", "email@domain.com new", "emailtest@domain.com new", "test.com new", "10", "November", "2000"));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }
}
