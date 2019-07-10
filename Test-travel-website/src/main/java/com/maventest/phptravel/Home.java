package com.maventest.phptravel;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
	private ChromeDriver driver;
	private FluentWait<WebDriver> waitWebDriver;

	public Home(ChromeDriver webDriver) {
		this.driver = webDriver;
		waitWebDriver = new FluentWait<WebDriver>(webDriver)
				.withTimeout(30, TimeUnit.SECONDS)
			    .pollingEvery(5, TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
		webDriver.manage().window().fullscreen();
		webDriver.get("https://www.phptravels.net/");
	}

	]
	public void checkIn() throws InterruptedException {
		WebElement calendar = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated((By.xpath("//input[@class='form input-lg dpd1']"))));
		WebElement nextMonth = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated((By.xpath("(//div[@class='datepicker-days']//th[last()])[1]"))));
		WebElement dayOut = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[11]/div[1]/table/tbody/tr[2]/td[7]")));
		WebElement calendarOut = driver.findElement(By.xpath("//input[@class='form input-lg dpd2']"));

		WebElement button = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[contains(.,'Search')])[1]")));

		calendar.click();
		System.out.println("kalendarz");
		nextMonth.click();
		System.out.println("dzein");
		calendarOut.click();
		System.out.println("kalendarz out");
		dayOut.click();
		System.out.println("dien ");
		button.click();

	}
	
	public void checkImage() throws InterruptedException {
		WebElement image = driver.findElement(By.xpath("//*[@id=\"body-section\"]/div[1]/a[1]/div/div/img"));
		image.click();

	}
	
	public boolean checkOffer() throws InterruptedException {
		WebElement offer = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/nav/div/div[2]/ul[1]/li[2]/a")));
		offer.click();
		WebElement buy = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"body-section\"]/div[6]/div/div[3]/div[1]/a[2]")));
		buy.click();
		WebElement tourDays = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"OVERVIEW\"]/div[4]/div/div/div[2]/div/div[1]/p[2]")));
		return tourDays.getText().toString().contains("Rate is subject");
	}
	
	public void noSuchElement() throws InterruptedException {
		WebElement offer = driver.findElement(By.xpath("/html/bodysa/nav/div/div[25]/"));
		offer.click();
	}
	

	public void selectNumPeople(int adults, int children) {
		Select selectAdults = new Select(driver.findElement(By.id("adults")));
		selectAdults.selectByIndex(adults - 1);

		Select selectChildren = new Select(driver.findElement(By.id("child")));
		selectChildren.selectByIndex(children);
	}

	public SearchPage submit() {
		return new SearchPage(driver);

	}
}