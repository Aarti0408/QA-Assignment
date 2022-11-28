
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.stream.Collectors;

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

public class TestCase2 {

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
	public void selectAndVerifySortedPrice() throws InterruptedException {

		// Enter Min Price and Max Price and click on Go
		WebElement minPrice = driver.findElement(By.xpath("//input[@id='low-price']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", minPrice);

		minPrice.sendKeys("100");
		driver.findElement(By.xpath("//input[@id='high-price']")).sendKeys("200");
		driver.findElement(By.xpath("//*[@class=\"a-button-text\"]//parent::span/input")).click();

		WebElement Dropdown = driver.findElement(By.xpath("//*[@id='s-result-sort-select']"));
		Select Category = new Select(Dropdown);
		Category.selectByVisibleText("Price: High to Low");

		List<WebElement> searchresultsPrice = driver.findElements(
				By.xpath("//*[starts-with(@cel_widget_id,'MAIN-SEARCH_RESULTS')]//span[@class='a-price']"));

		List<String> ActualReceievedList = searchresultsPrice.stream().map(s -> s.getText())
				.collect(Collectors.toList());
		List<String> SortedList = ActualReceievedList.stream().sorted().collect(Collectors.toList());

		System.out.println("======================ActualReceievedList=======================");
		ActualReceievedList.forEach(a -> System.out.println(a));
		System.out.println("==================================================================");
		System.out.println("===================================SortedList=======================");
		SortedList.forEach(s -> System.out.println(s));

		/*
		 * System.out.println("======================ActualReceievedList "
		 * +ActualReceievedList);
		 * System.out.println("======================SortedList "+SortedList);
		 */

		Assert.assertTrue(ActualReceievedList.equals(SortedList));

	}


 @AfterClass public void closeBrowser(){ 
	 driver.quit();
 } 
 }
 
