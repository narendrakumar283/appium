package bmw;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.serverevents.CustomEvent;

public class Logs_Print
{

	public static void main(String[] args) throws Exception
	{
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//define desired capabilities
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
		dc.setCapability("appPackage","com.vodqareactnative");
		dc.setCapability("appActivity","com.vodqareactnative.MainActivity");
		//launch app in device through appium server by creating driver object
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
		//Test automation
		CustomEvent evt=new CustomEvent();
		evt.setVendor("vodQA");
		try
		{
			Thread.sleep(10000);
			Activity ac=new Activity("com.soumyasethy.smstoast","com.soumyasethy.smstoast.SMSToast");
			driver.startActivity(ac);
			Thread.sleep(5000);
			driver.launchApp();
		}
		catch(Exception ex)
		{
			evt.setEventName(ex.getMessage());
		}
		//close app
		driver.closeApp();
		//stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		
	}

}
