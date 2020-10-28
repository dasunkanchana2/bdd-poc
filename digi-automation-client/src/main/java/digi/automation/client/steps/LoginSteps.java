package digi.automation.client.steps;

import digi.automation.client.driver.DriverInitializer;
import cucumber.api.java.en.*;
import org.openqa.selenium.*;

public class LoginSteps {

    WebDriver webDriver = null;

    @Given("^user launch browser$")
    public void userLaunchBrowser() throws Throwable {
        webDriver = DriverInitializer.getDriver(DriverInitializer.getProperty("browser"));
    }

    @When("^open site landing Page$")
    public void openSiteLandingPage() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("login.url"));
    }

    @When("^click on sign In button$")
    public void click_on_sign_In_button() throws Throwable {
        try {
            //webDriver.wait(12);
            webDriver.findElement(By.xpath("//*[@id=\"sign_in_btn_link\"]")).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.print(e);
        }
    }

    @Then("^user should be able to see the signIn popup window with options$")
    public void user_should_be_able_to_see_the_signIn_popup_window_with_options() throws Throwable {
        try {
            String headerValue = webDriver.findElement(By.xpath("//*[@id=\"sign_in_btn_link\"]")).getText();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.print(e);
        }
    }

    @Given("^navigate to sign In button and click$")
    public void navigate_to_sign_In_button_and_click() throws Throwable {
        System.out.print("Navigate the sign in button and click");
    }

    @When("^signIn popup window opens$")
    public void signin_popup_window_opens() throws Throwable {
        System.out.print("Sign in popup window open");
    }

    @When("^enter valid email as \"([^\"]*)\" and password as \"([^\"]*)\"$")
    public void enter_valid_email_as_and_password_as(String arg1, String arg2) throws Throwable {
        try {
            webDriver.findElement(By.xpath("//*[@id=\"sign_in_email\"]/input"))
                    .sendKeys(DriverInitializer.getProperty("username"));

            webDriver.findElement(By.xpath("//*[@id=\"mysecretpassword\"]"))
                    .sendKeys(DriverInitializer.getProperty("password"));

        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.print(e);
        }
    }

    @When("^click on sign In button on the sign in modal$")
    public void click_on_sign_In_button_on_the_sign_in_modal() throws Throwable {
        try {
            webDriver.findElement(By.xpath("//*[@id=\"sign_in_btn\"]")).click();

        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.print(e);
        }
    }

    @Then("^user should be able to see the home page$")
    public void user_should_be_able_to_see_the_home_page() throws Throwable {
        try {
            webDriver.wait(12);
            webDriver.findElement(By.id("navigate_linkTo_userProfile"));
            webDriver.quit();

        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.print(e);
        }
    }
}
