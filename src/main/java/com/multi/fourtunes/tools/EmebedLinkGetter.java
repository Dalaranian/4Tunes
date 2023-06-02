package com.multi.fourtunes.tools;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class EmebedLinkGetter {


	WebDriver webdriver;
	
	/**
	 * @param String title 노래 제목
	 * @param String link ManiaDb 링크
	 * @return
	 */
	public String getLink(String title, String link) {
		webdriver = null;
		File webDriverFile = new File("WebDriver/chromedriver_mac64/chromedriver.exec");
		
		System.out.println(webDriverFile.getName() + " " + webDriverFile.getAbsolutePath());
		
		try {
	        // Chrome 웹 드라이버 경로 설정
			String driverPath = webDriverFile.getAbsolutePath();
//			System.setProperty("webdriver.chrome.driver", driverPath);
			System.setProperty("webdriver.chrome.driver", "/Users/baes_macbook/Desktop/Repo/GitHub Repo/4Tunes/WebDriver/chromedriver_mac64/chromedriver");
			
	        // Chrome 옵션 설정
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--headless"); // 브라우저 창을 띄우지 않고 실행

	        // Chrome 웹 드라이버 인스턴스 생성
	        WebDriver driver = new ChromeDriver(options);
	        
	        try {
				driver.get(link);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "null";
	}
}
