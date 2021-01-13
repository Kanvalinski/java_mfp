package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Name test 1").withMiddlename("Name test 2").withLastname("Lastnametest3").
              withNickname("nicktest").withTitle("QA").withCompany("VRTtest").withAddress("Minsk").withMobilePhone("+375445555555").
              withEmail("email@domain.com").withEmail2("emailtest@domain.com").withHomepage("test.com").withBday("10").
              withBmonth("November").withByear("2000").withGroup("test1"), true);
    }
  }

  @Test
  public void testContactAddress() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));
    app.logout();
  }
  private String mergeAddress(ContactData contact) {
    return Arrays.asList(contact.getAddress())
            .stream().filter((s) -> ! s.equals(""))
            .collect(Collectors.joining("\n"));
  }
}
