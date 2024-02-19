package com.saucelabs.concurrency;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class Windows3Test extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startWindowsSession(testInfo);
  }

  @Test
  public void win3Test0() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test1() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test2() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test3() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test4() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test5() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test6() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test7() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test8() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void win3Test9() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }
}
