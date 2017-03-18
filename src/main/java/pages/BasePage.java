package pages;

import com.google.common.base.Function;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import utils.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created Simple by AT team
 */
public class BasePage  {

    public static final String imageDiffDir = "src/test/resources/imageRecognition/";
    public static final String ACTUAL_COLOR = "actualColor";
    public static final String EXPECTED_COLOR = "expectedColor";
    public static final String MARKED_DIFF_IMG = "actualMarked";
    public static final String alertContent = "com.app.tokobagus.betterb:id/md_content";
    public static final String tapOkButton = "com.app.tokobagus.betterb:id/md_buttonDefaultPositive";
    public static final String tapBatalButton = "com.app.tokobagus.betterb:id/md_buttonDefaultNegative";
    public static final String okKonfirmasiPopUp = "com.app.tokobagus.betterb:id/md_buttonDefaultPositive";
    public static final String batalKonfirmasiPopUp = "com.app.tokobagus.betterb:id/md_buttonDefaultNegative";
    protected WebDriver driver;

    // Layout Not Found Search
    private static final String notfoundLayoutId = "com.app.tokobagus.betterb:id/layout_no_result";
    private static final String imageView = "android.widget.ImageView";

    @AndroidFindBys({
            @AndroidFindBy(id = notfoundLayoutId),
            @AndroidFindBy(className = imageView)
    })
    private AndroidElement notFoundContent;

    public BasePage(WebDriver driver) {
        this.driver = driver;
//        this.rdata = new Sinon();
    }
    
