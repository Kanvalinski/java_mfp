package ru.stqa.mfp.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.mfp.addressbook.model.ContactData;
import ru.stqa.mfp.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("homepage"), contactData.getHomepage());
    click(By.name("bday"));
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getBday());
    click(By.name("bday"));
    click(By.name("bmonth"));
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getBmonth());
    click(By.name("bmonth"));
    type(By.name("byear"), contactData.getByear());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id +"']")).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptAlertDeleteContact() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int index) {
    wd.findElements(By.xpath("//tr/td/a[contains(@href, 'edit.php?id')]")).get(index).click();
  }

  private void initContactModificationById(int id) {
    wd.findElement(By.cssSelector("a[href='edit.php?id=" + id +"']")).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public void create(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(new ContactData().withFirstname("Name test 2").withMiddlename("Name test 2").withLastname("Lastnametest3").
            withNickname("nicktest").withTitle("QA").withCompany("VRTtest").withAddress("Minsk").withMobile("+375445555555").
            withEmail("email@domain.com").withEmail2("emailtest@domain.com").withHomepage("test.com").withBday("10").
            withBmonth("November").withByear("2000").withGroup("[none]"), true);
    submitContactCreation();
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    acceptAlertDeleteContact();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//input[@name='selected[]']"));
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (WebElement element : elements) {
      String firstname = element.findElement(By.xpath(".//td[3]")).getText();
      String lastname = element.findElement(By.xpath(".//td[2]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

}