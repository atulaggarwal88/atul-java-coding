package pack_selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class TestDynamicWebTables {
	static String url = "http://money.rediff.com/gainers/bse/daily";
	static By byAllWebTablesLink = By.xpath(".//*[@id='leftcontainer']/div[4]/child::*");
	static WebDriver driver = new FirefoxDriver();
	public static void main(String[] args) {		
		driver.get(url);
		List<WebElement> allWebTablesLink = driver.findElements(byAllWebTablesLink);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		for(int i = 0; i < allWebTablesLink.size(); i++){
			WebElement eachWebTableLink = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@id='leftcontainer']/div[4]/child::*)[position()=" + (i+1) + "]")));			

			System.out.println("Webtable Name: " + eachWebTableLink.getText());
			eachWebTableLink.click();
			int lastRow = getLastRowWebTable(By.xpath(".//*[@id='leftcontainer']/table/tbody/tr"));
			int lastColumn = lastColumnWebTable(By.xpath(".//*[@id='leftcontainer']/table/thead//th"));
			
			WebElement lastRowTableElement = driver.findElement(By.xpath("//*[@id='leftcontainer']/table/tbody/tr[" + lastRow + "]"));
			//System.out.println("Last row: " + lastRowTableElement.getAttribute("textContent"));
			System.out.println("Last row of Webtable: " + lastRowTableElement.getText());
		}
	}
	public static int getLastRowWebTable(By byTableElementXpath){
		return driver.findElements(byTableElementXpath).size();
		//return eElement;
	}
	public static int lastColumnWebTable(By byTableElementXpath){
		return driver.findElements(byTableElementXpath).size();
	}

}
