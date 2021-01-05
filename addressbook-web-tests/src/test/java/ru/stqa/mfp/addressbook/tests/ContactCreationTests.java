package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;
import ru.stqa.mfp.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("Name test 2").withMiddlename("Name test 2").withLastname("Lastnametest3").
            withNickname("nicktest").withTitle("QA").withCompany("VRTtest").withAddress("Minsk").withMobile("+375445555555").
            withEmail("email@domain.com").withEmail2("emailtest@domain.com").withHomepage("test.com").withBday("10").
            withBmonth("November").withByear("2000").withGroup("test1");
    app.contact().initContactCreation();
    app.contact().fillContactForm(contact, true);
    app.contact().submitContactCreation();
    app.goTo().homePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() +1));

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    app.logout();
  }
}
