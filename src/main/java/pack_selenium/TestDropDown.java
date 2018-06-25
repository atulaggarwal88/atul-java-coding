package pack_selenium;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestDropDown {
	static String url = "http://www.bensherman.com/";
	 
	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		driver.navigate().to(url);	

		List<WebElement> allDropDownMenu = driver.findElements(By.xpath("//div[@id='main-navigation']/ul[@class='level-0']/li"));
		System.out.println("Total Dropdown menus: " + allDropDownMenu.size());//Prints total dropdown menus		
		
		//Prints title of each dropdown menu, size and values in each drop down menu
		for(int i = 0; i < allDropDownMenu.size(); i++){
			//System.out.println("Dropdown menu: " + allDropDownMenu.get(i).getText());
			allDropDownMenu.get(i).click();
			List<WebElement> allDropDowns = driver.findElements(By.xpath("//div[@id='main-navigation']/ul[@class='level-0']/li[" + (i+1) + "]/ul/li//span[text()!='']"));
			//System.out.println(allDropDowns.size());
			Select select = new Select(allDropDownMenu.get(i));
			for(int j = 0; j < allDropDowns.size(); j++){
				select.selectByIndex(j+1);
				//eachDropDown.click();
				driver.navigate().back();
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			}
			
		}
	}
}
