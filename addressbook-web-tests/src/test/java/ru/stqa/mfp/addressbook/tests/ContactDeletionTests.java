package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().returnToHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Name test 1", "Name test 2", "Lastnametest3", "nicktest", "QA", "VRTtest", "Minsk", "+375445555555", "email@domain.com", "emailtest@domain.com", "test.com", "10", "November", "2000", "test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().acceptAlertDeleteContact();
    app.getNavigationHelper().returnToHomePage();
  }
}


