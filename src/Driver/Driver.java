package Driver;




import java.util.HashMap;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



import java.io.BufferedInputStream;

import java.io.FileInputStream;




public class Driver {
	public WebDriver getDriver() {
		///

		FileInputStream fi=null;
		BufferedInputStream bi=null;
		
		FileInputStream fi2=null;
		BufferedInputStream bi2=null;
		
		
		String driverpathString="";
		String downloadpathString="";
		
		ChromeOptions options;
		
		//for driverlocation
		try {
			fi=new FileInputStream("driverlocation.txt");
			bi=new BufferedInputStream(fi);
			int r=0;
			while(true) 
					{
				
					r=bi.read();
					if(r==-1) {
						System.out.println(driverpathString);
						break;
					}
					driverpathString=driverpathString+((char)r);
			}
			bi.close();
			fi.close();
			}catch(Exception exception) {
				
			}
		
			//for downloadlocation
		
		try {
			fi2=new FileInputStream("downloadlocation.txt");
			bi2=new BufferedInputStream(fi2);
			int r=0;
			while(true) 
					{
				
					r=bi2.read();
					if(r==-1) {
						System.out.println(downloadpathString);
						break;
					}
					downloadpathString=downloadpathString+((char)r);
			}
			bi2.close();
			fi2.close();
			}catch(Exception exception) {
				
			}
			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory",(downloadpathString.trim()));
			options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			System.setProperty("webdriver.chrome.driver",(driverpathString.trim())+"\\chromedriver.exe");

		return new ChromeDriver(options);
	}
	
}
