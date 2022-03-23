package com.assignment;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.ListIterator;

import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment2 {
	WebDriver driver;
	String url = "https://gmail.com";
	String email = "selnm.test@gmail.com";
	String password = "chrometest";
	String subjectText = "Test Mail";
	String bodyText = "Test EMail Body";;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get(url);
	}

	@Test
	public void gmailLogin() {
		WebElement userEmail = driver.findElement(By.xpath("//input[@id='identifierId']"));
		userEmail.sendKeys(email);

		WebElement next = driver.findElement(By.xpath("//span[text()='Next']"));
		next.click();

		WebElement userPassword = driver.findElement(By.xpath("//input[@name='password']"));
		userPassword.sendKeys(password);

		WebElement pwdNext = driver.findElement(By.xpath("//span[text()='Next']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", pwdNext);

		WebElement compose = driver.findElement(By.xpath("//div[contains(text(),'Compose')]"));
		compose.click();

		WebElement to = driver.findElement(By.xpath("//textarea[@name='to']"));
		to.sendKeys(email);

		WebElement subject = driver.findElement(By.xpath("//input[@name='subjectbox']"));
		subject.sendKeys(subjectText);

		WebElement body = driver.findElement(By.xpath("//div[contains(@class,'editable ')]"));
		body.sendKeys(bodyText);

		WebElement options = driver.findElement(By.xpath("//div[starts-with(@class,'J-JN-M-I')]"));
		options.click();

		WebElement label = driver.findElement(By.xpath("//div[contains(text(),'Label')]"));
		label.click();

		WebElement social = driver.findElement(By.xpath("//div[@class='J-LC-Jz']"));
		social.click();

		WebElement send = driver.findElement(By.xpath("//div[@class='dC']"));
		send.click();

		WebElement socialInbox = driver.findElement(By.xpath("//div[@class='aAy aKe-aLe']"));
		socialInbox.click();



		List<WebElement> clickMail = driver.findElements(By.xpath("//span[@class='bog']"));
		int size = clickMail.size();
		for (int i = 0; i < size; i++) {
			if (clickMail.get(i).getText().contains("Test Mail")) {
				clickMail.get(i).click();
				break;
			}
		}
		

		WebElement mailSubject = driver.findElement(By.xpath("//h2[@class='hP']"));
		String actualSubjectText = mailSubject.getText();
		assertEquals(actualSubjectText, subjectText);

		WebElement mailbody = driver.findElement(By.xpath("//div[contains(text(),'Test EMail Body')]"));
		String actualbodyText = mailbody.getText();
		assertEquals(actualbodyText, bodyText);
	}

}
