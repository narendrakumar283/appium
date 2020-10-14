package bmw;

import java.net.URL;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Send_Data 
{

	public static void main(String[] args) throws Exception
	{
		// start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired capabilities related to device and VodQA app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
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
		try 
		{
		Thread.sleep(10000);
		MobileElement e=(MobileElement) driver.findElementByXPath("//*[@content-desc='password']");
		e.clear();//clear existing
		e.click();//put focus
		driver.hideKeyboard();
		Map<String,Object> input1=ImmutableMap.of("text","admin");
		driver.executeScript("mobile:type",input1);//send data
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@text='LOG IN']")).click();
		Thread.sleep(5000);
		MobileElement e1=(MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Demo Long press button\")");
		String eid1=e1.getId();
		MobileElement e2=(MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"Demos wheel picker color\")");
		String eid2=e2.getId();
		Map<String,Object> inputs2=ImmutableMap.of("elementId",eid2,"elementToId",eid1);
		driver.executeScript("mobile:scrollBackTo",inputs2);
		//here scrollBackTo cmd is not working

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
