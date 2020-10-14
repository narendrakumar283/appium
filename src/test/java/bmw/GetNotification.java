package bmw;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class GetNotification 
{

	public static void main(String[] args) throws Exception
	{
		// start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired capabilities related to device and VodQA app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","397c9174670d7ece");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","10");
		dc.setCapability("autoGrantPermissions","true");
		dc.setCapability("adbExecTimeout","50000");
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
		Thread.sleep(10000);
		try
		{
			System.out.println(driver.executeScript("mobile:getNotifications"));
			Map<String,Object> resmap=(Map<String, Object>) driver.executeScript("mobile:getNotifications");
			List<Map<String, Object>> mapslist=(List<Map<String, Object>>)resmap.get("statusBarNotifications");
			for(Map<String,Object> eachmap:mapslist)
			{
				Map<String, Object> ncontent=(Map<String, Object>) eachmap.get("notification");
				//display title
				if(ncontent.get("bigTitle")!=null)
				{
					System.out.println(ncontent.get("bigTitle"));
				}
				else
				{
					System.out.println(ncontent.get("title"));
				}
				//display text
				if(ncontent.get("bigText")!=null)
				{
					System.out.println(ncontent.get("bigText"));
				}
				else
				{
					System.out.println(ncontent.get("text"));
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//close app
		driver.quit();
		//stop appium server
	    Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");


	}

}
