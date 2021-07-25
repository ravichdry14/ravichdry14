package JupitorToy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCase extends baseClass {
	
	List<Toy> toys;
	//WebDriverWait wait = new WebDriverWait(driver, 30);
	
	///********************Launch Application ***************************
	@BeforeTest
	public void LaunchApplication() throws IOException {

		driver = initializeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//Maximize Browser
		driver.manage().window().maximize();

		//Navigate to URL
		String URL = getURL();
		driver.get(URL);


	}

	
	///******************** Test Case 1 ***************************
	@Test
	public void TestCase1() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		//Navigate to Contact Page
		driver.findElement(By.cssSelector("li#nav-contact")).click();

		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.btn-contact.btn.btn-primary")));
		driver.findElement(By.cssSelector("a.btn-contact.btn.btn-primary")).click();
		String Expected_Message = "we appreciate your feedback";

		String errMsg = driver.findElement(By.cssSelector("div[class*='alert alert']")).getText();

		while (!errMsg.contains(Expected_Message)) {
			System.out.println("Field not filled Successfully");

			driver.findElement(By.id("forename")).sendKeys("Forename");

			driver.findElement(By.id("email")).sendKeys("Test123@gmail.com");

			driver.findElement(By.id("message")).sendKeys("See you Soon");

			driver.findElement(By.cssSelector("a.btn-contact.btn.btn-primary")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));

			errMsg = driver.findElement(By.cssSelector("div[class*='alert alert']")).getText();	
		}

		if (errMsg.contains(Expected_Message)) {
			System.out.println("Form Submitted Successfully");
		}

		//go to homepage
		driver.findElement(By.cssSelector("a[class='brand']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='btn btn-success btn-large']")));
		
		System.out.println("**************** End of Test 1 *******************");
	}


	///******************** Test Case 2 ***************************

	@Test(dataProvider="getData")
	public void TestCase2(String foreName, String email, String message) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.findElement(By.cssSelector("li#nav-contact")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.btn-contact.btn.btn-primary")));

		String Expected_Message = "we appreciate your feedback";

		//Enter ForeName
		driver.findElement(By.id("forename")).sendKeys(foreName);

		//Enter Email
		driver.findElement(By.id("email")).sendKeys(email);

		//Enter Message
		driver.findElement(By.id("message")).sendKeys(message);


		Thread.sleep(2000);
		driver.findElement(By.cssSelector("a.btn-contact.btn.btn-primary")).click();

		//wait.until

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));

		String errMsg = driver.findElement(By.cssSelector("div[class*='alert alert']")).getText();

		if (errMsg.contains(Expected_Message)) {
			System.out.println("Form Submitted Successfully for :" + foreName);
		}

		//go to homepage
		driver.findElement(By.cssSelector("a[class='brand']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='btn btn-success btn-large']")));
	
	}
	

	///******************** Test Case 3  ***************************
	
	@Test
	public void TestCase3 () {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		//Click Start Shopping
		//if (counter==0) {
			driver.findElement(By.cssSelector("a[class='btn btn-success btn-large']")).click();
		//}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[id*='product-']")));

		AddToy ("Funny Cow",2);
		AddToy ("Fluffy Bunny",1);

		//Click on Cart
		driver.findElement(By.id("nav-cart")).click();

		//Verify Cart Item
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped cart-items'] tr")));

		Assert.assertTrue(ValidateItemCountInCart ("Funny Cow",2));
		Assert.assertTrue(ValidateItemCountInCart ("Fluffy Bunny",1));
		System.out.println("**************** End of Test 3 *******************");
		
		//Empty Cart for Test Case 4
		driver.findElement(By.cssSelector("a.btn.btn-danger.ng-scope")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.popup.modal.hide.ng-scope.in")));
		
		driver.findElement(By.cssSelector("div.popup.modal.hide.ng-scope.in div a.btn.btn-success")).click();
		//int counter=0;
	}

	 
	///******************** Test Case _ Validation ***************************


	public boolean ValidateItemCountInCart (String toyName, int toyCount) {

		List<WebElement> rows = driver.findElements(By.cssSelector("[class='table table-striped cart-items'] tr[class='cart-item ng-scope']"));
		int count = rows.size();
		boolean itemExists= false;
		String itemBeforeXpath="//tr[@class='cart-item ng-scope'][";
		String itemAfterXpath = "]/td[1]";

		for (int i =1; i<=count; i++) {
			String itemActualXpath = itemBeforeXpath + i + itemAfterXpath;
			String itemName = driver.findElement(By.xpath(itemActualXpath)).getText();
			if (itemName.contains(toyName)) {

				String xpathForQuantity = itemBeforeXpath+ i + "]/td[3]/input";

				int itemCnt = Integer.parseInt(driver.findElement(By.xpath(xpathForQuantity)).getAttribute("value"));
				//String itemCount = driver.findElement(By.xpath("//td[contains(text(),toyName)]/following-sibling::td[2]")).getAttribute("value");
				System.out.println("Item: " + itemName +" added to cart with Quantity " + itemCnt);
				//Assert.assertEquals(false, false);
				if (itemCnt == toyCount) {
					itemExists= true;
				}
			}
		}
		return itemExists;

	}


	// ************************ Test Case 4 ***************************************

	@Test()
	public void TestCase4 () {

		System.out.println("**************** Start of Test 4 *******************");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		//go to homepage
				driver.findElement(By.cssSelector("a[class='brand']")).click();

		//Click Start Shopping
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='btn btn-success btn-large']")));
			driver.findElement(By.cssSelector("a[class='btn btn-success btn-large']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[id*='product-']")));
		
		AddAllItemToList();
		
		//Add toys

		AddToy ("Stuffed Frog",2);
		AddToy ("Fluffy Bunny",5);
		AddToy ("Valentine Bear",3);		

		//Verify Price of Each Product -- NNED TO TAKE

		//Click on Cart
		driver.findElement(By.id("nav-cart")).click();
		
		Double actualTotal = ValidateAmountinCart();


		//Get total Price
		String Total = driver.findElement(By.cssSelector("strong.total.ng-binding")).getText();


		Assert.assertEquals(Total, "Total: " +actualTotal);
		System.out.println("Total Price : " + Total + " matched with Actual - Total: " + actualTotal);
		System.out.println("**************** End of Test 4 *******************");

	}


	//Method to Add toys 

	public void AddToy(String toyName, int toyCount) {
		//Take list of all Products
		List<WebElement> products = driver.findElements(By.cssSelector("h4.product-title.ng-binding"));

		for (int i=0; i<products.size(); i++) {

			String productName = products.get(i).getText();

			if (productName.contains(toyName)) {
				//counter =1;

				for (int itemCount=1; itemCount<=toyCount; itemCount++) {
					driver.findElements(By.cssSelector("a.btn.btn-success")).get(i).click();

				}
				break;
			}
		}
	}
	
	
	public void AddAllItemToList()
	{
		
		toys = new ArrayList<Toy>();
		List<WebElement> products = driver.findElements(By.cssSelector("h4.product-title.ng-binding"));

		for (int i=0; i<products.size(); i++) {

			String productName = products.get(i).getText();
			String productPrice;
			productPrice = driver.findElements(By.xpath("//span[@class='product-price ng-binding']")).get(i).getText();
			toys.add(new Toy(productName,productPrice));	
		}	
	}
	
	public String getPricebyItemName(String productName) {
		String itemprice="0";
		for (int i=0;i<toys.size();i++) {
			if(toys.get(i).toyName.equals(productName)) {
				itemprice=toys.get(i).toyPrice;
				itemprice = itemprice.substring(1,itemprice.length());
				break;
			}
			
		}
		return itemprice;
	}




	///********************Data Provider for Test Case 2 ***************************
	@DataProvider
	public Object[][] getData() {
		Object[][] data = new Object[5][3];

		//set1
		data[0][0] = "ForeName1";
		data[0][1] = "Email1@planit.net.au";
		data[0][2] = "Message1";

		//set2
		data[1][0] = "ForeName2";
		data[1][1] = "Email2@planit.net.au";
		data[1][2] = "Message2";

		//set3
		data[2][0] = "ForeName3";
		data[2][1] = "Email3@planit.net.au";
		data[2][2] = "Message3";

		//set4
		data[3][0] = "ForeName4";
		data[3][1] = "Email4@planit.net.au";
		data[3][2] = "Message4";

		//set5
		data[4][0] = "ForeName5";
		data[4][1] = "Email5@planit.net.au";
		data[4][2] = "Message5";

		return data;
	}

	///********************Close Application ***************************
	@AfterTest
	public void CloseApplication() throws IOException {

		//driver = initializeDriver();
		//Close Browser
		driver.close();


	}

	
	public Double ValidateAmountinCart () {

		List<WebElement> rows = driver.findElements(By.cssSelector("[class='table table-striped cart-items'] tr[class='cart-item ng-scope']"));
		int count = rows.size();
		Double itemTotal = 0.00;
		String itemBeforeXpath="//tr[@class='cart-item ng-scope'][";
		String itemAfterXpath = "]/td[1]";

		for (int i =1; i<=count; i++) {
			String itemActualXpath = itemBeforeXpath + i + itemAfterXpath;
			String itemName = driver.findElement(By.xpath(itemActualXpath)).getText();
			

				String xpathForQuantity = itemBeforeXpath+ i + "]/td[3]/input";
				String xpathForPrice = itemBeforeXpath + i + "]/td[2]";
				String xpathForSubTotal = itemBeforeXpath + i + "]/td[4]";
				
				//get Price
				String price = 	driver.findElement(By.xpath(xpathForPrice)).getText();	
				Double actualPrice = Double.parseDouble(price.substring(1,price.length()));
				
				
				//get quantity
				int itemCnt = Integer.parseInt(driver.findElement(By.xpath(xpathForQuantity)).getAttribute("value"));

				//get SubTotal
				String subTotal = 	driver.findElement(By.xpath(xpathForSubTotal)).getText();
				Double actualSubTotal = Double.parseDouble(subTotal.substring(1,subTotal.length()));
				
				Double itemCartPrice =Double.parseDouble(getPricebyItemName(itemName)) ;
				
				Double expectedSubTotal = itemCartPrice * itemCnt;
								
				Assert.assertEquals(actualPrice, itemCartPrice, "Unit price not matching : "+ itemName);
				
				Assert.assertEquals(actualSubTotal, expectedSubTotal, "Expected sub total not matching for item :"+ itemName);
				
				itemTotal = itemTotal + expectedSubTotal;
				
			
		}
		return itemTotal;


	}

}
