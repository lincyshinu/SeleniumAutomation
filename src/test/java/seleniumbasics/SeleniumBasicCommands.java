package seleniumbasics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumBasicCommands {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		//WebDriver driver = new FirefoxDriver();
       // WebDriver driver=new EdgeDriver();
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		String title=driver.getTitle();
		System.out.println(title);
		String url=driver.getCurrentUrl();
		System.out.println(url);
		//String pagesource=driver.getPageSource();
		//System.out.println(pagesource);
		driver.close();
		
		
	}
}
