package ru.stqa.mfp.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;
import ru.stqa.mfp.addressbook.model.Contacts;
import ru.stqa.mfp.addressbook.model.GroupData;
import ru.stqa.mfp.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
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
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("pretest1"));
    }
  }

  @Test
  public void testContactRemoveFromGroup() {
    ContactData contact = selectContact();
    GroupData fromGroup = selectGroup(contact);
    Groups before = contact.getGroups();
    app.goTo().homePage();
    app.contact().groupPage(fromGroup.getId());
    app.contact().removeFromGroup(contact, fromGroup);
    ContactData contactAfter = selectContactById(contact);
    Groups after = contactAfter.getGroups();
    assertThat(after, equalTo(before.without(fromGroup)));
  }

  private ContactData selectContactById(ContactData contact) {
    Contacts contactsById = app.db().contacts();
    return contactsById.iterator().next().withId(contact.getId());
  }

  private ContactData selectContact() {
    Contacts allContacts = app.db().contacts();
    for (ContactData contact : allContacts) {
      if (contact.getGroups().size() > 0) {
        return contact;
      }
    }
    ContactData addContact = app.db().contacts().iterator().next();
    app.contact().addToGroup(addContact, app.db().groups().iterator().next());
    return addContact;
  }

  private GroupData selectGroup(ContactData removeContact) {
    ContactData contact = selectContactById(removeContact);
    Groups removedContact = contact.getGroups();
    return removedContact.iterator().next();
  }
}




