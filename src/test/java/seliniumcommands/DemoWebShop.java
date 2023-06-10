package seliniumcommands;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoWebShop {
	WebDriver driver;

	public void testInitialise(String browser) {
		if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"D:\\Automation\\SeleniumCommands\\src\\test\\resources\\driverFiles\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equals("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("Edge")) {
			driver = new EdgeDriver();

		} else {
			try {
				throw new Exception("Invalid Browser");

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@BeforeMethod
	public void setup() {
		testInitialise("Chrome");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("./Screenshots/" + result.getName() + ".png"));
		}
		driver.close();
		driver.quit();
	}

	@Test
	public void tc_001_verifyObsquraTitle() {
		driver.get("https://selenium.obsqurazone.com/index.php");
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		String expectedTitle = "Obsqura Testing";
		Assert.assertEquals(actualTitle, expectedTitle, "Invalid title found");
	}

	@Test
	public void tc_002_verifyLogin() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		String email = "lincylal123@yopmail.com";
		WebElement loginmail = driver.findElement(By.xpath("//input[@id='Email']"));
		loginmail.sendKeys(email);
		WebElement pswd = driver.findElement(By.xpath("//input[@id='Password']"));
		pswd.sendKeys("lincylal");
		WebElement loginButton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		loginButton.click();
		WebElement account = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String act_email = account.getText();
		System.out.println(act_email);
		Assert.assertEquals(act_email, email, "invalid aacount");

	}

	@Test
	public void tc_003_verifyRegistration() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement registerLink = driver
				.findElement(By.xpath("//div[@class='header-links']//a[@class='ico-register']"));
		registerLink.click();
		List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
		selectGender("F", gender);
		WebElement firstName = driver.findElement(By.xpath("//input[@id='FirstName']"));
		firstName.sendKeys("Liji");
		WebElement lastName = driver.findElement(By.xpath("//input[@id='LastName']"));
		lastName.sendKeys("Lal");
		String email = "lincy12345@yopmail.com";
		WebElement emailField = driver.findElement(By.xpath("//input[@id='Email']"));
		emailField.sendKeys(email);
		WebElement pswd = driver.findElement(By.xpath("//input[@id='Password']"));
		pswd.sendKeys("lalliji");
		WebElement ConfirmPswd = driver.findElement(By.xpath("//input[@id='ConfirmPassword']"));
		ConfirmPswd.sendKeys("lalliji");
		WebElement RegisterButton = driver.findElement(By.xpath("//input[@id='register-button']"));
		RegisterButton.click();
		WebElement emailLink = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String act_email = emailLink.getText();
		System.out.println(act_email);
		Assert.assertEquals(act_email, email, "Invalid Email");
		String exp_msg = "Your registration completed";
		WebElement reg_msg = driver.findElement(By.xpath("//div[@class='page-body']//div[@class='result']"));
		String act_msg = reg_msg.getText();
		System.out.println(act_msg);
		Assert.assertEquals(act_msg, exp_msg, "Invalid Message");
		WebElement continueButton = driver.findElement(By.xpath("//input[@class='button-1 register-continue-button']"));
		continueButton.click();
	}

	public void selectGender(String gen, List<WebElement> gender) {
		for (int i = 0; i < gender.size(); i++) {
			String genderValue = gender.get(i).getAttribute("value");
			if (genderValue.equals(gen)) {
				gender.get(i).click();
			}
		}
	}

	@Test
	public void tc_004_verifyTitleFromExcelSheet() throws IOException {
		driver.get("https://demowebshop.tricentis.com/");
		String act_title=driver.getTitle();
		String XcelPath="\\src\\test\\resources\\TestData.xlsx";
		String sheetname="HomePage";
		String exp_title=ExcelUtility.readStringData(XcelPath, sheetname, 0, 1);
		Assert.assertEquals(act_title,exp_title, "Invalid");
		}
	@Test
	public void tc_004_verifyRegistrationFromExcelSheet() throws IOException {
		driver.get("https://demowebshop.tricentis.com/");
	
		String XcelPath="\\src\\test\\resources\\TestData.xlsx";
		String sheetname="RegisterPage";
		String exp_title=ExcelUtility.readStringData(XcelPath, sheetname, 0, 1);
		WebElement registerLink = driver
				.findElement(By.xpath("//div[@class='header-links']//a[@class='ico-register']"));
		registerLink.click();
		String act_title=driver.getTitle();
		Assert.assertEquals(act_title,exp_title, "Invalid");
		List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
		selectGender("F", gender);
		WebElement firstName = driver.findElement(By.xpath("//input[@id='FirstName']"));
		firstName.sendKeys(ExcelUtility.readStringData(XcelPath, sheetname, 1, 1));
		WebElement lastName = driver.findElement(By.xpath("//input[@id='LastName']"));
		lastName.sendKeys(ExcelUtility.readStringData(XcelPath, sheetname, 2, 1));
		String email = ExcelUtility.readStringData(XcelPath, sheetname, 3, 1);
		WebElement emailField = driver.findElement(By.xpath("//input[@id='Email']"));
		emailField.sendKeys(email);
		WebElement pswd = driver.findElement(By.xpath("//input[@id='Password']"));
		pswd.sendKeys(ExcelUtility.readStringData(XcelPath, sheetname, 4, 1));
		WebElement ConfirmPswd = driver.findElement(By.xpath("//input[@id='ConfirmPassword']"));
		ConfirmPswd.sendKeys(ExcelUtility.readStringData(XcelPath, sheetname, 5, 1));
		WebElement RegisterButton = driver.findElement(By.xpath("//input[@id='register-button']"));
		RegisterButton.click();
		WebElement emailLink = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String act_email = emailLink.getText();
		System.out.println(act_email);
		Assert.assertEquals(act_email,email,"Invalid");
		String exp_msg = "Your registration completed";
		WebElement reg_msg = driver.findElement(By.xpath("//div[@class='page-body']//div[@class='result']"));
		String act_msg = reg_msg.getText();
		System.out.println(act_msg);
		Assert.assertEquals(act_msg, exp_msg, "Invalid Message");
		WebElement continueButton = driver.findElement(By.xpath("//input[@class='button-1 register-continue-button']"));
		continueButton.click();
		
	}
	@Test(dataProvider="invalidcredentials")
	public void tc_005_verifyLoginWithInvalidData(String email,String password)
	{
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		
		WebElement loginmail = driver.findElement(By.xpath("//input[@id='Email']"));
		loginmail.sendKeys(email);
		WebElement pswd = driver.findElement(By.xpath("//input[@id='Password']"));
		pswd.sendKeys(password);
		WebElement loginButton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		loginButton.click();
		WebElement errorMsg = driver.findElement(By.xpath("//div[@class='validation-summary-errors']//span"));
		String act_email = errorMsg.getText();
		System.out.println(act_email);
		String exp_msg="Login was unsuccessful. Please correct the errors and try again.";
		Assert.assertEquals(act_email, exp_msg, "invalid aacount");
	}
