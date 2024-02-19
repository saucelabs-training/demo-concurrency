package com.saucelabs.concurrency;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class Windows4Test extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startWindowsSession(testInfo);
  }

  @Test
  public void win4Test0() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test1() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test2() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test3() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test4() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test5() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test6() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test7() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test8() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win4Test9() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }
}
