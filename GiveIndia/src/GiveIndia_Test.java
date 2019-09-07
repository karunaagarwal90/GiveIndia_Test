import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GiveIndia_Test {
	static WebDriver driver;

	public static void main(String[] args) throws Exception {

		System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		driver =new ChromeDriver();
		driver.get("https://en.wikipedia.org/wiki/Selenium");
		try{
			driver.manage().window().maximize();
			List<WebElement> links=driver.findElements(By.xpath("//span[@id='External_links']/parent::h2/following-sibling::ul/li"));

			for(WebElement we : links){
				List <WebElement> k= we.findElements(By.tagName("a"));
				String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.ENTER);

				if(k != null && k.size() > 0){
					for(WebElement a : k){
						a.sendKeys(selectLinkOpeninNewTab);
						Thread.sleep(1000L);
					}
				}
				Set<String> stSession=driver.getWindowHandles();
				Iterator<String> it=stSession.iterator();
				String Parentid= it.next();

				while(it.hasNext()){
					driver.switchTo().window(it.next());
					System.out.println("External link title : " + driver.getTitle());
					driver.switchTo().window(Parentid);
				}
			}

			//Oxygen link click and counting number of Pdfs present in refrences
			WebElement oxylink=driver.findElement(By.xpath("//a[@href='/wiki/Oxygen'][1]"));
			oxylink.click();
			Thread.sleep(1000L);
			//Assert to validate if article is featured article
			assert driver.getTitle().equals("Oxygen - Wikipedia") : "Not a featured article";

			List<WebElement> lstRefSib=driver.findElements(By.xpath("//span[@id='References']/parent::h2/following-sibling::div"));

			WebElement reflink= lstRefSib.get(0);
			List<WebElement> pdflink=reflink.findElements(By.xpath("//a[contains(@href,'.pdf')]"));

			System.out.println("Number of Pdf Links is equal to " +pdflink.size());

			// Search pluto in search field and verify Plutonium as in second suggestion 
			driver.findElement(By.xpath("//input[@id='searchInput']")).sendKeys("pluto");
			Thread.sleep(1000L);
			List<WebElement> autosuggestion = driver.findElements(By.xpath("//div[@class='suggestions-results']/child::a"));
			//Assert to validate if 2nd suggestion is Plutonium
			if(autosuggestion!=null && autosuggestion.size()> 1){
				assert autosuggestion.get(1).getText().equals("Plutonium") : "2nd suggestion is not Plutonium";
			}
		} catch(Exception e){
			//e.printStackTrace();
		} finally{
			driver.close();
			driver.quit();
		}
	}

}