package com.saucelabs.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class Mac1Test extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startMacSession(testInfo);
  }

  @Test
  public void mac1Test0() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test1() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test2() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test3() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test4() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test5() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test6() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test7() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test8() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac1Test9() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }
}
