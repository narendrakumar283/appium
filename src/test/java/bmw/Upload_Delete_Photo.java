package bmw;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;


public class Upload_Delete_Photo 
{

	public static void main(String[] args) throws Exception
	{
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium --relaxed-security\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired capabilities related to device and VodQA app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
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
		//Automation
		try
		{
			Thread.sleep(10000);
			By backupSwitch=By.id("com.google.android.apps.photos:id/auto_backup_switch");
			By touchOutside=By.id("com.google.android.apps.photos:id/touch_outside");
			By keepoff=By.xpath("//*[@text='KEEP OFF']");
			By photo=By.xpath("//*[contains(@content-desc,'Photo taken')]");
			By trash=By.id("com.google.android.apps.photos:id/trash");
			By moveToTrash=By.xpath("//*[@text='MOVE TO TRASH']");
			By menu=By.xpath("//*[@content-desc='Show Navigation Drawer']");
			By settings=By.xpath("//*[@text='Settings']");
			By bo=By.xpath("//*[@text='Back up & sync']");
			By onoff=By.xpath("//*[@text='Off' or @text='On']");
			WebDriverWait wait=new WebDriverWait(driver,20);
			WebDriverWait shortWait=new WebDriverWait(driver,5);
		    wait.until(ExpectedConditions.presenceOfElementLocated(backupSwitch)).click();
		    wait.until(ExpectedConditions.presenceOfElementLocated(touchOutside)).click();
		    wait.until(ExpectedConditions.presenceOfElementLocated(keepoff)).click();
		    //delete all existing pictures via an infinite loop,which can terminate when no more pictures
		    try
		    {
		    	while(2>1)
		    	{
		    		shortWait.until(ExpectedConditions.presenceOfElementLocated(photo)).click();
		    		shortWait.until(ExpectedConditions.presenceOfElementLocated(trash)).click();
		    		shortWait.until(ExpectedConditions.presenceOfElementLocated(moveToTrash)).click();
		    	}
		    }
		    catch(Exception ignore)
		    {
		    	System.out.println("all existing pics deleted");
		    }
		    Thread.sleep(5000);

		File img=new File("D:\\batch249\\appiumexamples\\volume.png");
		driver.pushFile("/mnt/sdcard/Pictures/"+img.getName(),img);
		Thread.sleep(5000);
		ExpectedCondition condition=ExpectedConditions.numberOfElementsToBe(photo,1);
		wait.until(condition);
		System.out.println("Given pic uploaded successfully");
		wait.until(ExpectedConditions.presenceOfElementLocated(menu)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(settings)).click();
	    wait.until(ExpectedConditions.presenceOfElementLocated(bo)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(onoff)).click();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//close App
		driver.closeApp();
		//stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	   
	}

}
