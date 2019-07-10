package com.maventest.phptravel;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


public class Login {

	private WebDriver webDriver;
	private final Wait<WebDriver> waitWebDriver;
	
	
	public Login(WebDriver webDriver) {
		this.webDriver = webDriver;
		waitWebDriver = new FluentWait<WebDriver>(webDriver)
				.withTimeout(30, TimeUnit.SECONDS)
			    .pollingEvery(5, TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
		webDriver.get("https://www.phptravels.net/login");
	}

	public void login(String login, String password) throws Exception {
		webDriver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		webDriver.findElement(By.name("username")).sendKeys(login);
		System.out.println("login");
		webDriver.findElement(By.name("password")).sendKeys(password);
		System.out.println("pass");
		webDriver.findElement(By.xpath("//button[contains(.,'Login')]")).click();
		System.out.println("submit");
		waitWebDriver.until(ExpectedConditions.titleContains("My Account"));
	}

	public boolean assertTitle() throws Exception {
		return webDriver.getTitle().contains("My Account");
	}
}
