package com.saucelabs.concurrency;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class Mac3Test extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startMacSession(testInfo);
  }

  @Test
  public void mac3Test0() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test1() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test2() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test3() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test4() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test5() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test6() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test7() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test8() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }

  @Test
  public void mac3Test9() {
    driver.get("https://www.saucedemo.com/");
    loopCommands();
  }
}
