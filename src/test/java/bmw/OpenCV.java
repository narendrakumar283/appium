package bmw;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;

public class OpenCV 
{

	public static void main(String[] args) throws Exception
	{
		//visual testing via opencv
		String AppV1="D:\\batch249\\TheAppV1.apk";
		String appUpgrade="D:\\batch249\\TheAppV2.apk";
		String APPKG="io.cloudgrey.the_app";
		String APPACT="com.reactnativenavigation.controllers.NavigationActivity";
		String msg="Hello KALAM sir";
		By msgInput=MobileBy.AccessibilityId("messageInput");
		By echoBox=MobileBy.AccessibilityId("Echo Box");
		By savesmsg=MobileBy.AccessibilityId(msg);
		By savemsgbtn=MobileBy.AccessibilityId("messageSaveBtn");
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired capabilities related to device and app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
		dc.setCapability("autoGrantPermissions","true");
		dc.setCapability("adbExecTimeout","50000");
		dc.setCapability("app",AppV1);
		dc.setCapability("uninstallOtherPackages","io.cloudgrey.the_app");
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
		//create a folder for results
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		File resfolder=new File("VTResOn"+sf.format(dt));
		resfolder.mkdir();
		try
		{
			WebDriverWait w=new WebDriverWait(driver,20);
			w.until(ExpectedConditions.presenceOfElementLocated(echoBox));
			//Take screenshot of home screen in version1
			File hs=driver.getScreenshotAs(OutputType.FILE);
			w.until(ExpectedConditions.elementToBeClickable(echoBox)).click();
			w.until(ExpectedConditions.presenceOfElementLocated(msgInput)).sendKeys(msg);
			w.until(ExpectedConditions.presenceOfElementLocated(savemsgbtn)).click();
			w.until(ExpectedConditions.presenceOfElementLocated(savesmsg));
			//Take screenshot of saved message screen in version 1
			File ms=driver.getScreenshotAs(OutputType.FILE);
			Thread.sleep(10000);
			driver.installApp(appUpgrade);
			Thread.sleep(10000);
			Activity activity=new Activity(APPKG,APPACT);
			driver.startActivity(activity);
			w.until(ExpectedConditions.presenceOfElementLocated(echoBox));
			//Test the Home screen in version2 with version1
			doVisualCheck(driver,"homescreen",hs,resfolder);
			w.until(ExpectedConditions.elementToBeClickable(echoBox)).click();
			w.until(ExpectedConditions.presenceOfElementLocated(savesmsg));
			//Test the saved message screen in version2 with version 1
			doVisualCheck(driver,"msgscreen",ms,resfolder);
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

	public static void doVisualCheck(AndroidDriver driver, String checkName, File baseImg, File resfolder) throws Exception
	{
		SimilarityMatchingOptions opts=new SimilarityMatchingOptions();
		opts.withEnabledVisualization();
		File newImg=driver.getScreenshotAs(OutputType.FILE);
		SimilarityMatchingResult res=driver.getImagesSimilarity(baseImg,newImg);
		//if the similarity is not high enough,consider the check to have failed
		if(res.getScore()<1.0)
		{
			File difffile=new File(resfolder.getAbsolutePath()+"/Fail_"+checkName+".png");
			res.storeVisualization(difffile);
			System.out.println("Visual check of"+checkName+"failed; due to similarity match was only"+res.getScore()+
			",and below the threshold of 1.0 Visualization written to"+difffile.getAbsolutePath());
		}
		else
		{
			System.out.println(String.format("Visual check of '%s' passed;similarity match was %f",
					                                                              checkName,res.getScore()));
		}
	}

}
