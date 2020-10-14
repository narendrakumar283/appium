package bmw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class App_Info 
{

	public static void main(String[] args) throws Exception
	{
	    //start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium\"");
		URL u=new URL("http://0.0.0.0:4723/wd/hub");
		//define desired capabilities
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","397c9174670d7ece");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","10");
		dc.setCapability("appPackage","com.vodqareactnative");
		dc.setCapability("appActivity","com.vodqareactnative.MainActivity");
		//launch app in device through appium server by creating driver object
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
			Thread.sleep(10000);
			//get details related to app
			System.out.println("about app info in to text file");
			Map<String,String> m1=driver.getAppStringMap();
			File f1=new File("appinfo.txt");
			FileWriter fw1=new FileWriter(f1);
			BufferedWriter bw1=new BufferedWriter(fw1);
			for(Map.Entry e:m1.entrySet())
			{
				bw1.write(e.getKey()+"="+e.getValue());
				bw1.newLine();
			}
			bw1.close();
			fw1.close();
			//get app session details in device
			System.out.println("about session info in to text file");
			Map<String,Object> m2=driver.getSessionDetails();
			File f2=new File("sessioninfo.txt");
			FileWriter fw2=new FileWriter(f2);
			BufferedWriter bw2=new BufferedWriter(fw2);
			for(Map.Entry e:m2.entrySet())
			{
				bw2.write(e.getKey()+"="+e.getValue().toString());
				bw2.newLine();
			}
			bw2.close();
			fw2.close();
			//get settings stored for this test session
			System.out.println("about settings info in to text file");
			Map<String,Object> m3=driver.getSettings();
			File f3=new File("settingdinfo.txt");
			FileWriter fw3=new FileWriter(f3);
			BufferedWriter bw3=new BufferedWriter(fw3);
			for(Map.Entry e:m3.entrySet())
			{
				bw3.write(e.getKey()+"="+e.getValue().toString());
				bw3.newLine();
			}
			bw3.close();
			fw3.close();
			//get performance types
			System.out.println("about performance types");
			List<String> l1=driver.getSupportedPerformanceDataTypes();
			for(String v:l1)
			{
				System.out.println(v);
			}
			//get specific performance type details
			for(int i=0;i<l1.size();i++)
			{
				System.out.println("about"+l1.get(i)+"into text file");
				File f=new File(l1.get(i)+".txt");
				FileWriter fw=new FileWriter(f);
				BufferedWriter bw=new BufferedWriter(fw);
				List<List<Object>> l2=driver.getPerformanceData("com.vodqareactnative",l1.get(i),1000);
				for(int j=0;j<l2.size();j++)
				{
					for(int k=0;k<l2.get(j).size();k++)
					{
						try
						{
							bw.write(l2.get(j).get(k).toString()+"\t");
						}
						catch(Exception ex)
						{
							bw.write("null\t");
						}
					}
					bw.newLine();
				}
				bw.close();
				fw.close();
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
		Runtime.getRuntime().exec("taskkill /f /IM cmd.exe");


	}

}
