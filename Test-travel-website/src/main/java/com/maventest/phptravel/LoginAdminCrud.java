package com.maventest.phptravel;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginAdminCrud {

	private WebDriver webDriver;
	private final Wait<WebDriver> waitWebDriver;

	
	public LoginAdminCrud(ChromeDriver webDriver) {
		this.webDriver = webDriver;
		waitWebDriver = new WebDriverWait(webDriver, 10);
		webDriver.manage().window().fullscreen();
		webDriver.get("https://www.phptravels.net/admin-portal/");
	}

	public void login(String login, String password) throws Exception {
		webDriver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		webDriver.findElement(By.name("email")).sendKeys(login);
		System.out.println("login");
		webDriver.findElement(By.name("password")).sendKeys(password);
		System.out.println("pass");
		webDriver.findElement(By.xpath("//button[contains(.,'Login')]")).click();
		System.out.println("submit");
		waitWebDriver.until(ExpectedConditions.titleContains("Dashboard"));
	}
	
	public void goToAccounts(String name) throws Exception {
		//CREATE
		webDriver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		webDriver.findElement(By.xpath("//*[@id=\"social-sidebar-menu\"]/li[5]/a")).click();
		webDriver.findElement(By.xpath("//*[@id=\"ACCOUNTS\"]/li[3]/a")).click();
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/form/button")).click();
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[2]/div/div[1]/div/input")).sendKeys(name);
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[2]/div/div[2]/div/input")).sendKeys("Lii");
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[2]/div/div[3]/div/input")).sendKeys("chuolii@gmail.com");
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[2]/div/div[4]/div/input")).sendKeys("ChUoLi123");
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[2]/div/div[5]/div/input")).sendKeys("080008000");
		webDriver.findElement(By.xpath("//*[@id=\"s2id_autogen1\"]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"select2-drop\"]/ul/li[215]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[2]/div/div[8]/div/input")).sendKeys("Xiaomi Huawei Oppo OnePlus");
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[3]/button")).click();
		//String addedName = webDriver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div/div/div[1]/div[2]/table/tbody/tr[1]/td[3]")).getText().toString();
		//EDIT
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div/div/div[1]/div[2]/table/tbody/tr[1]/td[8]/span/a[1]")).click();
		String id = webDriver.getCurrentUrl().toString().substring(webDriver.getCurrentUrl().toString().length() - 3);
		int intID = Integer.parseInt(id);	
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[2]/div/div[9]/div/input")).sendKeys("xyz");
		webDriver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div[3]/button")).click();
		//DELETE
		webDriver.findElement(By.xpath("//*[@id=\""+intID+"\"]")).click();
		webDriver.switchTo().alert().accept();
		
	}
	
	public void goToTours(String name) throws Exception {
		//Form
		webDriver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		webDriver.findElement(By.xpath("//*[@id=\"social-sidebar-menu\"]/li[9]/a")).click();
		webDriver.findElement(By.xpath("//*[@id=\"Tours\"]/li[2]/a")).click();
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[3]/div/input")).sendKeys(name);
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[5]/div/table/tbody/tr[2]/td[2]/input")).sendKeys("2");
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[5]/div/table/tbody/tr[3]/td[2]/input")).sendKeys("1200");
		webDriver.findElement(By.xpath("//*[@id=\"childbtn\"]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[5]/div/table/tbody/tr[2]/td[3]/input")).sendKeys("1");
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[5]/div/table/tbody/tr[3]/td[3]/input")).sendKeys("600");
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[6]/div/select")).click();
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[6]/div/select/option[6]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[7]/div/input")).sendKeys("7");
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[8]/div/input")).sendKeys("8");
		webDriver.findElement(By.xpath("//*[@id=\"s2id_autogen11\"]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"GENERAL\"]/div[9]/div/select/option[4]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"s2id_locationlist1\"]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"select2-drop\"]/div/input")).sendKeys("Tokyo");
		webDriver.findElement(By.xpath("//*[@id=\"select2-drop\"]/ul/li")).click();
	}

	public boolean assertTitle() throws Exception {
		return webDriver.getTitle().contains("Dashboard");
	}
}
