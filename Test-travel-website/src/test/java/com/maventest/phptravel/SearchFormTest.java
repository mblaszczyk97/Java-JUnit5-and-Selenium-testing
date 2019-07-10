package com.maventest.phptravel;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.maventest.phptravel.Home;

import io.github.bonigarcia.seljup.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class SearchFormTest {
	private static Home homePage;
	private static ChromeDriver webDriver;

	public SearchFormTest(ChromeDriver webDriver) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		SearchFormTest.webDriver = webDriver;
	}

	@Test
	public void nextDayHotel() throws InterruptedException {
		homePage = new Home(webDriver);
		homePage.checkIn();
		assertEquals(webDriver.getTitle(), "PHPTRAVELS | Travel Technology Partner");
	}

	@AfterAll
	public static void tearDown() throws Exception {
		webDriver.quit();
	}
}