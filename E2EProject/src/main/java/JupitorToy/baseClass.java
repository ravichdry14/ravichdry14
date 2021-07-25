package JupitorToy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class baseClass {

	//define WebDriver
	public WebDriver driver;
	
	//initilialize Properties variable
	public Properties prop = new Properties();
	
	//initilialize filestream object
	public FileInputStream fis ;
	
	//initialize webdriver
	public WebDriver initializeDriver() throws IOException {
		
		fis = new FileInputStream("C:\\Users\\ravic\\eclipse-workspace\\E2EProject\\src\\main\\java\\JupitorToy\\data.properties");
		
		prop.load(fis);
		//String browserName = prop.getProperty("browser");
				
		System.setProperty("webdriver.chrome.driver", "C:\\Ravi Data\\Selenium\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
				
				
		return driver;
	}
	
	//Method to Fetch URL
	public String getURL() throws IOException {
		fis = new FileInputStream("C:\\Users\\ravic\\eclipse-workspace\\E2EProject\\src\\main\\java\\JupitorToy\\data.properties");
		
		prop.load(fis);
		String URL = prop.getProperty("URL");
		
		return URL;
	}
}
