package com.maventest.phptravel;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import com.maventest.phptravel.Login;

import io.github.bonigarcia.seljup.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class LoginTest {
	private static Login loginPage;
	private static ChromeDriver driver;
	
	public LoginTest(ChromeDriver driver) {
		LoginTest.driver = driver;
	}
	
	@Test
	public void validLoginOnWebsite() throws Exception {
		assertLogin();
	}
	
	private void assertLogin() throws Exception {
		
			loginPage = new Login(driver);

		loginPage.login("user@phptravels.com", "demouser");
		//System.out.println(driver.getTitle());
		assertTrue(loginPage.assertTitle());
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}
}