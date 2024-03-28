package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testAccessibleWithoutLogIn () {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals(driver.getTitle(), "Login");
	}

	@Test
	void testLoginFlow() {
		doMockSignUp("Large File", "Test", "SULI", "123");
		doLogIn("SULI", "123");
		
		// Logged in with new user created
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals(driver.getTitle(), "Home");
		
		// Log out
		driver.findElement(By.id("logout")).click();
		Assertions.assertEquals(driver.getTitle(), "Login");
		
		// Home page is no longer accessible.
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals(driver.getTitle(), "Login");
	}
	
	@Test
	void testAddingNote() {
		doMockSignUp("Large File", "Test", "SULI", "123");
		doLogIn("SULI", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		WebElement noteElement = driver.findElement(By.id("nav-notes-tab"));
		noteElement.click();

		WebElement addNoteButton= driver.findElement(By.id("add-note"));
		addNoteButton.click();
		
		String noteTitle = "Example Title";
		String noteDescription = "Example Description";
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitleElement = driver.findElement(By.id("note-title"));
		inputTitleElement.click();
		inputTitleElement.sendKeys(noteTitle);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescriptionElement = driver.findElement(By.id("note-description"));
		inputDescriptionElement.click();
		inputDescriptionElement.sendKeys(noteDescription);
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSubmit")));
		WebElement submitNotElement = driver.findElement(By.id("noteSubmit"));
		submitNotElement.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
		Assertions.assertTrue(driver.findElement(By.id("note-title")).getText().contains(noteTitle));
	}

	@Test
	public void testEditNote(){
		testAddingNote();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note")));
		WebElement editNote = driver.findElement(By.id("edit-note"));
		editNote.click();

		String noteTitle = "Update Title";
		String noteDescription = "Update Description";
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitleElement = driver.findElement(By.id("note-title"));
		inputTitleElement.click();
		inputTitleElement.clear();
		inputTitleElement.sendKeys(noteTitle);
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescriptionElement = driver.findElement(By.id("note-description"));
		inputDescriptionElement.click();
		inputDescriptionElement.clear();
		inputDescriptionElement.sendKeys(noteDescription);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-note-button")));
		WebElement submitNoteElement = driver.findElement(By.id("submit-note-button"));
		submitNoteElement.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
		Assertions.assertTrue(driver.findElement(By.id("note-title")).getText().contains(noteTitle));
		Assertions.assertTrue(driver.findElement(By.id("note-description")).getText().contains(noteDescription));
	}

	@Test
	public void testDeleteNote(){
		testAddingNote();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note")));
		WebElement deleteNoteElement = driver.findElement(By.id("delete-note"));
		deleteNoteElement.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		WebElement noteTable = driver.findElement(By.id("noteTable"));
		List<WebElement> notesList = noteTable.findElements(By.tagName("tbody"));

		Assertions.assertEquals(0, notesList.size());
	}

	@Test
	public void testCredential() throws InterruptedException {
		
		// add credential testing
		doMockSignUp("Large File", "Test", "SULI", "123");
		doLogIn("SULI", "123");


		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		WebElement credentialElement= driver.findElement(By.id("nav-credentials-tab"));
		credentialElement.click();

		String credentialPassword = "123";
		String credentialUser = "SULI";
		String inputCredentialUrl = "test-url";
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential")));
		WebElement addCredentialsElement= driver.findElement(By.id("add-new-credential"));
		addCredentialsElement.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement urlElement = driver.findElement(By.id("credential-url"));
		urlElement.click();
		urlElement.sendKeys(inputCredentialUrl);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement usernameElement = driver.findElement(By.id("credential-username"));
		usernameElement.click();
		usernameElement.sendKeys(credentialUser);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement passwordElement = driver.findElement(By.id("credential-password"));
		passwordElement.click();
		passwordElement.sendKeys(credentialPassword);
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-btn")));
		WebElement submitCredentialElement = driver.findElement(By.id("add-credential-btn"));
		submitCredentialElement.click();

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credentialListElement = credentialsTable.findElements(By.tagName("tbody"));

		Assertions.assertEquals(1, credentialListElement.size());
		
		// edit credential testing
		String inputCredentialUrlEdited = "url-updated";
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-btn")));
		WebElement editCredentialsElement= driver.findElement(By.id("edit-credential-btn"));
		editCredentialsElement.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		urlElement.click();
		urlElement.sendKeys(inputCredentialUrl);
		urlElement.sendKeys(inputCredentialUrlEdited);
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-credential-button")));
		submitCredentialElement.click();

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("credential-url")).getText().contains(inputCredentialUrlEdited));
		
		//delete credential testing
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential-btn")));
		WebElement deleteCredentialsElement= driver.findElement(By.id("delete-credential-btn"));
		deleteCredentialsElement.click();

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		WebElement credentialTableElement = driver.findElement(By.id("credentialTable"));
		List<WebElement> credentialListDeleted = credentialTableElement.findElements(By.tagName("tbody"));
		Assertions.assertEquals(0, credentialListDeleted.size());
	}
}
