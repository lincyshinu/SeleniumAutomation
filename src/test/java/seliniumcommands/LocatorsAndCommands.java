package seliniumcommands;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocatorsAndCommands {
	WebDriver driver;

	public void testInitialise(String browser) {
		if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\Automation\\SeleniumCommands\\src\\test\\resources\\driverFiles\\chromedriver.exe");
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
		// driver.close();
		 driver.quit();
	}

	@Test
	public void tc_001_verifyObsquraTitle() {                   
		driver.get("https://selenium.obsqurazone.com/index.php");
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		String expectedTitle = "Obsqura Testing1";
		Assert.assertEquals(actualTitle, expectedTitle, "Invalid title found");
	}

	@Test
	public void tc_002_verifySingleInputfieldMessage() {
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		WebElement messageField = driver.findElement(By.id("single-input-field"));
		messageField.sendKeys("Welcome");
		WebElement showbutton = driver.findElement(By.id("button-one"));
		showbutton.click();
		WebElement message = driver.findElement(By.id("message-one"));
		String actMessageText = message.getText();
		String expMessage = "Your Message : Welcome";
		Assert.assertEquals(actMessageText, expMessage, "Invalid Message");

	}

	@Test
	public void tc_003_verifyTwoInputfieldMessage() {
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		WebElement value1 = driver.findElement(By.id("value-a"));
		value1.sendKeys("10");
		WebElement value2 = driver.findElement(By.id("value-b"));
		value2.sendKeys("3");
		WebElement button2 = driver.findElement(By.id("button-two"));
		button2.click();
		WebElement message2 = driver.findElement(By.id("message-two"));
		String actvalue = message2.getText();
		String expvalue = "Total A + B : 13";
		Assert.assertEquals(actvalue, expvalue, "Invalid Result");

	}

	@Test
	public void tc_004_verifyEmptyFieldValidation() {

		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		String expectedFnameMsg = "Please enter First name.";
		String expectedLnameMsg = "Please enter Last name.";
		String expectedUnameMsg = "Please choose a username.";
		String expectedCnameMsg = "Please provide a valid city.";
		String expectedSnameMsg = "Please provide a valid state.";
		String expectedZnameMsg = "Please provide a valid zip.";

		WebElement submitButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		submitButton.click();
		WebElement firstNameValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom01']/following-sibling::div[1]"));
		String actualFnameMsg = firstNameValidationMsg.getText();
		Assert.assertEquals(actualFnameMsg, expectedFnameMsg, "Invalid FirstName");
		WebElement lastNameValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom02']/following-sibling::div[1]"));
		String actualLnameMsg = lastNameValidationMsg.getText();
		Assert.assertEquals(actualLnameMsg, expectedLnameMsg, "Invalid LastName");
		WebElement userNameValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustomUsername']/following-sibling::div[1]"));
		String actualUnameMsg = userNameValidationMsg.getText();
		Assert.assertEquals(actualUnameMsg, expectedUnameMsg, "Invalid UserName");
		WebElement cityValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom03']/following-sibling::div[1]"));
		String actualCnameMsg = cityValidationMsg.getText();
		Assert.assertEquals(actualCnameMsg, expectedCnameMsg, "Invalid City");
		WebElement stateValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom04']/following-sibling::div[1]"));
		String actualSnameMsg = stateValidationMsg.getText();
		Assert.assertEquals(actualSnameMsg, expectedSnameMsg, "Invalid State");
		WebElement zipValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom05']/following-sibling::div[1]"));
		String actualZnameMsg = zipValidationMsg.getText();
		Assert.assertEquals(actualZnameMsg, expectedZnameMsg, "Invalid Zip");
	}

	@Test
	public void tc_005_verifyTwoEmptyFieldValidation() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		WebElement firstNameMessageField = driver.findElement(By.xpath("//input[@id='validationCustom01']"));
		firstNameMessageField.sendKeys("Lincy");
		WebElement lastNameMessageField = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
		lastNameMessageField.sendKeys("Lal");
		WebElement UserNameMessageField = driver.findElement(By.xpath("//input[@id='validationCustomUsername']"));
		UserNameMessageField.sendKeys("lincylal@yopmail.com");
		WebElement cityMessageField = driver.findElement(By.xpath("//input[@id='validationCustom03']"));
		cityMessageField.sendKeys("Kollam");
		WebElement checkBox = driver.findElement(By.xpath("//input[@id='invalidCheck']"));
		checkBox.click();
		WebElement submitButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		submitButton.click();
		String expectedSnameMsg = "Please provide a valid state.";
		String expectedZnameMsg = "Please provide a valid zip.";
		WebElement stateValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom04']/following-sibling::div[1]"));
		String actualSnameMsg = stateValidationMsg.getText();
		Assert.assertEquals(actualSnameMsg, expectedSnameMsg, "Invalid State");
		WebElement zipValidationMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom05']/following-sibling::div[1]"));
		String actualZnameMsg = zipValidationMsg.getText();
		Assert.assertEquals(actualZnameMsg, expectedZnameMsg, "Invalid Zip");
	}

	@Test
	public void tc_006_verifySuccessInputFieldMessage() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");

		WebElement firstNameMessageField = driver.findElement(By.xpath("//input[@id='validationCustom01']"));
		firstNameMessageField.sendKeys("Lincy");
		WebElement lastNameMessageField = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
		lastNameMessageField.sendKeys("Lal");
		WebElement UserNameMessageField = driver.findElement(By.xpath("//input[@id='validationCustomUsername']"));
		UserNameMessageField.sendKeys("lincylal@yopmail.com");
		WebElement cityMessageField = driver.findElement(By.xpath("//input[@id='validationCustom03']"));
		cityMessageField.sendKeys("Kollam");
		WebElement stateMessageField = driver.findElement(By.xpath("//input[@id='validationCustom04']"));
		stateMessageField.sendKeys("Kerala");
		WebElement zipMessageField = driver.findElement(By.xpath("//input[@id='validationCustom05']"));
		zipMessageField.sendKeys("691538");
		WebElement checkBox = driver.findElement(By.xpath("//input[@id='invalidCheck']"));
		checkBox.click();
		WebElement submitButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		submitButton.click();
		String exp_successMsg = "Form has been submitted successfully!";
		WebElement successMsg = driver.findElement(By.xpath("//div[@id='message-one']"));
		String act_successMsg = successMsg.getText();
		Assert.assertEquals(act_successMsg, exp_successMsg, "invalid msg");
	}

	@Test
	public void tc_007_verifyNewsLetterSubscription() {
		driver.get("https://demowebshop.tricentis.com");
		WebElement newsEmailField = driver.findElement(By.cssSelector("input#newsletter-email"));
		WebElement subscribeButton = driver.findElement(By.cssSelector("input#newsletter-subscribe-button"));
		newsEmailField.sendKeys("lincylal@yopmail.com");
		subscribeButton.click();
		String exp_validationMsg = "Thank you for signing up! A verification email has been sent. We appreciate your interest.";
		WebElement validationMsg = driver.findElement(By.cssSelector("div#newsletter-result-block"));
		String act_validationMsg = validationMsg.getText();
		Assert.assertEquals(act_validationMsg, exp_validationMsg, "Invalid Message");
	}

	@Test
	public void tc_008_verifyInputValuesAndIntegerConversion() throws InterruptedException {
		driver.get("https://phptravels.com/demo/");
		WebElement fName = driver.findElement(By.cssSelector("input.first_name.input.mb1"));
		fName.sendKeys("Lincy");
		WebElement LName = driver.findElement(By.cssSelector("input.last_name.input.mb1"));
		LName.sendKeys("Lal");
		WebElement BName = driver.findElement(By.cssSelector("input.business_name.input.mb1"));
		BName.sendKeys("L&L");
		WebElement email = driver.findElement(By.cssSelector("input.email.input.mb1"));
		email.sendKeys("lincylal@yopmail.com");
		WebElement num1 = driver.findElement(By.cssSelector("span#numb1"));
		String number1 = num1.getText();
		WebElement num2 = driver.findElement(By.cssSelector("span#numb2"));
		String number2 = num2.getText();
		int a = Integer.parseInt(number1);
		int b = Integer.parseInt(number2);
		int sum = a + b;
		String result = Integer.toString(sum);
		WebElement resultMsg = driver.findElement(By.cssSelector("input#number"));
		resultMsg.sendKeys(result);
		WebElement button = driver.findElement(By.cssSelector("button#demo"));
		button.click();
		WebElement completedBox = driver.findElement(By.cssSelector("div.completed"));
		Thread.sleep(5000);
		boolean status = completedBox.isDisplayed();
		System.out.println(status);
	}

	@Test
	public void tc_009_verifyQuitAndClose() {
		driver.get("https://demo.guru99.com/popup.php");
		WebElement clickHereButton = driver.findElement(By.xpath("//a[text()='Click Here']"));
		clickHereButton.click();
	}

	@Test
	public void verifyNavigateto() {
		// driver.get("https://demowebshop.tricentis.com");
		driver.navigate().to("https://demowebshop.tricentis.com");
	}

	@Test
	public void verifyRefresh() {
		driver.get("https://demowebshop.tricentis.com");
		WebElement newsletterField = driver.findElement(By.xpath("//input[@id='newsletter-email']"));
		newsletterField.sendKeys("lincylal@yopmail.com");
		driver.navigate().refresh();
	}

	@Test
	public void verifyForwardAndBackwardNavigation() throws InterruptedException {
		driver.get("https://demowebshop.tricentis.com");
		WebElement loginMenu = driver.findElement(By.xpath("//a[text()='Log in']"));
		loginMenu.click();
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(2000);
		driver.navigate().forward();

	}

	@Test
	public void verifyWebElementcommands() throws InterruptedException {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement subjField = driver.findElement(By.xpath("//input[@id='subject']"));
		subjField.sendKeys("Selenium ");
		Thread.sleep(2000);
		WebElement descField = driver.findElement(By.xpath("//textarea[@id='description']"));
		descField.sendKeys("Automation Testing");
		subjField.clear();// to clear an input field
		String classAttributeValue = subjField.getAttribute("class");
		System.out.println(classAttributeValue);
		String tagnameValue = subjField.getTagName();
		System.out.println(tagnameValue);
		subjField.sendKeys("Selenium Automation");
		WebElement submitButton = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
		Thread.sleep(2000);
		submitButton.click();
		Thread.sleep(2000);
		String exp_successMsg = "Form has been submitted successfully!";
		WebElement successMsg = driver.findElement(By.xpath("//div[@id='message-one']"));
		String act_successMsg = successMsg.getText();
		Assert.assertEquals(act_successMsg, exp_successMsg, "invalid msg");
	}

	@Test
	public void tc_10_verifyIsDisplayed() {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement subjField = driver.findElement(By.xpath("//input[@id='subject']"));
		subjField.sendKeys("Selenium");
		boolean status = subjField.isDisplayed();
		System.out.println(status);
		Assert.assertTrue(status, "Invalid Status");

	}

	@Test
	public void tc_11_verifyIsSelected() {
		driver.get("https://selenium.obsqurazone.com/check-box-demo.php");
		WebElement singleCheckBox = driver.findElement(By.xpath("//input[@id='gridCheck']"));
		boolean statusBeforeClick = singleCheckBox.isSelected();
		System.out.println(statusBeforeClick);
		Assert.assertFalse(statusBeforeClick, "Checkbox is Selected");
		singleCheckBox.click();
		boolean statusAfterClick = singleCheckBox.isSelected();
		System.out.println(statusAfterClick);
		Assert.assertTrue(statusAfterClick, "Checkbox is not Selected");
	}

	@Test
	public void tc_12_verifyIsEnabled() {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement submitButton = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
		// submitButton.click();
		boolean statusAfterClick = submitButton.isEnabled();
		System.out.println(statusAfterClick);
		Assert.assertTrue(statusAfterClick, "Button is not Enabled");
		Point point = submitButton.getLocation();
		System.out.println(point.x + "," + point.y);
		Dimension dim = submitButton.getSize();
		System.out.println(dim.height + "," + dim.width);
		String bgcolor = submitButton.getCssValue("background-color");
		System.out.println(bgcolor);
		WebElement element = driver.findElement(By.tagName("input"));
		System.out.println(element);
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		System.out.println(elements);
	}

	@Test
	public void tc_13_verifyTheMessageDisplayedInNewTab() {
		driver.get("https://demoqa.com/browser-windows");
		WebElement newTabButton = driver.findElement(By.id("tabButton"));
		boolean status = newTabButton.isEnabled();
		System.out.println(status);
		// Assert.assertTrue(status, "Button is not enabled");
		newTabButton.click();
		driver.navigate().to("https://demoqa.com/sample");
		WebElement msg = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
		String Exp_msg = "This is a sample page";
		String act_msg = msg.getText();
		Assert.assertEquals(act_msg, Exp_msg, "Invalid Message");
	}

	@Test
	public void tc_14_verifyTheMessageDisplayedInNewWindow() {
		driver.get("https://demoqa.com/browser-windows");
		String parentWindow = driver.getWindowHandle();
		System.out.println("Parent Window Id" + parentWindow);
		WebElement NewWindowButton = driver.findElement(By.xpath("//button[@id='windowButton']"));
		NewWindowButton.click();
		Set<String> handles = driver.getWindowHandles();
		System.out.println(handles);
		Iterator<String> handleids = handles.iterator();
		while (handleids.hasNext()) {
			String childWindow = handleids.next();
			if (!childWindow.equals(parentWindow)) {
				driver.switchTo().window(childWindow);
				WebElement samplePage = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
				String act_msg = samplePage.getText();
				String exp_msg = "This is a sample page";
				Assert.assertEquals(act_msg, exp_msg, "Invalid msg");
				// driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}

	@Test
	public void tc_15_verifySimpleAlert() throws InterruptedException {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickme = driver.findElement(By.xpath("//button[@class='btn btn-success']"));
		clickme.click();
		Thread.sleep(5000);
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println(alertText);
		alert.accept();
	}

	@Test
	public void tc_16_verifyConfirmAlert() throws InterruptedException {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickme = driver.findElement(By.xpath("//button[@class='btn btn-warning']"));
		clickme.click();
		Thread.sleep(5000);
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println(alertText);
		alert.dismiss();
	}

	@Test
	public void tc_17_verifyPromptAlert() throws InterruptedException {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickme = driver.findElement(By.xpath("//button[@class='btn btn-danger']"));
		clickme.click();
		Thread.sleep(5000);
		Alert alert = driver.switchTo().alert();
		alert.sendKeys("Lincy");
		String alertText = alert.getText();
		System.out.println(alertText);
		alert.accept();

	}
	@Test
	public void tc_18_verifyTextInaFrame()
	{
		driver.get("https://demoqa.com/frames");
		List<WebElement> frames=driver.findElements(By.tagName("iframe"));
		int noOfFrames=frames.size();
		System.out.println(noOfFrames);
		//driver.switchTo().frame(3);//using index
		//driver.switchTo().frame("frame1");//using name or id
		WebElement frame1=driver.findElement(By.id("frame1"));//using web element
		driver.switchTo().frame(frame1);
		WebElement heading=driver.findElement(By.id("sampleHeading"));
		String headingText=heading.getText();
		System.out.println(headingText);
		driver.switchTo().parentFrame();
		//driver.switchTo().defaultContent();
		}
	
@Test
public void tc_19_verifyRightClick()
{
	driver.get("https://demo.guru99.com/test/simple_context_menu.html");
	WebElement rightClickMe=driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"));
	Actions action=new Actions(driver);
	action.contextClick(rightClickMe).build().perform();
}
@Test
public void tc_20_verifyDoubleClick()
{
	driver.get("https://demo.guru99.com/test/simple_context_menu.html");
	WebElement doubleClickMe=driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
	Actions actions = new Actions(driver);
	actions.doubleClick(doubleClickMe).build().perform();
	Alert alert = driver.switchTo().alert();
	alert.accept();
}
@Test
public void tc_21_verifyMouseHover()
{
	driver.get("https://demoqa.com/menu/");
	WebElement mainItem2=driver.findElement(By.xpath("//a[text()='Main Item 2']"));
	Actions actions=new Actions(driver);
	actions.moveToElement(mainItem2).build().perform();
	WebElement subItem=driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
	actions.moveToElement(subItem).build().perform();
	WebElement subSubItem=driver.findElement(By.xpath("//a[text()='Sub Sub Item 1']"));
	actions.moveToElement(subSubItem).build().perform();
	subSubItem.click();
	
	
}
@Test
public void tc_22_verifyDragAndDrop()
{
	driver.get("https://demoqa.com/droppable");
	WebElement dragMe=driver.findElement(By.id("draggable"));
	WebElement dropHere=driver.findElement(By.id("droppable"));
	Actions actions=new Actions(driver);
	actions.dragAndDrop(dragMe, dropHere).build().perform();
}
@Test
public void tc_23_verifyClickAndHoldResizable()
{
	driver.get("https://demoqa.com/resizable");
	WebElement resizableBox=driver.findElement(By.xpath("//div[@id='resizableBoxWithRestriction']/child::span"));
	Actions actions=new Actions(driver);
	actions.clickAndHold(resizableBox).build().perform();
	actions.dragAndDropBy(resizableBox, 100, 100).build().perform();
	
}
@Test
public void tc_24_verifyDropDown()
{
	driver.get("https://demo.guru99.com/test/newtours/register.php");
	WebElement country=driver.findElement(By.xpath("//select[@name='country']"));
	Select select=new Select(country);
	select.selectByVisibleText("INDIA");//select by text
	select.selectByIndex(23);//select by index
	select.selectByValue("IRELAND");//select by value
	}
@Test
public void tc_25_verifyMultipleSelectionFromDropdown()
{
	driver.get("https://www.softwaretestingmaterial.com/sample-webpage-to-automate/\r\n");
	WebElement multValues=driver.findElement(By.xpath("//select[@name='multipleselect[]']"));
	Select select=new Select(multValues);
	boolean value=select.isMultiple();
	System.out.println(value);
	select.selectByVisibleText("Performance Testing");
	select.selectByVisibleText("Agile Methodology");
	select.deselectAll();
	}
@Test
public void tc_26_verifyFileUpload()
{
	driver.get("https://demo.guru99.com/test/upload/");
	WebElement chooseFile=driver.findElement(By.xpath("//input[@id='uploadfile_0']"));
	chooseFile.sendKeys("D:\\Selenium\\test.txt");
	WebElement radio=driver.findElement(By.xpath("//input[@id='terms']"));
	radio.click();
	WebElement submit=driver.findElement(By.id("submitbutton"));
	submit.click();
	
}
@Test
public void verifyClickAndSendkeysUsingJavascriptExecuter()
{
	driver.get("https://demowebshop.tricentis.com/");
	JavascriptExecutor js=(JavascriptExecutor)driver;
	js.executeScript("document.getElementById('newsletter-email').value='test@gmail.com'");
	js.executeScript("document.getElementById('newsletter-subscribe-button').click()");
	
}
@Test
public void tc_28_verifyScrollDown()
{
	driver.get("https://demo.guru99.com/test/guru99home/");
	JavascriptExecutor js= (JavascriptExecutor)driver;
	js.executeScript("window.scrollBy(0,1000)");
}
@Test
public void tc_29_verifyScrollintoViewofaWebelement()
{
	driver.get("https://demo.guru99.com/test/guru99home/");
	WebElement linuxText=driver.findElement(By.linkText("Linux"));
	JavascriptExecutor js= (JavascriptExecutor)driver;
	js.executeScript("arguments[0].scrollIntoView();",linuxText);
	
	
}
@Test
public void tc_30_verifyHorizintalScrollintoViewofaWebelement()
{
	driver.get("https://demo.guru99.com/test/guru99home/");
	WebElement vbScriptText=driver.findElement(By.linkText("VBScript"));
	JavascriptExecutor js= (JavascriptExecutor)driver;
	js.executeScript("arguments[0].scrollIntoView();",vbScriptText);
	
}
@Test
public void tc_31_verifyTable() throws IOException
{
	driver.get("https://www.w3schools.com/html/html_tables.asp");
	List<WebElement> rowelements= driver.findElements(By.xpath("//table[@id='customers']//tbody//tr"));
	List<WebElement> columnelements=driver.findElements(By.xpath("//table[@id='customers']//tbody//td"));
	List<ArrayList<String>> act_GridData=TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowelements, columnelements);
	List<ArrayList<String>> exp_GridData=ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx", "Table");
	Assert.assertEquals(act_GridData, exp_GridData,"Invalid data Found");
	}
@Test
public void tc_32_verifyfileUploadUsingRobotClass() throws AWTException, InterruptedException 
{
	driver.get("https://www.foundit.in/seeker/registration");
	StringSelection s = new StringSelection("D:\\Selenium\\Test.docx");
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
	WebElement chooseFile=driver.findElement(By.xpath("//div[@class='uploadResume']"));
	chooseFile.click();
	Robot r=new Robot();
	r.keyPress(KeyEvent.VK_ENTER);
	r.keyRelease(KeyEvent.VK_ENTER);
	Thread.sleep(2000);
	r.keyPress(KeyEvent.VK_CONTROL);
	r.keyPress(KeyEvent.VK_V);
	r.keyRelease(KeyEvent.VK_CONTROL);
	r.keyRelease(KeyEvent.VK_V);
	Thread.sleep(2000);
	r.keyPress(KeyEvent.VK_ENTER);
	r.keyRelease(KeyEvent.VK_ENTER);
	
}
@Test
public void tc_33_verifyTable2() throws IOException
{
	driver.get("https://selenium.obsqurazone.com/table-sort-search.php");
	WebElement search=driver.findElement(By.xpath("//div[@id='dtBasicExample_filter']//input[@type='search']"));
	search.sendKeys("Caesar");
	List<WebElement> rowelements= driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr"));
	List<WebElement> columnelements= driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr//td"));
	List<ArrayList<String>> act_GridData=TableUtility.get_Dynamic_TwoDimension_ObscuraTablElemnts(rowelements, columnelements);
	System.out.println(act_GridData);
	List<ArrayList<String>> exp_GridData=ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx", "Table2");
	System.out.println(exp_GridData);
	Assert.assertEquals(act_GridData, exp_GridData,"Invalid data Found");
	
	}
@Test
public void tc_34_verifywaitsInSselenium()
{
	driver.get("https://demowebshop.tricentis.com/");
	/*Page load wait*/
	driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	/* Implicit wait*/
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	WebElement email=driver.findElement(By.xpath("//input[@id='newsletter-email']"));
	email.sendKeys("lincy@gmail.com");
	/* Explicit wait */
	WebElement subscribeButton=driver.findElement(By.xpath("//input[@id='newsletter-subscribe-button']"));
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	wait.until(ExpectedConditions.visibilityOf(subscribeButton));
	/*Fluent wait*/
	FluentWait fwait=new FluentWait<WebDriver>(driver);
	fwait.withTimeout(Duration.ofSeconds(10));
	fwait.pollingEvery(Duration.ofSeconds(1));
	fwait.until(ExpectedConditions.visibilityOf(subscribeButton));
	subscribeButton.click();
}

}	
