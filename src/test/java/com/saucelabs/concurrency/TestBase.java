package com.saucelabs.concurrency;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

public class TestBase {

  public WebDriver driver;

  @RegisterExtension public SauceTestWatcher watcher = new SauceTestWatcher();
  protected TestInfo testInfo;
  protected SessionId id;
  private static final String buildName;
  private static final String buildNumber;

  static {
    if (System.getenv("GITHUB_WORKFLOW") != null) {
      buildName = System.getenv("GITHUB_WORKFLOW");
      buildNumber = System.getenv("GITHUB_RUN_NUMBER");
    } else {
      buildName = "Concurrency Demo";
      buildNumber = String.valueOf(System.currentTimeMillis());
    }
  }

  public void startWindowsSession(TestInfo testInfo) {
    ChromeOptions options = new ChromeOptions();
    options.setPlatformName("Windows 11");
    startSession(testInfo, options);
  }

  public void startMacSession(TestInfo testInfo) {
    ChromeOptions options = new ChromeOptions();
    options.setPlatformName("MacOS 13");
    startSession(testInfo, options);
  }

  public void startSession(TestInfo testInfo, Capabilities options) {
    startSession(options, defaultSauceOptions(testInfo));
  }

  protected void startSession(Capabilities options, Map<String, Object> sauceOptions) {
    ((MutableCapabilities) options).setCapability("sauce:options", sauceOptions);

    URL url;
    try {
      url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    driver = new RemoteWebDriver(url, options);
    this.id = ((RemoteWebDriver) driver).getSessionId();
  }

  private Map<String, Object> defaultSauceOptions(TestInfo testInfo) {
    this.testInfo = testInfo;

    Map<String, Object> options = new HashMap<>();
    options.put("username", System.getenv("SAUCE_USERNAME"));
    options.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    options.put("name", testInfo.getDisplayName());
    options.put("build", buildName + ": " + buildNumber);
    options.put("seleniumVersion", System.getProperty("selenium.version"));
    return options;
  }

  protected void loopCommands() {
    long seconds = Long.parseLong(System.getProperty("test.duration"));
    loopCommands(Duration.ofSeconds(seconds));
  }
  protected void loopCommands(Duration duration) {
    int l = (int) Math.round(duration.toSeconds() / 5.0);
    IntStream.range(0, l)
        .forEach(
            i -> {
              driver.getTitle();
              try {
                Thread.sleep(5000);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            });
  }

  public class SauceTestWatcher implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
      printResults();
      try {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        driver.quit();
      } catch (Exception e) {
        System.out.println("problem with using driver: " + e);
      }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
      printResults();

      try {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
        driver.quit();
      } catch (Exception e) {
        System.out.println("problem with using driver: " + e);
      }
    }

    public void printResults() {
      String sauceReporter =
          String.format("SauceOnDemandSessionID=%s job-name=%s", id, testInfo.getDisplayName());
      String sauceTestLink = String.format("Test Job Link: https://app.saucelabs.com/tests/%s", id);
      System.out.print(sauceReporter + "\n" + sauceTestLink + "\n");
    }
  }
}
