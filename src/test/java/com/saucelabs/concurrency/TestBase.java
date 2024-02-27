package com.saucelabs.concurrency;

import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.saucerest.api.AccountsEndpoint;
import com.saucelabs.saucerest.model.accounts.Organization;
import com.saucelabs.saucerest.model.accounts.UserConcurrency;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
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
    if (System.getProperty("build.name") != null) {
      buildName = System.getProperty("build.name");
      buildNumber = System.getenv("GITHUB_RUN_NUMBER");
    } else if (System.getenv("GITHUB_WORKFLOW") != null) {
      buildName = System.getenv("GITHUB_WORKFLOW");
      buildNumber = System.getenv("GITHUB_RUN_NUMBER");
    } else {
      buildName = "Concurrency Demo";
      buildNumber = String.valueOf(System.currentTimeMillis());
    }
  }

  @BeforeEach
  public void setUp(TestInfo testInfo) throws IOException, InterruptedException {
    this.testInfo = testInfo;
    String username = System.getenv("SAUCE_USERNAME");
    String accessKey = System.getenv("SAUCE_ACCESS_KEY");
    SauceREST sauceREST = new SauceREST(username, accessKey, DataCenter.US_WEST);
    AccountsEndpoint accountsEndpoint = sauceREST.getAccountsEndpoint();

    boolean sessionStarted = false;

    while (!sessionStarted) {
      int randomDuration = new Random().nextInt(14000 + 1) + 1000;

      Thread.sleep(randomDuration);
      UserConcurrency userConcurrency =
          accountsEndpoint.getUserConcurrency(sauceREST.getUsername());
      Organization org = userConcurrency.concurrency.organization;
      System.out.println("Current concurrency: " + org.current.vms);

      if (org.current.vms < org.allowed.vms) {
        startSession(testInfo);
        sessionStarted = true;
      } else {
        System.out.println("Concurrency limit reached. Retrying in 10 seconds...");
      }
    }
  }

  private void startSession(TestInfo testInfo) {
    if (new Random().nextBoolean() && Boolean.getBoolean("tests.mixed")) {
      startMacSession(testInfo);
    } else {
      startWindowsSession(testInfo);
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
    if (System.getProperty("test.duration") != null) {
      long seconds = Long.parseLong(System.getProperty("test.duration"));
      loopCommands(Duration.ofSeconds(seconds));
    } else {
      int randomDuration = new Random().nextInt(30 - 10 + 1) + 10;
      loopCommands(Duration.ofSeconds(randomDuration));
    }
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
