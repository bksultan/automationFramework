package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utilities.Driver;
import utilities.PropertyUtils;

public class LoginFunc {
    WebDriver driver = Driver.getDriver("browser");
    LoginPage loginPage = new LoginPage();

    @Given("User navigates to {string} application")
    public void userNavigatesToApplication(String URL) {
        driver.navigate().to(PropertyUtils.getProperty(URL));
    }

    @Then("Verify User successfully navigated to this app")
    public void verifyUserSuccessfullyNavigatedToThisApp() {
        loginPage.restGet("/");
    }

    @Given("User log in with {string} credentials")
    public void userLogInWithCredentials(String credentials) {
        loginPage.authentication(credentials);
    }

    @Then("Verifying User successfully logged in")
    public void verifyingUserSuccessfullyLoggedIn() {
        loginPage.restGet("/login");
    }

    @When("User click on reset password button and provide {string} email")
    public void userClickOnResetPasswordButtonAndProvideEmail(String credentials) {
        loginPage.resetPassword(credentials);
        loginPage.checkMail(credentials);
    }

    @Then("User can reset password {string}")
    public void userCanResetPassword(String credentials) {
        loginPage.provideNewPassword(credentials);
        loginPage.restGet("/resetPassword");
    }

    @Given("User provide {string} credentials {int} times")
    public void userProvideCredentialsTimes(String credentials, Integer maxAttempts) {
        for (int i = 0; i <= maxAttempts; i++) {
            loginPage.authentication(credentials);
            Assert.assertTrue(loginPage.errorMsg.isDisplayed());
        }
    }

    @Then("User see error for maximum incorrect login attempts")
    public void userSeeErrorForMaximumIncorrectLoginAttempts() {
        Assert.assertTrue(loginPage.maxErrorAttempts.isDisplayed());
    }
}