    protected void waitForVisibilityOf(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Assert.assertTrue(isElementPresent(locator));
    }
    protected void waitForClickabilityOf(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    protected void waitForClickabilityOf(By locator, int time){
    	WebDriverWait wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void WaitFor(int seconds){
        int nanos = seconds * 1000;
        try {
            Thread.sleep(nanos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected AndroidDriver getAndroidDriver() {
        return ((AndroidDriver)driver);
    }

    /**
     * This below method has made to verify that size ListELement for WebView is not Empty
     */
    protected boolean isListElementPresentWeb(List<WebElement> list) {
        try {
            if (list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * This below method has made to verify that size ListELement is not Empty
     */
    protected boolean isListElementPresent(List<AndroidElement> list)
    {
        try {
            if (list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    protected boolean isElementPresent(By by) {
   	try {   
			if (driver.findElement(by).isDisplayed()){
		     	return true;
			}else{
				return false;
			}
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

    /**
     * This below method has made to always allow alert pop-up
     */
    public boolean isAutoAcept(By by)
    {
        try
        {
            if (isDevicesMarshmallow()) {
                waitForVisibility(by);
                clickElement(by);
                if (waitForVisibility(by))
                {
                    clickElement(by);
                    return true;
                }
                else
                {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        catch (NoSuchElementException | TimeoutException e)
        {
            return true;
        }
    }

    public boolean isDevicesMarshmallow() {
        return getVersionDevices().startsWith("6");
    }

    protected boolean isWaitElementPresent(By by){
     try {
    	 waitForVisibilityOf(by);
    	 return true;
     } catch (NoSuchElementException | TimeoutException e){
    	 return false;
     } catch (WebDriverException e) {
         return false;
     }
    }

    protected boolean isWaitElementPresent(WebElement element){
        try {
            waitForVisibility(element);
            return true;
        } catch (NoSuchElementException | TimeoutException e){
            return false;
        } catch (WebDriverException e) {
            return false;
        }
    }
    
    protected void clickElement(By by){
    	//waitForClickabilityOf(by);
    	waitForVisibilityOf(by);
    	driver.findElement(by).click();
    }
    
    protected void clickElement(By by, int time){
    	waitForClickabilityOf(by, time);
    	driver.findElement(by).click();
    }
    
    protected void clickElementWithoutWait(By by){
    	driver.findElement(by).click();
    }
    
    protected void sendKeysElement(By by,String keys){
    	waitForVisibilityOf(by);
    	driver.findElement(by).clear();
    	driver.findElement(by).sendKeys(keys);
    }

    protected void sendKeysById(By locator, String keys){
        WebElement element = driver.findElement(locator);
//        element.clear();
        element.sendKeys(keys);
        AndroidDriver driver = (AndroidDriver) this.driver;
        if (!driver.isAppInstalled(Constants.UNICODE_APP)) {
            hideSoftKeyboard();
            //WaitFor(1);
            //driver.navigate().back();
        }
    }

    public void hideSoftKeyboard() {
        try{
            ((AndroidDriver)driver).hideKeyboard(); // Closing Keyboard
        }catch (WebDriverException e){
            Log.debug("Softkeyboard not displaying");
        }
    }

    protected void sendKeysElements(By locator,int index, String keys){
    	waitForVisibilityOf(locator);
    	WebElement element=getTextElements(locator, index);
    	element.clear();
    	element.sendKeys(keys);
    }
    
    protected void sendKeysElements(WebElement element,String keys){
        Assert.assertTrue(element.isDisplayed());
    	element.sendKeys(keys);
    }
    
    protected void clickElements(WebElement element){
        Assert.assertTrue(element.isDisplayed());
    	element.click();
    }
    
    public String getStringText(By locator){
       return driver.findElement(locator).getText();	
    }
    
    public By getTextLocator(String locator){
    	return By.xpath("//android.widget.TextView[@text='"+locator+"']");
    }

    public By getSwitchTextLocator(String locator) {
        return By.xpath("//android.widget.Switch[@text='"+locator+"']");
    }
    
    public By getEditTextLocator(String locator){
    	return By.xpath("//android.widget.EditText[@text='"+locator+"']");
    }

    public By getEditTextResource(String locator) { return By.xpath("//android.widget.EditText[@resource-id='"+locator+"']"); }
    
    public By getIdLocator(String locator){
    	return By.id(locator);
    }

    /**
     * This below method to get locator by text value with classname TextInputLayout
     */
    public By getTextInputLayoutLocator(String locator)
    {
        return By.xpath("//TextInputLayout[@text='"+locator+"']");
    }

    public By getImageLocator(int index){
    	return By.xpath("//android.widget.ImageButton[@index='"+index+"']");
    }

    public By getImageViewLocator(int index){
    	return By.xpath("//android.widget.ImageView[@index='"+index+"']");
    }

    public By getContentLocator(String locator){
    	return By.xpath("//android.widget.ImageButton[@content-desc='"+locator+"']");
    }

    public By getContentDescLocator(String locator){
        return By.xpath("//android.widget.TextView[@content-desc='"+locator+"']");
    }

    public By getResourceLocator(String locator){ return By.xpath("//android.widget.ImageButton[@resource-id='"+locator+"']"); }
    
    public By getButtonLocator(String locator){
    	return By.xpath("//android.widget.Button[@resource-id='"+locator+"']");
    }

    public By getAndroidViewLocator(int index){
    	return (By.xpath("//android.view.View[@clickable='True']"));
    }

    public By getAndroidViewTextLocator(String locator){
    	return (By.xpath("//android.view.View[@content-desc='"+locator+"']"));
    }

    public By getToogleTextLocator(String locator ) { return (By.xpath("//android.widget.ToggleButton[@text='"+locator+"']")); }
    
    public By getSpinnerLocator(String locator){
    	return By.xpath("//android.widget.CheckedTextView[@text='"+locator+"']");
    }
    
    protected byte[] attachScreenShot(String filename) throws IOException{
    	File file = new File(Constants.screenshotsDir+filename);
    	FileOutputStream screenshotStream = new FileOutputStream(file);
    	byte[] bytes =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);	
    	screenshotStream.write(bytes);
        screenshotStream.close();
        return bytes;
    }

    protected void takeScreenShotInFile(String filename) throws Exception{
    	File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    	new File(Constants.screenshotsDir).mkdirs();
    	FileUtils.copyFile(file, new File(Constants.screenshotsDir+filename));
    }
    
    protected WebElement getTextElements(String locator,int index){
    	List<WebElement> elements = driver.findElements(getIdLocator(locator));
    	return elements.get(index);
    }
    
    protected List<WebElement> getListElements(By locator){
    	waitForVisibilityOf(locator);
    	List<WebElement> elements = driver.findElements(locator);
    	return elements;
    }
    
    protected WebElement getTextElements(By locator,int index){
    	waitForVisibilityOf(locator);
    	List<WebElement> elements = driver.findElements(locator);
    	return elements.get(index);
    }
    
    protected int getSizeElements(By locator){
    	//waitForVisibilityOf(locator);
    	List<WebElement> elements = driver.findElements(locator);
    	return elements.size();
    }
    
    @Attachment(value = "{0}", type = "image/png")
	public byte[] getAttachment(String filename) throws Exception{
    	takeScreenShotInFile(filename);
		return attachScreenShot(filename);
	}
    
    @Attachment("{method}")
    public String getTextAttachment(String input) throws Exception{
    	return input;
    }
	
    
    public void scrollPageUp() {
        System.out.println("Scrolling the content..");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.50);
        swipeObject.put("startY", 0.95);
        swipeObject.put("endX", 0.50);
        swipeObject.put("endY", 0.01);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    /**
     * This below method has made to scroll right page
     */
    public void swipePageRightToLeft()
    {
        int startx = (int) (driver.manage().window().getSize().getWidth() * 0.70);
        int endx = (int) (driver.manage().window().getSize().getWidth() * 0.30);
        int starty = driver.manage().window().getSize().getHeight() / 2;
        ((AndroidDriver)driver).swipe(startx, starty, endx, starty, 3000);
    }

    /**
     * This below method has made to scroll left page
     */
    public void swipePageLeftToRight()
    {
        int startx = (int) (driver.manage().window().getSize().getWidth() * 0.70);
        int endx = (int) (driver.manage().window().getSize().getWidth() * 0.30);
        int starty = driver.manage().window().getSize().getHeight() / 2;
        ((AndroidDriver)driver).swipe(endx, starty, startx, starty, 3000);
    }

    /**
     * This below method has made to scroll down page
     */
    public void swipePageBtmtToTop()
    {
        int starty = (int) (driver.manage().window().getSize().getHeight() * 0.80);
        int endy = (int) (driver.manage().window().getSize().getHeight() * 0.20);
        int startx = driver.manage().window().getSize().getWidth() / 2;
        ((AndroidDriver)driver).swipe(startx, starty, startx, endy, 500);
    }

    /**
     * This below method has made to scroll up page
     */
    public void swipePageTopToBtm()
    {
        int starty = (int)(driver.manage().window().getSize().getHeight() * 0.80);
        int endy = (int) (driver.manage().window().getSize().getHeight() * 0.20);
        int startx = driver.manage().window().getSize().getWidth() / 2;
        ((AndroidDriver)driver).swipe(startx, endy, startx, starty, 500);
    }

    public void swipeLeftToRight() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.01);
        swipeObject.put("startY", 0.5);
        swipeObject.put("endX", 0.9);
        swipeObject.put("endY", 0.6);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    public void swipeRightToLeft() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.9);
        swipeObject.put("startY", 0.5);
        swipeObject.put("endX", 0.01);
        swipeObject.put("endY", 0.5);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    public void swipeFirstCarouselFromRightToLeft() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.9);
        swipeObject.put("startY", 0.2);
        swipeObject.put("endX", 0.01);
        swipeObject.put("endY", 0.2);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    public void performTapAction(WebElement elementToTap) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("x", (double) 360); // in pixels from left
        tapObject.put("y", (double) 170); // in pixels from top
        tapObject.put("element", Double.valueOf(((RemoteWebElement) elementToTap).getId()));
        js.executeScript("mobile: tap", tapObject);
    }

    /**
     *
     * This method using fluentWait, this method will loop delay
     * - with actions - on Seconds until equals with
     * expected Time Out.
     *
     * @param locator is to spesific element.
     * @return boolean value
    */
    public Boolean isElementPresentAfterScrollDown(final By locator) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(80, TimeUnit.SECONDS)
                    .pollingEvery(2, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class);
            return wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    ((AndroidDriver)driver).swipe(200, 500, 200, 45, 1000);
                    return isElementPresent(locator);
                }
            });
        }catch (NoSuchElementException | TimeoutException e){
            return false;
        }
    }


    public Boolean isElementPresentAfterScrollUp(final By locator) {
        try{
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(60, TimeUnit.SECONDS)
                    .pollingEvery(2, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class);
            return wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    swipePageTopToBtm();
                    return driver.findElement(locator).isDisplayed();
                }
            });
        }catch (WebDriverException e){
            return false;
        }
    }

    public Boolean isWaitListElementPresent(final List<AndroidElement> element) {
        try{
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(10, TimeUnit.SECONDS)
                    .pollingEvery(5, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class);
            return wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return element.get(0).isDisplayed();
                }
            });
        }catch (WebDriverException | IndexOutOfBoundsException e){
            return false;
        }
    }

