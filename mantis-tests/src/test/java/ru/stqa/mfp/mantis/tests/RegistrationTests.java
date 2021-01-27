package ru.stqa.mfp.mantis.tests;

import org.testng.annotations.Test;

import static ru.stqa.mfp.mantis.tests.TestBase.app;

public class RegistrationTests {

  @Test
  public void testRegistration() {
    app.registration().start("user1", "user1@localhost.localdomain");
  }
}
