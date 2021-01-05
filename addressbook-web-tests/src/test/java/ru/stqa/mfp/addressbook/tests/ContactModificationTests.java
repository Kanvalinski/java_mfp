package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;
import ru.stqa.mfp.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Name test 1").withMiddlename("Name test 2").withLastname("Lastnametest3").
              withNickname("nicktest").withTitle("QA").withCompany("VRTtest").withAddress("Minsk").withMobile("+375445555555").
              withEmail("email@domain.com").withEmail2("emailtest@domain.com").withHomepage("test.com").withBday("10").
              withBmonth("November").withByear("2000").withGroup("test1"), true);
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Name test 1 new").withMiddlename("Name test 2 new").withLastname("Lastnametest3 new").
            withNickname("nicktest new").withTitle("QA new").withCompany("VRTtest new").withAddress("Minsk new").withMobile("+375445555555 new").
            withEmail("email@domain.com new").withEmail2("emailtest@domain.com new").withHomepage("test.com new").
            withBday("10").withBmonth("November").withByear("2000");
    app.contact().modify(contact);
    Contacts after = app.contact().all();
    assertEquals(after.size(), before.size());
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
