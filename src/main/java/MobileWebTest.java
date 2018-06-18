
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.net.MalformedURLException;

public class MobileWebTest {

    GoogleFormPage page;
    GoogleFormConstant constant = new GoogleFormConstant();

    @Before
    public void setup() throws MalformedURLException {
        System.out.println("Start Tests");
        page = new GoogleFormPage();
    }

    @Test
    // testId=001
    public void checkTitleElements() {
        Assert.assertEquals("Not correct title name Title page", "Форма регистрации",
                page.webDriver.findElement(By.className("freebirdFormviewerViewHeaderTitle")).getText());
        Assert.assertEquals("Not correct title name Title Description page", "Тест форма",
                page.webDriver.findElement(By.className("freebirdFormviewerViewHeaderDescription")).getText());
        Assert.assertEquals("Not correct title name Title Required Legend page", "* Обязательно",
                page.webDriver.findElement(By.className("freebirdFormviewerViewHeaderRequiredLegend")).getText());

        Assert.assertEquals("Not correct element name Email address", "Адрес электронной почты *",
                page.getElementTitle(constant.FIELD_EMAIL));
        Assert.assertEquals("Not correct element name Birthday date", "Дата рождения *",
                page.getElementTitle(constant.FIELD_BIRTHDAY_DATE));
        Assert.assertEquals("Not correct element name Name", "Имя *",
                page.getElementTitle(constant.FIELD_NAME));
        Assert.assertEquals("Not correct element name Sex", "Пол *",
                page.getElementTitle(constant.FIELD_SEX));
        Assert.assertEquals("Not correct element name Your mood", "Как ваше настроение ? :) *",
                page.getElementTitle(constant.FIELD_YOUR_MOOD));
    }

    @Test
    // testId=002
    public void checkCorrectSend() {
        page.fillFieldPage();
        page.clickSendButton();

        page.conformSend();
    }

    @Test
    // testId=003
    public void checkElementsMessageAfterClearValues() {
        page.fillFieldPage();

        page.clearValue(constant.FIELD_EMAIL);
        page.checkElementError(constant.FIELD_EMAIL, constant.ERRORMASSAGE);

        page.clearValue(constant.FIELD_NAME);
        page.checkElementError(constant.FIELD_NAME, constant.ERRORMASSAGE);

        page.setSexValue(0);
        page.checkElementError(constant.FIELD_SEX, constant.ERRORMASSAGE);

        page.setYourMood(constant.YOUR_MOOD_SUPER);
        page.checkElementError(constant.FIELD_YOUR_MOOD, constant.ERRORMASSAGE);
    }

    @Test
    // testId=004
    public void checkElementsMessageEmptyValues() {
        page.clickSendButton();

        page.checkElementError(constant.FIELD_EMAIL, constant.ERRORMASSAGE);

        page.checkElementError(constant.FIELD_BIRTHDAY_DATE, constant.ERRORMASSAGE);

        page.checkElementError(constant.FIELD_NAME, constant.ERRORMASSAGE);

        page.checkElementError(constant.FIELD_SEX, constant.ERRORMASSAGE);

        page.checkElementError(constant.FIELD_YOUR_MOOD, constant.ERRORMASSAGE);
    }

    @Test
    // testId=005
    public void checkElementsIncorrectMessageEmail() {
        page.setValue(constant.FIELD_EMAIL, "IncorrectEmail@.com");
        page.checkElementError(constant.FIELD_EMAIL, "Укажите действительный адрес эл. почты");
    }


    @Test
    // testId=006
    public void checkElementsWithoutIncorrectMessageWithMaxName() {
        page.setValue(constant.FIELD_NAME, "aaaaaaaaaaaaaaaaaaaa");
        page.checkElementError(constant.FIELD_NAME, "");
    }

    @Test
    // testId=007
    public void checkElementsIncorrectMessageName() {
        page.setValue(constant.FIELD_NAME, "aaaaaaaaaaaaaaaaaaaaa");
        page.checkElementError(constant.FIELD_NAME, "Максимальное количество символов 20 превышено");
    }

    @Test
    // testId=008
    public void checkSendWithCorrectDate() {
        page.fillFieldPage();

        page.setValue(constant.FIELD_BIRTHDAY_DATE, page.getDate(0));
        page.clickSendButton();
        page.conformSend();
    }

    @Test
    // testId=009
    public void checkSendWithIncorrectDate() {
        page.fillFieldPage();
        page.setDateDefaultValue(true);

        page.checkElementError(constant.FIELD_BIRTHDAY_DATE, "Недопустимая дата.");
    }

    @Test
    // testId=010
    public void SelectYourMoodsSuper() {
        page.fillFieldPageWithoutMood();
        page.setYourMood(constant.YOUR_MOOD_SUPER);

        page.clickSendButton();
        page.conformSend();
    }

    @Test
    // testId=011
    public void SelectYourMoodsGood() {
        page.fillFieldPageWithoutMood();
        page.setYourMood(constant.YOUR_MOOD_GOOD);

        page.clickSendButton();
        page.conformSend();
    }

    @Test
    // testId=012
    public void SelectYourMoodsNormal() {
        page.fillFieldPageWithoutMood();
        page.setYourMood(constant.YOUR_MOOD_NORMAL);

        page.clickSendButton();
        page.conformSend();
    }

    @Test
    // testId=013
    public void SelectYourMoodsFine() {
        page.fillFieldPageWithoutMood();
        page.setYourMood(constant.YOUR_MOOD_FINE);

        page.clickSendButton();
        page.conformSend();
    }

    @Test
    // testId=014
    public void SelectYourMoodsBad() {
        page.fillFieldPageWithoutMood();
        page.setYourMood(constant.YOUR_MOOD_BAD);

        page.clickSendButton();
        page.conformSend();
    }

    @Test
    // testId=015
    public void cantSelectTwoYourAnother() {
        page.fillFieldPageWithoutMood();
        page.setYourMood(constant.YOUR_MOOD_ANOTHER);
        page.webDriver.findElement(By.className("quantumWizTextinputSimpleinputInput")).sendKeys("asd");
        page.clickSendButton();
        page.conformSend();
    }

    @Test
    // testId=016
    public void cantSelectTwoYourMoods() {
        page.setYourMood(constant.YOUR_MOOD_SUPER);
        page.setYourMood(constant.YOUR_MOOD_GOOD);

        page.checkElementError(constant.FIELD_YOUR_MOOD, constant.ERRORMASSAGE);
    }
}
