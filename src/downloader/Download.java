package downloader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import Driver.Driver;
import globals.Global;


public class Download {
	//the class where ChromeDriver object returning function is
	Driver driverClassObj;
	//downloader Site
	String downloadsite="https://ytmp3.cc/en13/";
	
	
	//default playlist
	String playlistFormat="https://www.youtube.com/playlist?list=";
	String query_template = "https://www.youtube.com/results?search_query=";
	int timeToSleep=3000;//derive no. of loops total no. of videos
	
	String playlistId;	
	WebDriver browser;
	WebDriverWait wait;
	List <WebElement> urlSongs;
	public List <String> urls=new ArrayList<>();
	public Download() {
		driverClassObj=new Driver();
		
	}
	public String purifyLink(String link) {
		String tempPlaylistId;
		int equalsSignCount=0;
		
		for (int i = 0; i < link.length(); i++) {
		    if (link.charAt(i) == '=') {
		        equalsSignCount++;
		    }
		}
		
		if(equalsSignCount==2) {
			String splits[]=link.split("=");
			tempPlaylistId=splits[2];
		}else if(equalsSignCount==1){
			
			String splits[]=link.split("=");
			tempPlaylistId=splits[1];	
		}else {
			return link;
		}
		return tempPlaylistId;
	}
	
	public List<String> openPlayListSite(String playlistUrl){
		String playlist = playlistUrl;
		try {
		browser=driverClassObj.getDriver();
		}catch(IllegalStateException exception) {

			  JOptionPane.showMessageDialog(Global.jFrame,"Your ChromeDriver Location is Wrong","Alert",JOptionPane.WARNING_MESSAGE); 
	
		}catch (SessionNotCreatedException e) {
			  JOptionPane.showMessageDialog(Global.jFrame,"Your ChromeDriver needs to be updated","Alert",JOptionPane.WARNING_MESSAGE); 
		}
		wait=new WebDriverWait(browser,Duration.ofSeconds(10));
		
		
		playlistId=purifyLink(playlist);
		
		String completePlaylist=playlistFormat+playlistId;
		browser.get(completePlaylist);
		
		
		int totalVideos = 0;
		
		WebElement htmlElement,nVideosElement;
		List<WebElement> textElements;
		
		List<String> songs=new ArrayList<>();
		nVideosElement=browser.findElement(By.xpath("//*[@id=\"stats\"]/yt-formatted-string[1]"));
		try {

			totalVideos=Integer.parseInt((((nVideosElement.getAttribute("innerHTML"))).split(" "))[0]);
			
		}catch (Exception e) {
			//when total no. of videos has a comma ","
			String temp1[]=(nVideosElement.getAttribute("innerHTML")).split(" ");
			String temp2[]=temp1[0].split(","); //can handle 999,999 songs
			String temp3=temp2[0]+temp2[1];
			totalVideos=Integer.parseInt(temp3);
			
		}

		while(true) {
			textElements=browser.findElements(By.xpath("//*[@id=\"video-title\"]"));
			urlSongs=browser.findElements(By.xpath("//*[@id=\"content\"]/a"));
			
			if(textElements.size()==totalVideos) {
				
				break;
			}
			htmlElement=browser.findElement(By.tagName("html"));
			htmlElement.sendKeys(Keys.END);
			System.out.println("Parsing....");
		}
		for(WebElement urlSong:urlSongs) {
			urls.add(((urlSong.getAttribute("href")).split("&list"))[0]);
		}
		for(WebElement textElement:textElements) {
			songs.add((textElement.getAttribute("innerHTML")).trim());
		}
		
		return songs;
	}
	
	public void singleSong(String song) {
		//later uncomment this
		//closing 2nd tab if it exists.
		java.util.Set<String> winHandles = browser.getWindowHandles();
		List<String> windowsList = new ArrayList<String>();
		windowsList.addAll(winHandles);
		try {
			 browser.switchTo().window(windowsList.get(1));
			 browser.close();
		}catch(Exception exception){
			System.out.println("this time no AD");
		}
		//selecting first tab
		
		browser.switchTo().window(windowsList.get(0));
		WebElement inputElement,downloadElement;
		browser.get(downloadsite);
		inputElement=browser.findElement(By.xpath("//*[@id=\"input\"]"));
		inputElement.sendKeys(song);
		inputElement.sendKeys(Keys.ENTER);
		WebDriverWait wait=new WebDriverWait(browser,Duration.ofSeconds(15));
		
		try {
			downloadElement=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"buttons\"]/a[1]")));
			downloadElement.click();
			}catch (Exception e) {
				System.out.println("Download Button didnt load");
			}
		
		
	}
}
