package digi.automation.client.steps;

import digi.automation.client.driver.DriverInitializer;
import cucumber.api.java.en.*;
import org.openqa.selenium.*;


public class Login{

WebDriver webDriver = null;

	@Given("^user launch browser$")
	public void userlaunchbrowser() throws Throwable {
		webDriver = DriverInitializer.getDriver("chrome");
	}

	@When("^open site landing Page$")
	public void opensitelandingPage() throws Throwable {
		webDriver.get(DriverInitializer.getProperty("login.url"));
	}

	@When("^click on sign In button$")
	public void clickonsignInbutton() throws Throwable {
		webDriver.get(DriverInitializer.getProperty("login.url"));
	}

	@Then("^user should be able to see the signIn popup window with options$")
	public void usershouldbeabletoseethesignInpopupwindowwithoptions() throws Throwable {
		try {
			WebElement webElement = webDriver.findElement(By.xpath(""));
			webElement.click();
		}catch (NoSuchElementException e){
			System.out.println(e);
		}
	}


}