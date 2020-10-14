package bmw;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class TouchAction_Horizontal 
{

	public static void main(String[] args) throws Exception
	{
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desiredcapabilities related to device and vodQA app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
		dc.setCapability("appPackage","com.vodqareactnative");
		dc.setCapability("appActivity","com.vodqareactnative.MainActivity");
		//Launch app in device through appium server by creating driver object
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
		//using "TouchAction"class methods
		try
		{
			WebDriverWait wait=new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='LOG IN']"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("slider1"))).click();
			Thread.sleep(3000);
			MobileElement s=(MobileElement) driver.findElementByAccessibilityId("slider1");
			Dimension size=s.getSize();
			TouchAction ta=new TouchAction(driver);
			//left to right
			ta.press(ElementOption.element(s,0,size.height/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			                               .moveTo(ElementOption.element(s,size.width-10,size.height/2)).release().perform();
			
			Thread.sleep(10000);
			//right to left
			ta.press(ElementOption.element(s,size.width-10,size.height/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(ElementOption.element(s,0,size.height/2)).release().perform();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//closeApp
		driver.closeApp();
		//stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		
	}

}