    public Boolean waitForVisibility(final By locator){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        return wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return driver.findElement(locator).isDisplayed();
            }
        });
    }
    public Boolean waitForVisibility(final WebElement element){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        return wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return element.isDisplayed();
            }
        });
    }
    
    //creating sort descending
    public int[] sortDesc(int[] intArray) { 
        int n = intArray.length;
        int temp = 0;
        for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){  
                        if(intArray[j-1] < intArray[j]){
                                //swap the elements!
                                temp = intArray[j-1];
                                intArray[j-1] = intArray[j];
                                intArray[j] = temp;
                        }
                }
        }
        return intArray;
    }

    public String getVersionDevices() {
        return (String)((AndroidDriver)driver).getCapabilities().getCapability("platformVersion");
    }

    public void switchWebViewCtx() {
        Log.debug("Switch to Webview Mode");
        driver = ((AndroidDriver)driver).context("WEBVIEW_"+Constants.appPackage);
    }

    public void switchNativeCtx() {
        Log.info("Switch to Native Mode");
        driver = ((AndroidDriver)driver).context("NATIVE_APP");
    }

    public void clickBySize(Point point) {
        ((AndroidDriver)driver).tap(1, point.getX(),
                point.getY(), 100);
    }

    public Point getPointLocation(By by) {
        WebElement element = driver.findElement(by);
        return element.getLocation();
    }

    public Dimension getDimesionElement(By by) {
        WebElement element = driver.findElement(by);
        return element.getSize();
    }

    public BufferedImage convertImgFileToBufferedImage(String imagePath){
        BufferedImage in = null;
        try {
            in = ImageIO.read(new File(imagePath+ getTestclassName() +".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public BufferedImage convertImgFileToBufferedImage(String imagePath, String filename){
        BufferedImage in = null;
        try {
            in = ImageIO.read(new File(imagePath + filename + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public void createPNGFile(BufferedImage bufferedImage, String filename, String imageDir) {
        try{
            new File(imageDir).mkdirs();
            File captured = new File(imageDir + filename + ".png");
            ImageIO.write(bufferedImage, "PNG", captured);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPNGFile(BufferedImage bufferedImage, String filename){
        try{
            new File(imageDiffDir).mkdirs();
            File captured = new File(imageDiffDir+ filename+ getTestclassName() +".png");
            ImageIO.write(bufferedImage.getSubimage(0, 0, 30, 30), "PNG", captured);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkTutorialsColors(By by) {
        WaitFor(3);
        setExpectedColor(by); // captured IMG only first time install
        BufferedImage bufferedImage = getSpesificScreenshot(by);

        createPNGFile(bufferedImage, ACTUAL_COLOR);
        ImageDiff diff = new ImageDiffer().makeDiff(

                convertImgFileToBufferedImage(imageDiffDir+ EXPECTED_COLOR), // Expected Image
                convertImgFileToBufferedImage(imageDiffDir+ ACTUAL_COLOR)); // Actual Image

        createPNGFile(diff.getMarkedImage(), MARKED_DIFF_IMG);
        return diff.hasDiff();
    }

    public BufferedImage getSpesificScreenshot(By by) {
        isWaitElementPresent(by);
        WebElement element = driver.findElement(by);
        return new AShot().coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, element).getImage();
    }

    public String getTestclassName() {
        return this.getClass().getSimpleName();
    }

    public void setExpectedColor(By by) {
        File expectedFile = new File(imageDiffDir+EXPECTED_COLOR + getTestclassName() +".png");
        if (!(expectedFile.exists() && expectedFile.isFile())) {
            try {
                BufferedImage bufferedImage = getSpesificScreenshot(by);
                createPNGFile(bufferedImage, EXPECTED_COLOR);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** Method to compare image */
    protected static boolean isImgNotEqual(File img1, File img2) throws IOException{
        BufferedImage image1 = ImageIO.read(img1);
        BufferedImage image2 = ImageIO.read(img2);

        DataBufferByte databuff1 = (DataBufferByte)image1.getRaster().getDataBuffer();
        DataBufferByte databuff2 = (DataBufferByte)image2.getRaster().getDataBuffer();

        if (databuff1.getNumBanks() != databuff2.getNumBanks()) {
            return true;
        }

        for (int bank = 0; bank < databuff1.getNumBanks(); bank++) {
            if (!Arrays.equals(databuff1.getData(bank), databuff2.getData(bank))) {
                return true;
            }
        }

        return false;
    }

    public void closeAlertKonf() {
        boolean logoutKonfirmasi = isWaitElementPresent(getIdLocator(alertContent));
        if (logoutKonfirmasi) {
            Log.info("Click OK konfirmasi logout");
            clickElement(getIdLocator(tapBatalButton));
        }
    }

    public void clickBatalOnAlert() {
        boolean logoutKonfirmasi = isWaitElementPresent(getIdLocator(alertContent));
        if (logoutKonfirmasi) {
            Log.info("Click OK konfirmasi on Alert : "+ getTextAlert());
            clickElement(getIdLocator(okKonfirmasiPopUp));
        }
    }

    private String getTextAlert() {
        return getStringText(getIdLocator(alertContent));
    }

    protected boolean isNotFoundSearchContentVisible() {
        try {
            return this.notFoundContent.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e){
            return false;
        }
    }

    protected boolean isEnvTestProduction() {
        return InstanceDriver.getEnvironmentTest().equalsIgnoreCase("release");
    }
}
