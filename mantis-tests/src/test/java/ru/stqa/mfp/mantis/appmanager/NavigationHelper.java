package ru.stqa.mfp.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void manageUsers() {
    if (isElementPresent(By.tagName("div.main-content"))
            && wd.findElement(By.tagName("span")).getText().equals("Manage")) {
      return;
    }
    click(By.cssSelector("a[href='/mantisbt-2.24.4/manage_overview_page.php']"));
    click(By.cssSelector("a[href='/mantisbt-2.24.4/manage_user_page.php'"));
  }

}