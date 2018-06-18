

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static java.lang.Thread.sleep;

public class GoogleFormPage {

    AppiumDriver webDriver;
    GoogleFormConstant constant = new GoogleFormConstant();

    public GoogleFormPage() throws MalformedURLException {
        URL serverURL = new URL("http://127.0.0.1:4723/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_7_2012_Android_5");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Browser");
        webDriver = new AndroidDriver(serverURL, capabilities);
        webDriver.findElement(By.xpath("//a[text()='русский']")).click();
        webDriver.get("https://docs.google.com/forms/d/e/1FAIpQLSdqT5F9_qhPDmJ4lfIH7buVkUvjf4LS9ODdqD7PYfVbfFTnpA/viewform");
    }

    public void fillFieldPage() {
        fillFieldPageWithoutMood();
        setYourMood(constant.YOUR_MOOD_SUPER);
    }

    public void fillFieldPageWithoutMood(){
        setValue(constant.FIELD_EMAIL, "CorrectEmail@a.com");
        setDateDefaultValue(false);
        setValue(constant.FIELD_NAME, "Nikolay");
        setSexValue(constant.SEX_MAN);
    }

    public void clickSendButton() {
        webDriver.findElement(By.className("quantumWizButtonPaperbuttonEl")).click();
    }

    public void setValue(int elementName, String value) {
        getElement(elementName).findElement(By.tagName("input")).sendKeys(value);
    }

    public void setDateDefaultValue(boolean futureDate) {
        getElement(1).findElement(By.className("quantumWizTextinputPaperinputInput")).click();
        Set<String> contextNames = webDriver.getContextHandles();
        webDriver.context((String) contextNames.toArray()[0]);
        WebElement year = (WebElement)webDriver.findElementsById("numberpicker_input").get(2);
        if(futureDate) {
            year.sendKeys(Keys.DELETE);
            year.sendKeys("2030");
        }
        webDriver.findElementById("button1").click();
        webDriver.context((String) contextNames.toArray()[1]);
    }

    public void clearValue(int elementName) {
        WebElement elem = getElement(elementName).findElement(By.tagName("input"));
        elem.clear();
        elem.sendKeys("a", Keys.BACK_SPACE);
    }

    public void setSexValue(int value) {
        webDriver.findElement(By.className("quantumWizMenuPaperselectEl")).click();

        getElement(constant.FIELD_SEX).findElement(By.className("exportSelectPopup")).findElements(By.className("exportOption")).get(value).click();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setYourMood(int value) {
        getElement(constant.FIELD_YOUR_MOOD).findElements(By.className("docssharedWizToggleLabeledContainer ")).get(value).click();
    }

    public String getElementTitle(int elementName) {
        return getElement(elementName).findElement(By.className("freebirdFormviewerViewItemsItemItemTitle")).getText();
    }

    public WebElement getElement(int elementName) {
        return (WebElement) webDriver.findElements(By.className("freebirdFormviewerViewItemsItemItem")).get(elementName);
    }

    public void checkElementError(int elementName, String errorMessage) {
        Assert.assertNotNull("Element not have red color",
                getElement(elementName).getAttribute("class").contains("HasError"));

        Assert.assertEquals("Element not have correct errorMessage", errorMessage,
                getElement(elementName).findElement(By.className("freebirdFormviewerViewItemsItemErrorMessage")).getText());
    }

    public String getDate (int dayPlus){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, dayPlus);
        Date date = cal.getTime();
        return sdf.format(date);
    }

    public void conformSend(){
        Assert.assertEquals("Not confirmed send form", "Ответ записан.",
                webDriver.findElement(By.className("freebirdFormviewerViewResponseConfirmationMessage")).getText());
    }

    public void closeChrome() {
        webDriver.quit();
    }
}
