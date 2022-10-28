package configAutomation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;;

public class ArrayConfig {

	static Map<String, String> parent = new HashMap<String, String>();

	static Map<String, Integer> arrCount = new HashMap<String, Integer>();

	static WebDriver driver;

	static Map<String, Integer> arraycountnumber = new HashMap<String, Integer>();

	static String mainLocation = "";
	static String firstNode = "";
	static int currentNoOfItem = -1;
	static int prevNoOfItem = -1;
	static String isUpdate = "";
	static int arrcount = 0;

	public static int incrementarrcouhnt() {
		arrcount++;
		return arrcount;
	}

	public static void parseJson(JSONObject jsonobj, String key) {

		System.out.println(jsonobj.get(key));
	}

	public static void clickBoolean(WebElement d) {
		Select s1 = new Select(d);
		s1.selectByVisibleText("boolean");

	}

	public static void clickString(WebElement d) {
		Select s1 = new Select(d);
		s1.selectByVisibleText("string");

	}

	public static void clickNumber(WebElement d) {
		Select s1 = new Select(d);
		s1.selectByVisibleText("number");

	}

	public static void clickObject(WebElement d) {
		Select s1 = new Select(d);
		s1.selectByVisibleText("object");

	}

	public static void clickArray(WebElement d) {
		Select s1 = new Select(d);
		s1.selectByVisibleText("array");

	}

	public static void clickBooleanTrue(WebElement d) {
		Select s1 = new Select(d);
		s1.selectByVisibleText("true");

	}

	public static void clickBooleanFalse(WebElement d) {
		Select s1 = new Select(d);
		s1.selectByVisibleText("false");

	}

	@SuppressWarnings("deprecation")
	public static void openBrowser(String url, int env) {
		System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.id("user_login")).sendKeys("priyam.anand");
		if (env == 3) {
			driver.findElement(By.id("user_pass")).sendKeys("Divakar@1999");
		} else if (env == 2) {
			driver.findElement(By.id("user_pass")).sendKeys("Divakar@2002");
		} else if (env == 1) {
			driver.findElement(By.id("user_pass")).sendKeys("Divakar@2005");
		} else if (env == 4) {
			driver.findElement(By.id("user_pass")).sendKeys("Divakar@2005");
		}

		driver.findElement(By.id("wp-submit")).click();
		driver.findElement(By.xpath("//div[contains(text(),'UI Builder')]")).click();

	}
	
	public static void openConfigUrl(String configURL) {
		driver.get(configURL);
	}


	public static void goUp(WebElement e) {
		Actions actions = new Actions(driver);
		//actions.moveByOffset(900, -2)
		actions.moveToElement(e, 500, 0)
		.click().build().perform();
		actions.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).keyUp(Keys.LEFT_CONTROL).build().perform();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		actions.moveByOffset(400, 0).click().build().perform();
