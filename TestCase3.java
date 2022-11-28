
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCase3 {

	static WebDriver driver;
	static JavascriptExecutor jxe;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public void launchBrowser() {

		BasicConfigurator.configure();
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\exxxaar\\eclipse-workspace\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}

	@Test
	public void gotoHomePageandSearchByPrice() {

		//Open Amazon page
		driver.get("https://www.amazon.com");
		driver.navigate().refresh();
		/*
		 * //TO verify Amazon Home Page is displayed String homePage =
		 * driver.findElement(By.xpath("//*[@id='nav-global-location-slot']")).getText()
		 * ; System.out.println("==========Amazon Home Page============="+homePage);
		 * Assert.assertEquals(homePage,"Deliver to");
		 */
		//Select Category as Electronics
		WebElement Dropdown = driver.findElement(By.xpath("//*[@id='searchDropdownBox']"));
		Select Category = new Select(Dropdown);
		Category.selectByVisibleText("Electronics");

		//Type iphone14 in search bar
		driver.findElement(By.xpath(" //input[@name='field-keywords']")).sendKeys("Iphone 14");

		//click on Search Button
		driver.findElement(By.xpath(" //input[@id='nav-search-submit-button']")).click();

		//Enter Min Price and Max Price and click on Go
		WebElement minPrice = driver.findElement(By.xpath("//input[@id='low-price']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", minPrice);
		minPrice.sendKeys("100");
		driver.findElement(By.xpath("//input[@id='high-price']")).sendKeys("200");
		driver.findElement(By.xpath("//*[@class=\"a-button-text\"]//parent::span/input")).click();

	}
	
		@Test(dependsOnMethods = "gotoHomePageandSearchByPrice" )
		public void addItemsToCart() throws InterruptedException {
			
			List<WebElement> ItemsneedtoAddinCart = driver.findElements(By.xpath("//*[@data-component-type='s-product-image']"));
			
			int noOfItemsOnPage = ItemsneedtoAddinCart.size();
			System.out.println("==========NoOfItemsOnPage============="+noOfItemsOnPage);
			
			Random rand = new Random();
			ItemsneedtoAddinCart.get(rand.nextInt(ItemsneedtoAddinCart.size())).click();
	
			validateAddToCartButtonStatus(ItemsneedtoAddinCart,rand);
		}
			
		public void validateAddToCartButtonStatus(List<WebElement> ItemsneedtoAddinCart , Random rand) throws InterruptedException{
			
		//String newCartlabel;
		int newPrimeButtonElementssize;
		
		int PrimeButtonElementssize = driver.findElements(By.xpath("//*[@id='primeSavingsUpsell-signup-link']")).size();
		System.out.println("==========PrimeButton Status ============="+PrimeButtonElementssize);

				do {
				driver.navigate().back();
				List<WebElement> NewItemsneedtoAddinCart = driver.findElements(By.xpath("//*[@data-component-type='s-product-image']"));
				Thread.sleep(2000);
				NewItemsneedtoAddinCart.get(rand.nextInt(NewItemsneedtoAddinCart.size())).click();
				Thread.sleep(1000);
				newPrimeButtonElementssize = driver.findElements(By.xpath("//*[@id='primeSavingsUpsell-signup-link']")).size();
					System.out.println("==========newPrimeButtonElementssize Status ============="+newPrimeButtonElementssize);
				}while(newPrimeButtonElementssize>0);

				
		String Cartlabel =	driver.findElement(By.xpath(" //*[contains(@class,'a-icon-cart')]/following-sibling::input")).getAttribute("aria-labelledby");
		if (Cartlabel.contains("exportsUndeliverable-cart-announce")) {

			driver.findElement(By.xpath("//*[@id='contextualIngressPtPin']")).click();

			// Change Country
			WebElement Dropdown = driver.findElement(By.xpath("//*[@id='GLUXCountryList']"));
			Select Category = new Select(Dropdown);
			Category.selectByVisibleText("United Kingdom");
			driver.findElement(By.xpath("//*[@name='glowDoneButton']")).click();
		}
						
		WebElement CartButton = driver.findElement(By.xpath("//*[@id='add-to-cart-button']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", CartButton);

		CartButton.click();
					
}
	@Test(dependsOnMethods = "addItemsToCart")
		public void validateSignInPage() {
			 
			driver.findElement(By.xpath("//*[@name='proceedToRetailCheckout']")).click();
			String SignInPageText = driver.findElement(By.xpath("//*[@class='a-spacing-small']")).getText();
			System.out.println("==========SignInPageText============="+SignInPageText);
			Assert.assertEquals(SignInPageText,"Sign in");
		}
	@AfterClass 
	public void closeBrowser(){ driver.quit();
	} 
}	
		

