package com.saucelabs.concurrency;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class Windows7Test extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startWindowsSession(testInfo);
  }

  @Test
  public void win7Test0() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test1() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test2() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test3() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test4() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test5() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test6() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test7() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test8() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win7Test9() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }
}
