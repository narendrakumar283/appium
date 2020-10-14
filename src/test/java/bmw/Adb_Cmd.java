package bmw;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;

public class Adb_Cmd
{

	public static void main(String[] args)throws Exception 
	{
		// start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium --relaxed-security\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired capabilities related to device and VodQA app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
		//dc.setCapability("autoGrantPermissions","true");
		//dc.setCapability("adbExecTimeout","50000");
		dc.setCapability("appPackage","com.google.android.apps.photos");
		dc.setCapability("appActivity","com.google.android.apps.photos.home.HomeActivity");
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
			By backupSwitch=By.id("com.google.android.apps.photos:id/auto_backup_switch");
			By touchOutside=By.id("com.google.android.apps.photos:id/touch_outside");
			By keepoff=By.xpath("//*[@text='KEEP OFF']");
			WebDriverWait w=new WebDriverWait(driver,10);
			w.until(ExpectedConditions.presenceOfElementLocated(backupSwitch)).click();
			w.until(ExpectedConditions.presenceOfElementLocated(touchOutside)).click();
			w.until(ExpectedConditions.presenceOfElementLocated( keepoff)).click();
			List<String> l=Arrays.asList("-rf","/mnt/sdcard/Pictures/*.*");
			Map<String,Object> cmd=ImmutableMap.of("command","rm","args",l);
			driver.executeScript("mobile:shell",cmd);
			Thread.sleep(10000);
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