@DataProvider(name="invalidcredentials")
public Object[][] userCredentials() {
	Object[][] data= {{"jibi123@yopmail.com","jibi1234"},{"jibi1234@yopmail.com","jibi123"},{"jibi1234@yopmail.com","jibi1234"}};
	return data;
}
@Test
public void tc_06_verifyRegistrationUsingRandomGenerator() {
	driver.get("https://demowebshop.tricentis.com/");
	WebElement registerLink = driver
			.findElement(By.xpath("//div[@class='header-links']//a[@class='ico-register']"));
	registerLink.click();
	String fName=RandomDataUtility.getfName();
	String lName=RandomDataUtility.getlName();
	String emailId=RandomDataUtility.getRandomEmail();
	String psword=fName+"@123";
	List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
	selectGender("F", gender);
	WebElement firstName = driver.findElement(By.xpath("//input[@id='FirstName']"));
	firstName.sendKeys(fName);
	WebElement lastName = driver.findElement(By.xpath("//input[@id='LastName']"));
	lastName.sendKeys(lName);
	
	WebElement emailField = driver.findElement(By.xpath("//input[@id='Email']"));
	emailField.sendKeys(emailId);
	WebElement pswd = driver.findElement(By.xpath("//input[@id='Password']"));
	pswd.sendKeys(psword);
	WebElement ConfirmPswd = driver.findElement(By.xpath("//input[@id='ConfirmPassword']"));
	ConfirmPswd.sendKeys(psword);
	WebElement RegisterButton = driver.findElement(By.xpath("//input[@id='register-button']"));
	RegisterButton.click();
	WebElement emailLink = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
	String act_email = emailLink.getText();
	System.out.println(act_email);
	Assert.assertEquals(act_email, emailId, "Invalid Email");
	
	}
@Test
public void tc_007_verifyloginWithParameters()
{
	driver.get("https://demowebshop.tricentis.com/");
}


}
