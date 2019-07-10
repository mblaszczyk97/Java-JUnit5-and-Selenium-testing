package com.maventest.phptravel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.maventest.phptravel.Home;

import io.github.bonigarcia.seljup.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class PageTest {
	private static ChromeDriver driver;
	private static Home homePage;
	
	public PageTest(ChromeDriver driver) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		PageTest.driver = driver;
	}
	
	@Test
	public void imageClick() throws InterruptedException {
		homePage = new Home(driver);
		homePage.checkImage();
		assertAll(
	            () -> assertEquals(driver.getTitle(), "Virgin Gorda beaches and lobste"),
	            () -> assertNotEquals(driver.getTitle(), "Virgin Gorda beaches and lobster")
	    );
	}
	
	@Test
	public void checkOffer() throws InterruptedException {
		homePage = new Home(driver);
		assertTrue(homePage.checkOffer());
	}
	
	@Test
	public void noElementException() throws InterruptedException {
		homePage = new Home(driver);
		assertThrows(org.openqa.selenium.NoSuchElementException.class,() -> {
			homePage.noSuchElement();
	    });
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}
}