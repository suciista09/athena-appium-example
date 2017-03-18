package pages;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by suci on 3/17/17.
 */
public class AthenaPage extends BasePage {

    private String userNameField = "com.naspers_classifieds.loginsample:id/username";
    private String passwordField = "com.naspers_classifieds.loginsample:id/password";
    private String loginButton = "com.naspers_classifieds.loginsample:id/login";
    private String messageText = "com.naspers_classifieds.loginsample:id/message";
    private int timeout = 10;

    public AthenaPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @Step("I type username")
    public void typeUsername(String username){
        sendKeysElement(getIdLocator(userNameField),username);
    }

    @Step("I type password")
    public void typePassword(String password){
        sendKeysElement(getIdLocator(passwordField),password);
    }

    @Step("I click login button")
    public void clickLoginButton(){
        waitForClickabilityOf(getIdLocator(loginButton),timeout);
        clickElement(getIdLocator(loginButton));
    }

    @Step("I verify login is success")
    public void verifySuccessLogin(){
        Assert.assertEquals(getStringText(getIdLocator(messageText)),"Success!","Can not find Success!");
    }

    @Step("I verify login is fail")
    public void verifyFailLogin(){
        Assert.assertEquals(getStringText(getIdLocator(messageText)),"Failure!","Can not fing Failure!");
    }

}
