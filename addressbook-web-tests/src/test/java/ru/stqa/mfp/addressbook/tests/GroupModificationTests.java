package ru.stqa.mfp.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.mfp.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupModification() {
    //int before = app.getGroupHelper().getGroupCount();
    List<GroupData> before = app.group().list();
    int index = before.size() - 1;
    GroupData group = new GroupData(before.get(index).getId(), "test1", "test2", "test3");
    app.group().modify(index, group);
    //int after = app.getGroupHelper().getGroupCount();
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }
}