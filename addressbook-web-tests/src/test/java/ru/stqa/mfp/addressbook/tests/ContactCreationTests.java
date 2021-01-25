package ru.stqa.mfp.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.ContactData;
import ru.stqa.mfp.addressbook.model.Contacts;
import ru.stqa.mfp.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader
            (new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType()); //List<ContactData>.class
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(enabled = false)
  public void testBadContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("Name test 3'").withMiddlename("Name test 2").withLastname("Lastnametest3").
            withNickname("nicktest").withTitle("QA").withCompany("VRTtest").withAddress("Minsk").withMobilePhone("+375445555555").
            withEmail("email@domain.com").withEmail2("emailtest@domain.com").withHomepage("test.com").withBday("10").
            withBmonth("November").withByear("2000"); //.withGroup("test1") -- deleted
    app.contact().initContactCreation();
    app.contact().fillContactForm(contact, true);
    app.contact().submitContactCreation();
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();

    assertThat(after, equalTo(before));
    verifyContactListIUi();
    app.logout();
  }

  @Test(enabled = false)
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/stphoto.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) throws Exception {
    //Contacts before = app.contact().all();
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/stphoto.jpg");
    ContactData newContact = new ContactData().withFirstname("test_name").withLastname("test_surname")
            .withPhoto(photo).inGroup(groups.iterator().next());
    Contacts before = app.db().contacts();
    //app.contact().create(contact, true); //Create Contacts using data of ContactGenerator
    /*ContactData contact = new ContactData().withFirstname("Name test 2").withMiddlename("Name test 2").withLastname("Lastnametest3").
            withNickname("nicktest").withTitle("QA").withCompany("VRTtest").withAddress("Minsk").withMobilePhone("+375445555555").
            withEmail("email@domain.com").withEmail2("emailtest@domain.com").withHomepage("test.com").withBday("10").
            withBmonth("November").withByear("2000").withGroup("test1").withPhoto(photo);
    */
    app.contact().initContactCreation();
    app.contact().fillContactForm(newContact, true);
    app.contact().submitContactCreation();
    app.contact().waitForMessage();
    app.goTo().homePage();


    assertThat(app.contact().count(), equalTo(before.size() + 1));
    //Contacts after = app.contact().all();
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
