package configAutomation.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Update {
	static Map<String, String> parent = new HashMap<String, String>();

	static Map<String, Integer> arrCount = new HashMap<String, Integer>();
	static Map<String, Integer> arraycountnumber = new HashMap<String, Integer>();

	static WebDriver driver;
	static int array_count;
	static String mainLocation = "";
	static String firstNode = "";
	static int currentNoOfItem = -1;
	static int prevNoOfItem = -1;
	static int arrcount = 0;
	static String isUpdate = "";

	public static int incrementarrcount() {
		arrcount++;
		return arrcount;
	}

	static int array_type = 1;

	static String[] allElements = { "", "BEHIND_THE_SCENES", "CATCH_UP", "CLIP", "DOCUMENTARY", "EPISODE", "EVENT",
			"FULL_MATCH", "HIGHLIGHTS", "MOVIE", "MOVIE_PROMOTION", "MOVIE_TEASER", "MOVIE_TRAILER", "MUSIC",
			"MUSIC_VIDEOS", "REPLAY", "SECOND_SCREEN", "SHORT_FILMS", "SPORTS_CLIPS", "STUDIO_SHOW", "VIDEOS" }; // if
																													// its
																													// array
																													// of
																													// String
																													// write
																													// it
																													// inside
																													// this

	public static void resetarrcount() {
		currentNoOfItem = -1;
		prevNoOfItem = -1;

	}

	static int objcount = 0;

	public static void resetobjcount() {
		objcount = 0;

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

	public static String getPath(String nextKey) {
		String path = "";
		String con = nextKey;
		Boolean iterate = true;
		do {
			if (parent.get(con) != firstNode) {
				path = "." + con + path;
				con = parent.get(con);
			} else {
				path = "." + con + path;
				con = parent.get(con);
				iterate = false;
			}
		} while (iterate);

		return path;

	}

	public static void goUp() {
		
		Actions actions = new Actions(driver);
		actions.moveByOffset(360, 0).click().build().perform();
		actions.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).keyUp(Keys.LEFT_CONTROL).build().perform();
		String e = driver.findElement(By.xpath("//*[@id=\"acf-group_tags\"]/div[1]/div/button[3]")).getAttribute("aria-expanded");
		System.out.println(e);
		if(e.equalsIgnoreCase("true"))
		{
			driver.findElement(By.xpath("//*[@id=\"acf-group_tags\"]/div[1]/div/button[3]/span[2]")).click();
		}
		
	
	}

	public static String getPrePath(String pathmain) {
		String prePath = "";

		String s1 = pathmain;
		String[] words = s1.split("\\.");
		for (int i = 0; i < words.length - 1; i++) {
			prePath = prePath + "." + words[i];
		}

		prePath = prePath.substring(1);
		return prePath;

	}

	public static String getPath2(String nextKey) {
		System.out.println("isnide get path 2");
		String path = "";
		String con = nextKey;
		Boolean iterate = true;

		String newcon = "";

		do {
			if (arrCount.containsKey(con)) {
				System.out.println("inside array count");
				newcon = con + "." + arrCount.get(con);

				path = "." + newcon + path;

			} else {
				System.out.println("inside else array count");

				path = "." + con + path;

			}
			con = parent.get(con);

			System.out.println(path);

		} while (con != firstNode);

		String x = nextKey;
		System.out.println();
		System.out.println("____________________________");
		while (x != firstNode) {
			System.out.println(x + "-->" + parent.get(x));
			x = parent.get(x);

		}
		System.out.println("_________________________________");
		System.out.println();
		System.out.println();

		for (Map.Entry<String, Integer> paiEntry : arrCount.entrySet()) {
			System.out.println(paiEntry.getKey() + "->" + paiEntry.getValue());
		}

		return path;
	}

	public static String getDotPrePathLocation(String pathmain) {
		String path21 = "";

		String dotPath = "";
		String s121 = pathmain;
		String[] words21 = s121.split("\\.");
		for (int i = 0; i < words21.length; i++) {
			if (i == 0) {
				path21 = words21[i];
				dotPath = words21[i];
			} else if (i < words21.length - 1) {
				path21 = path21 + "[" + words21[i] + "]";
				dotPath = dotPath + "." + words21[i];
			}

		}
		return dotPath;
	}

	public static void parseJson2(JSONObject jsonobj, String key, String location, String type) {
		String val;
		if (type != "Boolean") {
			WebElement writeInObj = getWritingObjectWebEle(location);
			clearPreVal(writeInObj);
			val = jsonobj.get(key).toString();
			writeInNumberString(location, val);
			goUp();

		} else {
			val = jsonobj.get(key).toString();
			if (val == "true") {
				WebElement ele = selectBoolian(location);
				clickBooleanTrue(ele);

			} else {
				WebElement ele = selectBoolian(location);
				clickBooleanFalse(ele);
			}

		}
		System.out.println(jsonobj.get(key));

	}

	public static String makeLocation(String location) {
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

		return path;
	}

//	public static void clickOnAddArrayButton(String location, String level) {
//		driver.findElement(By.xpath("((//div[@data-schemapath='" + location
//				+ "'])[4]//button[@class='json-editor-btn-add json-editor-btntype-add'])[" + level + "]")).click();
//
//	}

	public static void writeInNumberString(String location, String val) {
		String path = makeLocation(location);
		System.out.println("print path " + path);
		driver.findElement(By.xpath("//div[@data-schemapath='" + location + "']//input[@name='" + path + "']"))
				.sendKeys(val);
		
	
		

	}

	public static WebElement selectBoolian(String location) {
		String path = makeLocation(location);

		System.out.println("print path " + path);
		WebElement ele = driver.findElement(By.xpath("//select[@name='" + path + "']"));
		return ele;
	}

//	public static void openCloseObject(String location) {
//
//		List<WebElement> ele = driver.findElements(By.xpath("(//div[@data-schemapath='" + location
//				+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"));
//		if (!ele.isEmpty()) {
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
//					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
//					.click();
//		} else {
//			Actions actions = new Actions(driver);
//			actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
//
//			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
//					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
//					.click();
//		}
//
//	}

//	public static void closeObject(String location) {
//
//		List<WebElement> ele = driver.findElements(By.xpath("(//div[@data-schemapath='" + location
//				+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"));
//		if (!ele.isEmpty()) {
//
//			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
//					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
//					.click();
//		} else {
//			Actions actions = new Actions(driver);
//			actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
//			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
//					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
//					.click();
//		}
//
//	}

	public static WebElement getWritingObjectWebEle(String location) {
		WebElement ele = driver.findElement(By.xpath("//div[@data-schemapath='" + location
				+ "' and @data-schematype='object']//span[@style='margin: 0px 0px 0px 10px;']//input[@type='text']"));
		return ele;
	}

//	public static void writeInOBject(WebElement write, String value) {
//		write.sendKeys(value);
//
//	}

//	public static void addButton(String location) {
//		driver.findElement(By.xpath("(//div[@data-schemapath='" + location
//				+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-add json-editor-btntype-add'])[1]"))
//				.click();
//	}

	public static void clearPreVal(WebElement ele) {
		ele.click();
		Actions action = new Actions(driver);
		action.keyDown(Keys.LEFT_CONTROL).sendKeys(ele, "a").keyUp(Keys.LEFT_CONTROL).sendKeys(Keys.BACK_SPACE).build()
				.perform();
	}

//	public static WebElement selectType(String location, String currVal) {
//
//		WebElement ele = driver.findElement(By.xpath("//div[@data-schemapath='" + location
//				+ "']//label[contains(text(),'" + currVal + "')]//following-sibling::select"));
//
//		return ele;
//
//	}

	public static WebElement writeInNumber(String location) {
		WebElement ele = driver.findElement(By.xpath(
				"//div[@data-schemapath='" + location + "' and @data-schematype='number']//input[@type='text']"));
		return ele;
	}

	public static WebElement writeInText(String location) {
		WebElement ele = driver.findElement(
				By.xpath("//div[@data-schemapath='root.gdpr_ROKU_TV_policies.0.terms_of_use' ]//input[@type='text']"));
		return ele;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void getKeyValue(String json[]) {

		String val = "";
		Map<String, String> elements = new TreeMap<String, String>();
		for (int i = 0; i < json.length; i = i + 2) {
			elements.put(json[i], json[i + 1]);
		}

		for (Map.Entry<String, String> e : elements.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
			if (e.getValue().equalsIgnoreCase("true") || e.getValue().equalsIgnoreCase("false")) {
				val = e.getValue();
				if (val == "true") {
					WebElement ele = selectBoolian(e.getKey());
					clickBooleanTrue(ele);

				} else {
					WebElement ele = selectBoolian(e.getKey());
					clickBooleanFalse(ele);
				}
				goUp();

			} else {

				if (isNumeric(e.getValue())) {
					WebElement writeInObj = writeInNumber(e.getKey());
					clearPreVal(writeInObj);
					val = e.getValue();
					writeInNumberString(e.getKey(), val);
					goUp();
				} else {
					WebElement writeInObj = writeInText(e.getKey());
					clearPreVal(writeInObj);
					val = e.getValue();
					writeInNumberString(e.getKey(), val);
					goUp();
				}

				System.out.println();

			}

		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	@SuppressWarnings("deprecation")
	public static void openBrowser(String url, int env, int opt) throws NumberFormatException, IOException {
		System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		if (opt == 1) {
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
		} else {
			driver.findElement(By.id("user_login")).sendKeys("sri.chandana.uppu@accenture.com");
			if (env == 3) {
				driver.findElement(By.id("user_pass")).sendKeys("VinodRukmini@25299");
			}
		}

		driver.findElement(By.id("wp-submit")).click();
		driver.findElement(By.xpath("//div[contains(text(),'UI Builder')]")).click();

		// driver.get(configURL);
	}

	public static void update() {
//		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
//		actions.moveByOffset(400, 0).click().build().perform();
//		actions.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).build().perform();
		goUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("publish")));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.findElement(By.id("publish")).click();

	}

	public static void doNotUpdate() {

	}

	public static void openConfigUrl(String configURL) {
		driver.get(configURL);
	}

	public static void updated() {
		// WebElement ele =
		// driver.findElement(By.xpath("//p[contains(text(),'Configuration
		// updated.')]"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Configuration updated.')]")));

	}

	public static void main(String[] args) throws IOException {

		InputStreamReader r = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(r);

		String[] json = { "root.fastScrubConfig.should_1x_display", "true", "root.fastScrubConfig.one_click_jump", "20",
				"root.fastScrubConfig.scrubData.0.jump", "20", "root.fastScrubConfig.scrubData.1.jump", "40",
				"root.fastScrubConfig.scrubData.2.jump", "80" };

		System.out.println();
		for (Map.Entry<String, String> paiEntry : parent.entrySet()) {
			System.out.println(paiEntry.getKey() + "->" + paiEntry.getValue());
		}
		System.out.println();
		for (Map.Entry<String, Integer> paiEntry : arrCount.entrySet()) {
			System.out.println(paiEntry.getKey() + "->" + paiEntry.getValue());
		}

		String newNode = "";
		// String oldNode = "";
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("Enter option  :\n1. QA\n2. PP\n3. Prod\n4. Stg");
		int env = sc.nextInt();
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

		sc.nextLine();
		String[] configURL = new String[100];
		String c = "y";
		int noOfLinks = 0;
		System.out.println("Enter URLs for the config site : ");
		while (c.equalsIgnoreCase("y")) {
			System.out.print("Enter link " + (noOfLinks + 1) + " : ");

			configURL[noOfLinks] = br.readLine();
			noOfLinks = noOfLinks + 1;
			System.out.println("Do u want to enter more links ?(press y for yes , n for no ) : ");
			c = br.readLine();

		}

		System.out.println("Who is this \n1. priyam \n2. Chandana");
		int opt = Integer.parseInt(br.readLine());

		openBrowser(envLink, env, opt);

		for (int loop = 0; loop < noOfLinks; loop++) {

			arrCount.clear();

			openConfigUrl(configURL[loop]);

			try {

				getKeyValue(json);
			} catch (Exception e) {
				System.out.println("INVALID JSON"); // TODO: handle exception
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