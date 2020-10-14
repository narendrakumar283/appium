package bmw;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class ViewTag_Esspresso 
{

	public static void main(String[] args)throws Exception
	{
		//Espresso only
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired capabilites related to device and app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("automationName","ESPRESSO");
		dc.setCapability("forceEspressRebuild","true");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
		dc.setCapability("appPackage","com.vodqareactnative");
		dc.setCapability("appActivity","com.vodqareactnative.MainActivity");
		//launch app in device through appium server by creating object
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
		//common Test automation code for both drivers
		try
		{
			Thread.sleep(5000);
			driver.findElementByXPath("//*[@text='LOG IN']").click();
			Thread.sleep(5000);
			//locate visible views(ScrollView,ListView,GridView,TextView...etc)
			driver.findElementByAndroidViewTag("Slide your number").click();
			Thread.sleep(5000);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			Thread.sleep(5000);
		}
		//close app
		driver.quit();
		//stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");

	}

}
