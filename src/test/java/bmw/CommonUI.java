package bmw;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonUI
{

	public static void main(String[] args) throws Exception
	{
		//get environment
		Scanner sc=new Scanner(System.in);
		System.out.println("enter environment");
		String en=sc.nextLine();
		RemoteWebDriver driver;
		//specific code as per environment
		if(en.equalsIgnoreCase("mobile"))
		{
			//start appium server to use given "chromedriver" as per version of "chrome" in device
			Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium --chromedriver-executable D:\\batch249\\chromedriver_win32\\chromedriver.exe\"");
			URL u=new URL("http://0.0.0.0:4723/wd/hub");
			//Define desired capabilities related to android device and browser
			DesiredCapabilities dc=new DesiredCapabilities();
			dc.setCapability(CapabilityType.BROWSER_NAME,"chrome");
			dc.setCapability("deviceName","397c9174670d7ece");
			dc.setCapability("platformName","android");
			dc.setCapability("platformVersion","10");
		
		//launch browser in device through appium server by creating driver object
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
	  }
		else
		{
			System.out.println("enter browser name");
			String bn=sc.nextLine();
			//open browser in computer by creating driver object
			if(bn.equalsIgnoreCase("chrome"))
			{
				WebDriverManager.chromedriver().setup();
				driver=new ChromeDriver();
			}
			else if(bn.equalsIgnoreCase("firefox"))
			{
				WebDriverManager.firefoxdriver().setup();
				driver=new FirefoxDriver();
			}
			else if(bn.equalsIgnoreCase("edge"))
			{
				WebDriverManager.edgedriver().setup();
				driver=new EdgeDriver();
			}
			else if(bn.equalsIgnoreCase("opera"))
			{
				WebDriverManager.operadriver().setup();
				driver=new OperaDriver();
			}
			else
			{
				WebDriverManager.iedriver().setup();
				driver=new InternetExplorerDriver();
			}
			driver.manage().window().maximize();
			Thread.sleep(5000);
		}
		//common code for both environments
		//use xpath(),cssSelector().linkText(),partialLinkText(),className(),id(),tagName()
		try
		{ 
			Thread.sleep(10000);
			//Launch site
			driver.get("http://newtours.demoaut.com");
			Thread.sleep(5000);
			//click register link
			driver.findElementByPartialLinkText("REGISTER").click();
			Thread.sleep(5000);
			//Fill fields
			driver.findElement(By.cssSelector("input[name='firstName']")).sendKeys("abdul");
			driver.findElement(By.cssSelector("input[name='lastName']")).sendKeys("kalam");
			driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("8106702540");
			driver.findElement(By.cssSelector("input[name='userName']")).sendKeys("apj@abdulkalam");
			driver.findElement(By.cssSelector("input[name='address1']")).sendKeys("dno:11,mosqestreet");
			driver.findElement(By.cssSelector("input[name='address2']")).sendKeys("dhanushkoti road");
			driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Rameshwaram");
			driver.findElement(By.cssSelector("input[name='state']")).sendKeys("Tamilnadu");
			driver.findElement(By.cssSelector("input[name='postalCode']")).sendKeys("645782");
			//drop-down
			WebElement e=driver.findElement(By.xpath("//*[@name='country']"));
			Select s=new Select(e);
			s.selectByVisibleText("INDIA");
			//Fill remaining fields 
			driver.findElement(By.cssSelector("input[name='email']")).sendKeys("kalam@gmail.com");
			driver.findElement(By.cssSelector("input[name='password']")).sendKeys("batch249");
			driver.findElement(By.cssSelector("input[name='confirmPassword']")).sendKeys("batch249");
			//submit data to server
			driver.findElement(By.cssSelector("input[name='register']")).click();
			//close site
			driver.close();			
		}
		catch(Exception ex)
		{
			SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
			Date dt=new Date();
			File src=driver.getScreenshotAs(OutputType.FILE);
			String fname=sf.format(dt)+".png";
			File dest=new File(fname);
			FileHandler.copy(src,dest);
			System.out.println(ex.getMessage());
		}
		//stop appium server for mobile environment
		if(en.equalsIgnoreCase("mobile"))
		{
			Runtime.getRuntime().exec("taskkill /F /IM node.exe");
			Runtime.getRuntime().exec("taskkill /f /IM cmd.exe");
		}

	}

}
