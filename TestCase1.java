
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCase1 {

	// public static void main(String[] args) {
	// TODO Auto-generated method stub
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
	public void gotoHomePageandSearch() {

		// Open Amazon page
		driver.get(" https://www.amazon.com");
		driver.navigate().refresh();

		// Select Category as Electronics
		WebElement Dropdown = driver.findElement(By.xpath("//*[@id='searchDropdownBox']"));
		Select Category = new Select(Dropdown);
		Category.selectByVisibleText("Electronics");

		// Type Iphone 14 in search bar
		driver.findElement(By.xpath(" //input[@name='field-keywords']")).sendKeys("Iphone 14");

		// click on Search Button
		driver.findElement(By.xpath(" //input[@id='nav-search-submit-button']")).click();

	}

	@Test(dependsOnMethods = "gotoHomePageandSearch")
	public void selectBrandandVerifyPrice() throws InterruptedException {

		// Select Featured Brand
		WebElement ElementBrand = driver.findElement(By.xpath("//*[@aria-label='ZAGG']//div"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ElementBrand);
		Thread.sleep(3000);
		ElementBrand.click();

		// Enter Min Price and Max Price and click on Go
		driver.findElement(By.xpath("//input[@id='low-price']")).sendKeys("100");
		driver.findElement(By.xpath("//input[@id='high-price']")).sendKeys("2000");
		driver.findElement(By.xpath("//*[@class=\"a-button-text\"]//parent::span/input")).click();

		// Capture all web elements in to List

		jxe = (JavascriptExecutor) driver;
		jxe.executeScript("window.scrollBy(0,400)");

		List<WebElement> searchresultsPrice = driver.findElements(
				By.xpath("//*[starts-with(@cel_widget_id,'MAIN-SEARCH_RESULTS')]//span[@class='a-price']"));
		System.out.println("===========================================");
		System.out.println("No of Item Found");
		System.out.println("==========" + searchresultsPrice.size() + "===========");
	
		List<Integer> PriceWithinRange = new ArrayList<>();
		List<Integer> PriceWithoutRange = new ArrayList<>();
		for (int i = 0; i < searchresultsPrice.size(); i++) {

			String Price = searchresultsPrice.get(i).getText();

			System.out.println("===========================================");
			System.out.println("Price of Price " + i + "item is: " + Price);

			String finalPrice1 = Price.substring(1, 4);

			
			System.out.println("Price of " + i + 1 + " item is : " + finalPrice1);
			

			int pricereceived = Integer.parseInt(finalPrice1);
		
			

		
			if (pricereceived >= 100 && pricereceived <= 2000) {
		
				PriceWithinRange.add(pricereceived);

			
				System.out.println("Items Price within Range" + PriceWithinRange);
			

			} else {
				
				PriceWithoutRange.add(pricereceived);
				
				System.out.println("Items Price Out of Range" + PriceWithoutRange);
			

			}
			

			if (PriceWithoutRange.isEmpty()) {

				System.out.println("===========================================");
				System.out.println("Items Selected with in Selected Price Range");

			}

		}

	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
