package com.maventest.phptravel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchPage{
	private WebDriver webDriver;
	
	public SearchPage(ChromeDriver driver) {
		this.webDriver = driver;
	}
	
	public String getTitle() {
		return webDriver.getTitle();
	}

}
