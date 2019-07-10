package com.maventest.phptravel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import com.maventest.phptravel.LoginAdminCrud;

import io.github.bonigarcia.seljup.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class LoginAdminCrudTest {
	private static LoginAdminCrud loginPage;
	private static ChromeDriver driver;

	public LoginAdminCrudTest(ChromeDriver driver) {
		LoginAdminCrudTest.driver = driver;
	}

	@Test
	public void validLoginOnWebsite() throws Exception {
		loginPage = new LoginAdminCrud(driver);
		loginPage.login("admin@phptravels.com", "demoadmin");
		System.out.println(driver.getTitle());
		assertTrue(loginPage.assertTitle());
	}
	
	@Test
	public void CRUDTesting() throws Exception {
		loginPage = new LoginAdminCrud(driver);
		loginPage.login("admin@phptravels.com", "demoadmin");
		assertDoesNotThrow(() -> {
			loginPage.goToAccounts("Chuo");
	    });
	}
	
	@Test
	public void FormTesting() throws Exception {
		loginPage = new LoginAdminCrud(driver);
		loginPage.login("admin@phptravels.com", "demoadmin");
		assertDoesNotThrow(() -> {
			loginPage.goToTours("Jedziemy");;
	    });
	}


	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}
}