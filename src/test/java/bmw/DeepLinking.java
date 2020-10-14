package bmw;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class DeepLinking 
{

	public static void main(String[] args) throws Exception
	{
		//deep linking
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
		dc.setCapability("app","https://github.com/cloudgrey-io/the-app/releases/download/v1.2.1/TheApp-v1.2.1.apk");
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
		try
		{
			Thread.sleep(10000);
			By echoBox=MobileBy.AccessibilityId("Echo Box");
			By logout=MobileBy.xpath("//*[@text='Logout']");
			WebDriverWait w=new WebDriverWait(driver,10);
			w.until(ExpectedConditions.presenceOfElementLocated(echoBox));
			//logout to app via deeplinking
			try
			{
				driver.get("theapp://login/alice/mypassword"); //usually return exception
			}
			catch(Exception ex)
			{
				System.out.println("Done");
			}
			//validation
			try
			{
				w.until(ExpectedConditions.presenceOfElementLocated(logout));
				System.out.println("Logout test passed via deep linking");
			}
			catch(Exception exe)
			{
				System.out.println("Logout test failed via deep linking");
			}
			Thread.sleep(10000);
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//close app
		driver.closeApp();
		//stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");

	}

}
