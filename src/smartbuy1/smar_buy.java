package smartbuy1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class smar_buy {

	public WebDriver driver;
	public double numberOfTry = 10;
    public double totalfinl2;
	SoftAssert softassert = new SoftAssert();

	@BeforeTest

	public void log_in() {

		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();

		driver.get("https://smartbuy-me.com/smartbuystore/");
		// driver.manage().window().maximize();
		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();

	}

	@Test
	public void add_Element_labtop() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		for (int i = 0; i < numberOfTry; i++) {
			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();

			String msg = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[1]")).getText();

			if (msg.contains("Sorry")) {

				numberOfTry = i;
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();
				String totalpricstring = driver
						.findElement(By.xpath("/html/body/main/div[3]/div[1]/div[2]/div[4]/div/div[2]/div/div[1]/div[2]"))
						.getText();
				String totalpriceAfterSplit[] = totalpricstring.split("JOD");
				String totalprictrim = totalpriceAfterSplit[0].trim();
				String totalpricereplece = totalprictrim.replace(",", ".");
				String totalpricerep2 = totalpricereplece.replace(".000", "");
				double totalfinal = Double.parseDouble(totalpricerep2);
				 totalfinl2 = totalfinal * 1000;
				System.out.println(totalfinl2);
			} else {

				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();
			}
			
		}

		System.out.println(numberOfTry);
	}

	@Test
	public void Test_price_of_added() {

		String price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();
		String priceAfterSplit[] = price.split("JOD");
		String realpriceinSting = priceAfterSplit[0];
		String priceTrim = realpriceinSting.trim();
		String finelprice = priceTrim.replace(",", ".");

		double val = Double.parseDouble(finelprice);
		double totalprice = val * numberOfTry;

		 softassert.assertEquals(totalfinl2, totalprice);

	}

}