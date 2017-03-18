package scenarios;

import listeners.ScreenshootsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AthenaPage;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

/**
 * Created by suci on 3/17/17.
 */
@Listeners(ScreenshootsListener.class)
@Features("Login Test")
public class AthenaTest extends AndroidSetup {
    AthenaPage athenaPage;

    @Stories("As a User I want to be able to login")
    @Title("User Login Success")
    @Test
    public void testLogin_CorrectCredential_Success(){
        athenaPage = new AthenaPage(driver);
        athenaPage.typeUsername("athena@olx.com");
        athenaPage.typePassword("123456");
        athenaPage.clickLoginButton();
        athenaPage.verifySuccessLogin();
    }

    @Stories("As a User I want to get error message while login using wrong credentials")
    @Title("Fail Login")
    @Test
    public void testLogin_inCorrectCredential_Fail(){
        athenaPage = new AthenaPage(driver);
        athenaPage.typeUsername("wrong email");
        athenaPage.typePassword("wrong password");
        athenaPage.clickLoginButton();
        athenaPage.verifyFailLogin();
    }

}
