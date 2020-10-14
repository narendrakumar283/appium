package bmw;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class ImageMatch
{

	public static void main(String[] args) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter phone number");
		String phno=sc.nextLine();
		// convert image to Base64 String
		File f=new File("D:\\batch249\\appiumexamples\\phone.png");
		Path path=f.toPath();
		String x=Base64.getEncoder().encodeToString(Files.readAllBytes(path));
		System.out.println(x);
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//Define desired Capabilites related to device and app
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","emulator-5554");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.1.0");
		dc.setCapability("appPackage","com.google.android.dialer");
		dc.setCapability("appActivity","com.google.android.dialer.extensions.GoogleDialtactsActivity");
		//launch app in device through appium server
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
		try
		{
			try
			{
				driver.findElement(By.xpath("//*[@content-desc='key pad']")).click();
			}
			catch(Exception ex)
			{
				driver.findElement(By.xpath("//*[@text='Add call']")).click();
			}
			Thread.sleep(5000);
			for(int i=0;i<phno.length();i++)
			{
				char d=phno.charAt(i);
				driver.findElement(By.xpath("//*[contains(@content-desc,'"+d+"')]")).click();
			}
			Thread.sleep(5000);
			//driver.findElement(By.xpath("//*[@content-desc='dial']")).click();
			//Thread.sleep(3000);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		try
		{
			Thread.sleep(5000);
			if(driver.findElementByImage(x).isDisplayed())
			{
				int xco=driver.findElementByImage(x).getLocation().getX();
				int yco=driver.findElementByImage(x).getLocation().getY();
				int w=driver.findElementByImage(x).getSize().getWidth();
				int h=driver.findElementByImage(x).getSize().getHeight();
				System.out.println(xco+" "+yco);
				System.out.println(w+" "+h);
				//automate operations on matched element using "TouchAction"class
				Thread.sleep(5000);
			}
		}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			//close app
			driver.closeApp();
			Thread.sleep(5000);
			//stop appium server
			Runtime.getRuntime().exec("taskkill /F /IM node.exe");
			Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}

}