//		actions.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).keyUp(Keys.LEFT_CONTROL).build().perform();

	}

	public static void clickOnAddArrayButton(String location, String level) {
		driver.findElement(By.xpath("((//div[@data-schemapath='" + location
				+ "'])[4]//button[@class='json-editor-btn-add json-editor-btntype-add'])[" + level + "]")).click();

	}

	public static WebElement selectType(String location, String currVal) {

		WebElement ele = driver.findElement(By.xpath("//div[@data-schemapath='" + location
				+ "']//label[contains(text(),'" + currVal + "')]//following-sibling::select"));

		return ele;

	}

	public static void arrayMaker(String location, String val, int pos) {
		clickOnAddArrayButton(location, "1");
		String path = location + "." + String.valueOf(pos);
		pos = pos + 1;
		String currVal = "item " + String.valueOf(pos);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-schemapath='" + path
				+ "']//label[contains(text(),'" + currVal + "')]//following-sibling::select")));

		WebElement selectTypeOfVariable = selectType(path, currVal);

		clickString(selectTypeOfVariable);

		writeInNumberString(path, val);
		
	
			goUp(selectTypeOfVariable);
		
		
		
	

	}

	public static void writeInNumberString(String location, String val) {
		String path = "";
		String s1 = location;
		String[] words = s1.split("\\.");
		for (int i = 0; i < words.length; i++) {
			if (i == 0) {
				path = words[i];
			} else {
				path = path + "[" + words[i] + "]";
			}

		}
		System.out.println(path + "   --->   " + val);
		driver.findElement(By.xpath("//div[@data-schemapath='" + location + "']//input[@name='" + path + "']"))
				.sendKeys(val);

	}

	public static void update() {
		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		actions.moveByOffset(800, 0).click().build().perform();
		actions.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("publish")));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.findElement(By.id("publish")).click();

	}

	public static void updated() {
		// WebElement ele =
		// driver.findElement(By.xpath("//p[contains(text(),'Configuration
		// updated.')]"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Configuration updated.')]")));

	}

	public static void doNotUpdate() {

	}

	public static void main(String[] args) throws IOException {

		InputStreamReader r = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(r);

		String[] allElements = { "","BEHIND_THE_SCENES", "CATCH_UP", "CLIP", "DOCUMENTARY", "EPISODE", "EVENT",
				"FULL_MATCH", "HIGHLIGHTS", "MOVIE", "MOVIE_PROMOTION", "MOVIE_TEASER", "MOVIE_TRAILER", "MUSIC",
				"MUSIC_VIDEOS", "REPLAY", "SECOND_SCREEN", "SHORT_FILMS", "SPORTS_CLIPS", "STUDIO_SHOW", "VIDEOS" };

		String newNode = "";
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("Enter option  :\n1. QA\n2. PP\n3. Prod \n4. Stg");
		int env = Integer.parseInt(br.readLine());
		String envLink = "";
		if (env == 1) {
			envLink = "https://dsfqa.sonyliv.com/wp-login.php?itsec-hb-token=ui_builder_admin&redirect_to=https%3A%2F%2Fdsfqa.sonyliv.com%2F&reauth=1";
		} else if (env == 2) {
			envLink = "https://dsfpreprod.sonyliv.com/wp-login.php?itsec-hb-token=ui_builder_admin&redirect_to=https%3A%2F%2Fdsfpreprod.sonyliv.com%2F&reauth=1";
		} else if (env == 3) {
			envLink = "https://dsf.sonyliv.com/wp-login.php?itsec-hb-token=ui_builder_admin&redirect_to=https%3A%2F%2Fdsf.sonyliv.com%2F&reauth=1";
		} else if (env == 4) {
			envLink = "https://dsfstg.sonyliv.com/wp-login.php?itsec-hb-token=ui_builder_admin&redirect_to=https%3A%2F%2Fdsfstg.sonyliv.com%2F&reauth=1";
		}

		//sc.nextLine();

		List<String> links = new ArrayList<String>();
		boolean morelink = true;
		int ii = 1;
		while (morelink) {
			System.out.println("Enter link " + ii);
			String link =br.readLine();
			links.add(link);
			ii++;

			System.out.println("Want to enter more : ");
			String choose =br.readLine();
			if (choose.equalsIgnoreCase("n")) {
				morelink = false;
			}
		}

		System.out.print("Press 1 for new node \nPress 2 for inside existing node : ");
		int option = Integer.parseInt(br.readLine());
		//sc.nextLine();

		if (option == 1) {
			System.out.println("Enter main node : ");
			newNode = br.readLine();
		} else {
			System.out.println("Enter main node from root node : ");
			newNode = br.readLine();

		}
		
		openBrowser(envLink, env);

		for (int no = 0; no < links.size(); no++) {

			String configURL = links.get(no);
			
			openConfigUrl(configURL);

			System.out.println("env link " + envLink + "\n configurl " + configURL + "\n env :" + env);

			

			if (option == 1) {

				firstNode = newNode;
				mainLocation = "root";

			} else {

				String[] singleElement = allElements;

				for (int i = 0; i < singleElement.length; i++) {

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					arrayMaker(newNode, singleElement[i], i);
				}

			}

			if (isUpdate.isEmpty()) {
				System.out.println("Want to update ? (press y for yes , n for no ) : ");
				String update = br.readLine();
				isUpdate = update;
				if (update.equalsIgnoreCase("y")) {
					update();
					updated();

				} else {
					doNotUpdate();
				}

			} else {
				String update = isUpdate;
				if (update.equalsIgnoreCase("y")) {
					update();
					updated();

				} else {
					doNotUpdate();
				}

			}

		}

	}

}

//root.main.arrayOfString
//https://dsf.sonyliv.com/wp-admin/post.php?post=46051&action=edit
