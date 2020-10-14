package bmw;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class Read_Sms 
{

	public static void main(String[] args)throws Exception
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
		//dc.setCapability("autoGrantPermissions","true");
		//dc.setCapability("adbExecTimeout","50000");
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
			Map<String,Object> res=(Map<String, Object>) driver.executeScript("mobile:listSms");
			long l=(long) res.get("total");
			List<Map<String,Object>> msgs=(List<Map<String,Object>>)res.get("items");
			//get and display latest SMS details
			System.out.println(msgs.get(0).get("address"));
			System.out.println(msgs.get(0).get("date"));
			System.out.println(msgs.get(0).get("subject"));
			System.out.println(msgs.get(0).get("body"));
			//display all SMS details
			for(Map<String,Object> m:msgs)
			{
				System.out.println(m.get("address"));
				System.out.println(m.get("date"));
				System.out.println(m.get("subject"));
				System.out.println(m.get("body"));
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
