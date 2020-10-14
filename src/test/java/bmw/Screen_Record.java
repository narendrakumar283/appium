package bmw;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Screen_Record 
{

	public static void main(String[] args) throws Exception
	{
		//screen recorder
		//Start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired capabilities related to device and VodQA app
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
		
		try
		{
			Thread.sleep(10000);
			//start video recording
			AndroidStartScreenRecordingOptions asr=new AndroidStartScreenRecordingOptions();
			asr.withVideoSize("1280x720");
			asr.withTimeLimit(Duration.ofSeconds(200));
			driver.startRecordingScreen(asr);
			driver.setClipboardText("kalam");
			MobileElement e=(MobileElement) driver.findElement(By.xpath("//*[@content-desc='password']"));
			e.clear();
			Thread.sleep(5000);
			Dimension size=e.getSize();
			TouchAction ta=new TouchAction(driver);
			ta.longPress(ElementOption.element(e,size.width/2,size.height/2))
			             .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).perform();
			Thread.sleep(10000);
			//press on paste,but it is not considerable as an element
			PointOption p=new PointOption();
			PointOption loc=p.withCoordinates(e.getLocation().getX()+30,e.getLocation().getY()-30);
			ta.press(loc).release().perform();
			Thread.sleep(10000);
			//stop recording and save video file
			String videobase64string=driver.stopRecordingScreen();
			byte[] decode=Base64.getDecoder().decode(videobase64string);
			File f=new File("androidclip.mp4");
			FileUtils.writeByteArrayToFile(f, decode);
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
