package listeners;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import pages.BasePage;
import pages.Constants;
import pages.InstanceDriver;
import ru.yandex.qatools.allure.utils.AnnotationManager;
import utils.Log;

import java.lang.annotation.Annotation;
import java.util.Iterator;

/**
 * Created by buddyarifin on 6/14/16.
 */
public class ScreenshootsListener extends TestListenerAdapter  {

    private BasePage base;
    private WebDriver driver;
    private Object obj;
    private Annotation[] annotations;
    private Hub hub;
    private String[] includedTest = {Constants.FILTER_TEST, Constants.POSTADS_TEST,
            Constants.PAID_TEST, Constants.POSTADSTEST};

    @Override
    public void onTestFailure(ITestResult testResult){
        obj = testResult.getInstance();
        driver = ((InstanceDriver)obj).driver;
        base = new BasePage(driver);
        try {
            Log.error("***** Error "+getTestTitle(testResult)+" test has failed on devices ==> ["+getDeviceName()+"] *****");
            base.getAttachment("FailedOn_"+getTestClassName(testResult)+testResult.getMethod().getMethodName()+".png");
//            request.goToTracker(testResult, this.array);
            Log.error("FailedOn_"+ getTestClassName(testResult)+testResult.getMethod().getMethodName()+".png");
        } catch (Exception e){
            Log.error("-->Unable to screen capture, for test "+getTestTitle(testResult));
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult){
        Log.debug("Running Test --> "+getTestTitle(iTestResult));
        driver = setDriver(iTestResult);
//        ((AndroidDriver)driver).removeApp("io.appium.settings");
        if (ArrayUtils.contains(includedTest, getTestClassName(iTestResult))) {
            Log.debug("Removing Unicode App on Devices : "+getDeviceName());
            ((AndroidDriver)driver).removeApp(Constants.UNICODE_APP);
        }
    }

    public Object getDeviceName() {
        return ((AndroidDriver)driver).getCapabilities().getCapability("deviceName");
    }

    @Override
    public void onTestSuccess(ITestResult testResult){
        driver = setDriver(testResult);
        base = new BasePage(driver);
        try {
            Log.debug("***** Success Execution for "+getTestTitle(testResult)+" *****");
//            request.goToTracker(testResult, this.array);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {
            Iterator<ITestResult> listOfFailedTests = testContext.getFailedTests().getAllResults().iterator();
            while (listOfFailedTests.hasNext()){
                ITestResult failedTest = listOfFailedTests.next();
                ITestNGMethod method = failedTest.getMethod();
                if (testContext.getFailedTests().getResults(method).size() > 1){
                    listOfFailedTests.remove();
                } else {
                    if (testContext.getPassedTests().getResults(method).size() > 0) {
                        listOfFailedTests.remove();
                    }
                }
            }
    }

    private String getTestTitle(ITestResult result) {
        annotations = result.getMethod().getConstructorOrMethod().getMethod().getAnnotations();
        AnnotationManager annotationManager = new AnnotationManager(annotations);
        return annotationManager.getTitle();
    }

    private WebDriver setDriver(ITestResult testResult){
        obj = testResult.getInstance();
        return driver = ((InstanceDriver)obj).driver;
    }

    public String getTestClassName(ITestResult testResult) {
        return testResult.getTestClass().getName();
    }

}
