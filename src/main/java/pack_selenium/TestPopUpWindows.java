package pack_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestPopUpWindows {
	
	static By byLink_MultiPopUp = By.xpath("//a[text()='Multi-PopUp Test']"); 
	static String url = "http://www.popuptest.com/";
	
	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();		
		driver.get(url);
		String orgWindowHandle = driver.getWindowHandle();
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		Wait wait = new FluentWait(driver)
					.withTimeout(2,TimeUnit.SECONDS)
					.pollingEvery(2,TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
		
		WebElement link_MultiPopUp = (WebElement)wait.until(new Function<WebDriver, WebElement>(){
			public WebElement apply(WebDriver driver){
				System.out.println("inside fluent wait");
				return driver.findElement(byLink_MultiPopUp);
			}
		});
		link_MultiPopUp.click();
		
		Set<String> allWindowHandles = driver.getWindowHandles();
		System.out.println("Total popup windows: " + allWindowHandles.size());
		for(String eachWndHandle : allWindowHandles){
			if (!(eachWndHandle.equals(orgWindowHandle))){
				System.out.println("Total popup windows: " + allWindowHandles.size());
				driver.switchTo().window(eachWndHandle);
				driver.close();
			}				
		}
		driver.switchTo().window(orgWindowHandle);
		driver.close();
		allWindowHandles = driver.getWindowHandles();
		for(String eachWndHandle : allWindowHandles){			
			System.out.println("Total popup windows: " + allWindowHandles.size());
			driver.switchTo().window(eachWndHandle);
			driver.close();							
		}
		

	}

}
