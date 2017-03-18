package pages;

import io.appium.java_client.android.AndroidDriver;
import utils.Log;

/**
 * Created by buddyarifin on 8/29/16.
 */
public class InstanceDriver {

    public AndroidDriver driver;
    private static String environmentTest;

    protected static String getEnvironmentTest() {
        return environmentTest;
    }
    protected static void setEnvironmentTest(String environmentTest) {
        Log.debug("Running Test pointing to "+environmentTest);
        InstanceDriver.environmentTest = environmentTest;
    }
}
