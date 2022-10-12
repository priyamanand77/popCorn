package configAutomation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

public class MainConfigV1_2 {
	static Map<String, String> parent = new HashMap<String, String>();

	static Map<String, Integer> arrCount = new HashMap<String, Integer>();

	static WebDriver driver;

	static Map<String, Integer> arraycountnumber = new HashMap<String, Integer>();

	static String mainLocation = "";
	static String firstNode = "";
	static int currentNoOfItem = -1;
	static int prevNoOfItem = -1;

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
		actions.moveByOffset(500, 0).click().build().perform();
		actions.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String path = "";

		String con = nextKey;
		Boolean iterate = true;

		String newcon = "";
		do {
			if (parent.get(con) != firstNode) {

				if (arrCount.containsKey(con)) {
					newcon = con + "." + arrCount.get(con);

					path = "." + newcon + path;

				} else {
					path = "." + con + path;

				}
				con = parent.get(con);
			} else {
				path = "." + con + path;

				con = parent.get(con);
				iterate = false;
			}
		} while (iterate);

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
			val = jsonobj.get(key).toString();
			writeInNumberString(location, val);

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

	public static void openCloseObject(String location) {

		List<WebElement> ele = driver.findElements(By.xpath("(//div[@data-schemapath='" + location
				+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"));
		if (!ele.isEmpty()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
					.click();
		} else {
			Actions actions = new Actions(driver);
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();

			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
					.click();
		}

	}

	public static void closeObject(String location) {

		List<WebElement> ele = driver.findElements(By.xpath("(//div[@data-schemapath='" + location
				+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"));
		if (!ele.isEmpty()) {

			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
					.click();
		} else {
			Actions actions = new Actions(driver);
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
			driver.findElement(By.xpath("(//div[@data-schemapath='" + location
					+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-edit_properties json-editor-btntype-properties'])[1]"))
					.click();
		}

	}

	public static WebElement getWritingObjectWebEle(String location) {
		WebElement ele = driver.findElement(By.xpath("//div[@data-schemapath='" + location
				+ "' and @data-schematype='object']//span[@style='margin: 0px 0px 0px 10px;']//input[@type='text']"));
		return ele;
	}

	public static void writeInOBject(WebElement write, String value) {
		write.sendKeys(value);

	}

	public static void addButton(String location) {
		driver.findElement(By.xpath("(//div[@data-schemapath='" + location
				+ "' and @data-schematype='object']//button[@type='button' and  @class='json-editor-btn-add json-editor-btntype-add'])[1]"))
				.click();
	}

	public static void clearPreVal(WebElement ele) {
		ele.click();
		Actions action = new Actions(driver);
		action.keyDown(Keys.LEFT_CONTROL).sendKeys(ele, "a").keyUp(Keys.LEFT_CONTROL).sendKeys(Keys.BACK_SPACE).build()
				.perform();
	}

	public static WebElement selectType(String location, String currVal) {

		WebElement ele = driver.findElement(By.xpath("//div[@data-schemapath='" + location
				+ "']//label[contains(text(),'" + currVal + "')]//following-sibling::select"));

		return ele;

	}

	public static void getKey(JSONObject jsonobj, String pre) {
		Iterator<?> keys;
		String nextKey;

		keys = jsonobj.keys();
		while (keys.hasNext()) {
			nextKey = (String) keys.next();

			try {

				if (jsonobj.get(nextKey) instanceof JSONObject) {
					System.out.println();
					System.out.println("object --> " + nextKey);

					parent.put(nextKey, pre);

					String path = getPath(nextKey);
					String pathmain = mainLocation + path;
					System.out.println("Parent : " + pathmain + " \npre :" + pre + " \ncurrent :" + nextKey);

					String prePath = getPrePath(pathmain);
					System.out.println("Previous path " + prePath);

					openCloseObject(prePath);
					WebElement writeInObj = getWritingObjectWebEle(prePath);
					clearPreVal(writeInObj);
					writeInOBject(writeInObj, nextKey);
					addButton(prePath);
					closeObject(prePath);

					WebElement selectTypeOfVariable = selectType(pathmain, nextKey);
					clickObject(selectTypeOfVariable);

					try {
						getKey(jsonobj.getJSONObject(nextKey), nextKey);

						System.out.println("after object :" + nextKey);

					} catch (Exception e) {
						System.out.println("object found");

					}

				} else if (jsonobj.get(nextKey) instanceof JSONArray) {
					System.out.println();
					System.out.println("array -- >" + nextKey);
					JSONArray jsonArray = jsonobj.getJSONArray(nextKey);

					if (jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {

							String jsonArrayString = jsonArray.get(i).toString();
							JSONObject innerjsonObject = new JSONObject(jsonArrayString);
							parent.put(nextKey, pre);
							String path = getPath(nextKey);
							String pathmain = mainLocation + path;
							System.out.println("Parent : " + pathmain + " \npre :" + pre + " \ncurrent :" + nextKey);
							String prePath = getPrePath(pathmain);

							System.out.println("Previous path " + prePath);

							openCloseObject(prePath);
							WebElement writeInObj = getWritingObjectWebEle(prePath);
							clearPreVal(writeInObj);
							writeInOBject(writeInObj, nextKey);
							addButton(prePath);
							closeObject(prePath);
							WebElement selectTypeOfVariable = selectType(pathmain, nextKey);
							clickArray(selectTypeOfVariable);
							goUp();

							try {
								getKey(innerjsonObject, nextKey);
								System.out.println("after array :" + nextKey);
							} catch (Exception e) {
								System.out.println("array found");
								// TODO: handle exception
							}

						}
					} else {
						parent.put(nextKey, pre);
						String path = getPath(nextKey);
						String pathmain = mainLocation + path;
						System.out.println("Parent : " + pathmain + " \npre :" + pre + " \ncurrent :" + nextKey);
						String prePath = getPrePath(pathmain);

						System.out.println("Previous path " + prePath);

						openCloseObject(prePath);
						WebElement writeInObj = getWritingObjectWebEle(prePath);
						clearPreVal(writeInObj);
						writeInOBject(writeInObj, nextKey);
						addButton(prePath);
						closeObject(prePath);
						WebElement selectTypeOfVariable = selectType(pathmain, nextKey);
						clickArray(selectTypeOfVariable);
						goUp();

					}

				} else if (jsonobj.get(nextKey) instanceof Boolean) {

					System.out.println();
					System.out.println("boolean ---> " + nextKey);

					parseJson(jsonobj, nextKey);

					parent.put(nextKey, pre);

					String path = getPath2(nextKey);

					String pathmain = mainLocation + path;
					System.out.println("Parent : " + pathmain + " \npre " + pre + " \ncurrent " + nextKey);

					String dotpath = getDotPrePathLocation(pathmain);
					System.out.println("print dot pre path :" + dotpath);

					String prePath = getPrePath(pathmain);

					System.out.println("Previous path " + prePath);

					openCloseObject(prePath);
					WebElement writeInObj = getWritingObjectWebEle(prePath);
					clearPreVal(writeInObj);
					writeInOBject(writeInObj, nextKey);
					addButton(prePath);
					closeObject(prePath);

					WebElement selectTypeOfVariable = selectType(pathmain, nextKey);
					clickBoolean(selectTypeOfVariable);

					parseJson2(jsonobj, nextKey, pathmain, "Boolean");
					goUp();

					try {
						getKey(jsonobj.getJSONObject(nextKey), nextKey);
						System.out.println("after boolian " + nextKey);
					} catch (Exception e) {
						System.out.println(" boolean value");
						// TODO: handle exception
					}

				} else if (jsonobj.get(nextKey) instanceof String) {
					System.out.println();

					System.out.println("string ---> " + nextKey);

					parseJson(jsonobj, nextKey);

					parent.put(nextKey, pre);

					String path = getPath2(nextKey);

					String pathmain = mainLocation + path;
					System.out.println("Parent : " + pathmain + " pre " + pre + " current " + nextKey);

					String dotpath = getDotPrePathLocation(pathmain);
					System.out.println("print dot pre path :" + dotpath);

					String prePath = getPrePath(pathmain);

					System.out.println("Previous path " + prePath);

					openCloseObject(prePath);
					WebElement writeInObj = getWritingObjectWebEle(prePath);

					clearPreVal(writeInObj);

					writeInOBject(writeInObj, nextKey);
					addButton(prePath);
					closeObject(prePath);
					WebElement selectTypeOfVariable = selectType(pathmain, nextKey);
					clickString(selectTypeOfVariable);
					parseJson2(jsonobj, nextKey, pathmain, "String");
					goUp();

					try {
						getKey(jsonobj.getJSONObject(nextKey), nextKey);
						System.out.println("after string " + nextKey);
					} catch (Exception e) {
						System.out.println("handled string exception");
						// TODO: handle exception
					}

				} else if (jsonobj.get(nextKey) instanceof Number) {
					System.out.println();
					System.out.println("Number ---> " + nextKey);

					parseJson(jsonobj, nextKey);

					parent.put(nextKey, pre);

					String path = getPath2(nextKey);

					String pathmain = mainLocation + path;
					System.out.println("Parent : " + pathmain + " \npre " + pre + " \ncurrent " + nextKey);

					String dotpath = getDotPrePathLocation(pathmain);
					System.out.println("print dot pre path :" + dotpath);

					String prePath = getPrePath(pathmain);

					System.out.println("Previous path " + prePath);

					openCloseObject(prePath);
					WebElement writeInObj = getWritingObjectWebEle(prePath);
					clearPreVal(writeInObj);
					writeInOBject(writeInObj, nextKey);
					addButton(prePath);
					closeObject(prePath);
					WebElement selectTypeOfVariable = selectType(pathmain, nextKey);
					clickNumber(selectTypeOfVariable);

					parseJson2(jsonobj, nextKey, pathmain, "Number");
					goUp();

					try {
						getKey(jsonobj.getJSONObject(nextKey), nextKey);
						System.out.println("afetr number " + nextKey);
					} catch (Exception e) {
						System.out.println(" its number ");
						// TODO: handle exception
					}
				}
			} catch (Exception e) {
				System.out.println(e);

			}

		}

	}

	@SuppressWarnings("deprecation")
	public static void openBrowser(String url, int env) {
		System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

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

		// driver.get(configURL);
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

		String json = "{\r\n"
				+ "	\"gtouch\": {\r\n"
				+ "		\"is_enabled\": true,\r\n"
				+ "\r\n"
				+ "		\"switch_user\": true,\r\n"
				+ "		\"user_mismatch_msg\": false,\r\n"
				+ "		\"minimize_maximize_in_hrs\": 0.25,\r\n"
				+ "		\"defaultCount\": 0,\r\n"
				+ "		\"frequency\": 0,\r\n"
				+ "		\"time_interval_in_hrs\": 0,\r\n"
				+ "		\"repeat_in_interval\": true,\r\n"
				+ "		\"ga_analytics_id\": \"gtouch_ipad\",\r\n"
				+ "		\"conviva_analytics_id\": \"gtouch_ipad\",\r\n"
				+ "		\"redirection_enabled\": true,\r\n"
				+ "		\"b2b_partner_attribution\": false,\r\n"
				+ "		\"allowed_login_type\": \"sso\",\r\n"
				+ "		\"pip_enable\": false,\r\n"
				+ "		\"partner_back_button_text\": \"Back to Gtouch App\",\r\n"
				+ "		\"who_is_watching\": false\r\n"
				+ "	}\r\n"
				+ "}";

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

		System.out.print("Press 1 for new node \nPress 2 for inside existing node : ");
		int option = sc.nextInt();
		sc.nextLine();

		// System.out.println("env link " + envLink + "\n configurl " + configURL + "\n
		// env :" + env);

		openBrowser(envLink, env);

		if (option == 1) {
			System.out.println("Enter main node : ");
			newNode = sc.nextLine();
			firstNode = newNode;
			mainLocation = "root";

		} else {

			System.out.println("Enter main node from root node : ");
			newNode = sc.nextLine();

			String path = "";
			String s1 = newNode;
			String[] words = s1.split("\\.");
			for (int i = 0; i < words.length; i++) {
				if (i < words.length - 1) {
					path = path + "." + words[i];
				} else {
					firstNode = words[i];
				}
			}
			mainLocation = path.substring(1);
			System.out.println("print path " + mainLocation);

		}

		for (int loop = 0; loop < noOfLinks; loop++) {
			openConfigUrl(configURL[loop]);

			try {
				JSONObject jsonObject = new JSONObject(json);
				getKey(jsonObject, firstNode);
			} catch (Exception e) {
				System.out.println("INVALID JSON");// TODO: handle exception
			}

			System.out.println("Want to update ? (press y for yes , n for no ) : ");
			String update = br.readLine();

			if (update.equalsIgnoreCase("y")) {
				update();
				updated();

			} else {
				doNotUpdate();
			}

		}

	}

}

// root.static_view.0.main
//	    https://dsfqa.sonyliv.com/wp-admin/post.php?post=30881&action=edit
