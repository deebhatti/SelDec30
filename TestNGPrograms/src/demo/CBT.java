package demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CBT {

	WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public void openBrowser(String browser) {
		if (browser.equals("Chrome")) {
			driver = new ChromeDriver();
		}
		
		//Adding a comment in line number 28

		else if (browser.equals("Internet Explorer")) {
			System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		else if (browser.equals("Edge")) {
			System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}

		else if (browser.equals("Firefox")) {
			System.setProperty("webdriver.firefox.marionette", "geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.linkedin.com/");
	}

	@Test(priority = 0)
	public void verifyTitle() {
		String expectedTitle = "LinkedIn: Log In or Sign Up";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(priority = 1)
	public void verifySignInBtnVisibility() {
		boolean signInBtnStatus = driver.findElement(By.id("login-submit")).isDisplayed();
		Assert.assertTrue(signInBtnStatus);
	}

	@Test(priority = 2, enabled = false)
	public void verifyText() {
		String expectedText = "Be great at what you do";
		String actualText = driver.findElement(By.cssSelector("#regForm > h2")).getText();
		Assert.assertEquals(actualText, expectedText);
	}

	@Test(priority = 3)
	public void verifySearchBtnBeforeText() {
		boolean searchBtnStatus = driver.findElement(By.name("search")).isEnabled();
		Assert.assertFalse(searchBtnStatus);
	}

	@Test(priority = 4)
	public void verifySearchBtnAfterText() {
		driver.findElement(By.name("first")).sendKeys("Some Text");
		boolean searchBtnStatus = driver.findElement(By.name("search")).isEnabled();
		Assert.assertTrue(searchBtnStatus);
	}

	@AfterTest
	public void closeBrowser() {
		driver.close();
	}

}
