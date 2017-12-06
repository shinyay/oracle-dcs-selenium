package integrationtest.demoapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DemoAppTest {
    private WebDriver driver;
    private String baseUrl;
    private String contextroot;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        String PROXY = System.getProperty("proxy");
        if (PROXY != null) {
            org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
            proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(CapabilityType.PROXY, proxy);
            driver = new FirefoxDriver(cap);
        } else {
            driver = new FirefoxDriver();
        }

        baseUrl = System.getProperty("baseurl");
        contextroot = System.getProperty("contextroot");
        if (baseUrl == null) {
            throw new Exception(
                    "You need to set the property 'baseurl'. Example : https://xxxxxx/'");
        }
        if (contextroot == null) {
            throw new Exception(
                    "You need to set the property 'contextroot'. Example : demoapp'");
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testFeatureC() throws Exception {
        driver.get(baseUrl + contextroot + "/top.jsp");
        try {
            assertEquals("featureC", driver.findElement(By.xpath("//h2[2]"))
                    .getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testHelloWorld() throws Exception {
        driver.get(baseUrl + contextroot + "/top.jsp");
        try {
            assertEquals("Hello World!", driver.findElement(By.xpath("//h2[1]"))
                    .getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
