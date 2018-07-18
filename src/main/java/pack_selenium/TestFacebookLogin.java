package pack_selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.*;

public class TestFacebookLogin {
	
	static By byTxtBx_Email = By.cssSelector("input#email");
	static By byTxtBx_Password = By.cssSelector("input.inputtext[type='password']");
	static By byButn_Login = By.cssSelector("input[type^='subm']");
	static By byDrpDown_AccountSettinngs = By.cssSelector("div#userNavigationLabel");	
	static By byMenuItm_Logout = By.cssSelector("ul._54nf li:nth-child(12)");
	static By byImg_Header = By.cssSelector("._cy6 ._4kny ._1k67 ._2s25");
	static String url = "https://www.facebook.com/";
	
	
	public static void main(String[] args) {		
		WebDriver driver = new FirefoxDriver();		
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(2000,TimeUnit.MILLISECONDS);
		
		String str_ExpectedTitle = "Facebook – log in or sign up";
		String str_ActualTitle = driver.getTitle();
		
		Assert.assertTrue("URL launch failed", str_ExpectedTitle.equals(str_ActualTitle));	
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement txtBx_Email = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtBx_Email));
		txtBx_Email.sendKeys("<user name>");
		WebElement txtBx_Password = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtBx_Password));
		txtBx_Password.sendKeys("<password>");
		WebElement butn_Login = wait.until(ExpectedConditions.elementToBeClickable(byButn_Login));
		butn_Login.click();
		WebElement drpDown_AccountSettings;
		try{
			drpDown_AccountSettings = wait.until(ExpectedConditions.elementToBeClickable(byDrpDown_AccountSettinngs));
			drpDown_AccountSettings.click();
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
				
		WebElement menuItm_Logout = wait.until(ExpectedConditions.elementToBeClickable(byMenuItm_Logout));
		Assert.assertTrue("Login failed", menuItm_Logout.getText().contains("Log Out"));
		
		WebElement img_Header = driver.findElement(byImg_Header);
		
		Actions actions = new Actions(driver);
		Action action = actions.moveToElement(img_Header).build();
		action.perform();
		
		String actualToolTip = img_Header.getAttribute("title");
		Assert.assertTrue("Actual value of tooltip: " + actualToolTip + "Expected: Profile", actualToolTip.contains("Profile"));
						
		
	}
}
