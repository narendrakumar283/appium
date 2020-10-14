package bmw;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.appium.Eyes;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class AppliTools 
{

	public static void main(String[] args) throws Exception
	{
		// visual testing via Aplitools Eyes cloud
		String App_V1="https://github.com/cloudgrey-io/the-app/releases/download/v1.10.0/TheApp-VR-v1.apk";
		String App_V2="https://github.com/cloudgrey-io/the-app/releases/download/v1.10.0/TheApp-VR-v2.apk";
		By ECHO_SCREEN=MobileBy.AccessibilityId("Echo Box");
		By MSG_BOX=MobileBy.AccessibilityId("messageInput");
		By SAVE_BTN=MobileBy.AccessibilityId("messageSaveBtn");
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desiredcapabilities
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
		dc.setCapability("autoGrantPermissions","true");
		dc.setCapability("adbExecTimeout","50000");
		dc.setCapability("showGradleLog","true");
		dc.setCapability("app",App_V1);//decomment for the 1st time execution
		//dc.setCapability("app",App_V2);//decomment for the 2nd time execution
		dc.setCapability("uninstallOtherPackages","io.cloudgrey.the_app");
		//Launch app in device through appium server
		AndroidDriver driver;
		while(2>1)
		{
			try
			{
				driver=new AndroidDriver(u,dc);
				break;
			}
			catch(Exception ex)
			{
				
			}
		}
		//set up eyes SDK(Register to Aplitools and attatch eyes dependency code in pom.xml)
		try
		{	
			//Authenticate to cloud
			Eyes eyes=new Eyes();
			eyes.setLogHandler(new StdoutLogHandler());//To get logsin eclipse console
			eyes.setForceFullPageScreenshot(true);//to take screenshot of full screen of app
			eyes.setApiKey("X47GkPo5rleQknMSRfG4PPOUCcBmgliDJZRznRg9NFo110");
			//continue automation
			WebDriverWait w=new WebDriverWait(driver,10);
			w.until(ExpectedConditions.presenceOfElementLocated(ECHO_SCREEN));
			eyes.open(driver,"TheApp","Visual screen test");
			eyes.checkWindow();
			w.until(ExpectedConditions.presenceOfElementLocated(ECHO_SCREEN)).click();
			w.until(ExpectedConditions.presenceOfElementLocated(MSG_BOX)).sendKeys("kalam");
			driver.findElement(SAVE_BTN).click();
			eyes.checkWindow();
			eyes.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//close site
		driver.closeApp();
		//stop apium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");

	}

}
