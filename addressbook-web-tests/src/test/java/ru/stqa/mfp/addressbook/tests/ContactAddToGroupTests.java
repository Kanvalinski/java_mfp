package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;
import ru.stqa.mfp.addressbook.model.Contacts;
import ru.stqa.mfp.addressbook.model.GroupData;
import ru.stqa.mfp.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("pretest1"));
    }

    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Name test 1")
              .withMiddlename("Name test 2").withLastname("Lastnametest3")
              .withNickname("nicktest").withTitle("QA").withCompany("VRTtest")
              .withAddress("Minsk").withMobilePhone("+375445555555")
              .withEmail("email@domain.com").withEmail2("emailtest@domain.com")
              .withHomepage("test.com").withBday("10")
              .withBmonth("November").withByear("2000"), true);
    }
  }

  @Test
  public void testContactAddToGroup() {
    ContactData contact = selectContact();
    GroupData toGroup = selectGroup(contact);
    Groups before = contact.getGroups();
    app.goTo().homePage();
    app.contact().addToGroup(contact, toGroup);
    ContactData contactAfter = selectContactById(contact);
    Groups after = contactAfter.getGroups();
    assertThat(after, equalTo(before.withAdded(toGroup)));
  }

  private ContactData selectContactById(ContactData contact) {
    Contacts contactById = app.db().contacts();
    return contactById.iterator().next().withId(contact.getId());
  }

  private ContactData selectContact() {
    Contacts allContacts = app.db().contacts();
    Groups allGroups = app.db().groups();
    for (ContactData contact : allContacts) {
      if (contact.getGroups().size() < allGroups.size()) {
        return contact;
      }
    }
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("newTestGroup").withHeader("newHeader"));
    return allContacts.iterator().next();
  }

  private GroupData selectGroup(ContactData contact) {
    Groups allGroups = app.db().groups();
    Groups contactInGroups = contact.getGroups();
    Collection<GroupData> contactGroups = new HashSet<GroupData>(contactInGroups);
    Collection<GroupData> available = new HashSet<GroupData>(allGroups);
    available.removeAll(contactGroups);
    GroupData availableGroup = available.iterator().next();
    return availableGroup;
  }
}

