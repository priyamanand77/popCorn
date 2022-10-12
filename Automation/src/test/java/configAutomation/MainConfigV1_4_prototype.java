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

public class MainConfigV1_4_prototype {
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

	static String[] allElements = {}; // if its array of String write it inside this

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
		actions.moveByOffset(1000, 0).click().build().perform();
		actions.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).keyUp(Keys.LEFT_CONTROL).build().perform();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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

	public static void clickOnAddArrayButton(String location, String level) {
		driver.findElement(By.xpath("((//div[@data-schemapath='" + location
				+ "'])[4]//button[@class='json-editor-btn-add json-editor-btntype-add'])[" + level + "]")).click();

	}

	public static void arrayMaker(String location, int pos, int type) {
		pos = pos + 1;
		System.out.println("Inside array maker");
		String strpos = String.valueOf(pos);

		if (type == 1) {
			clickOnAddArrayButton(location, strpos);
			String path = location + "." + String.valueOf(pos - 1);

			String currVal = "item " + String.valueOf(pos);

			WebElement selectTypeOfVariable = selectType(path, currVal);
			clickObject(selectTypeOfVariable);
		} else if (type == 2) {

			for (int i = 0; i < allElements.length; i++) {
				clickOnAddArrayButton(location, "1");
				String path = location + "." + String.valueOf(i);

				String currVal = "item " + String.valueOf(i + 1);

				WebElement selectTypeOfVariable = selectType(path, currVal);

				clickString(selectTypeOfVariable);

				writeInNumberString(path, allElements[i]);

			}
			goUp();
		} else {

		}

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

					String path = getPath2(nextKey);
					objcount++;
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
					Scanner sc = new Scanner(System.in);

					// System.out.println(jsonArray.getClass().getName());
					System.out.println("Enter the jason type \n1. Object \n2. String \n3. Number \n4.Empty");
					// int typeOfArray = sc.nextInt();
					int typeOfArray;
//					doing only for a perticular ticket please remove for other tickets  
					typeOfArray = array_type;

					resetobjcount();

					for (int i = 0; i < jsonArray.length(); i++) {

						String jsonArrayString = jsonArray.get(i).toString();
						JSONObject innerjsonObject = new JSONObject(jsonArrayString);

						if (arrCount.containsKey(nextKey)) {
							int c = arrCount.get(nextKey);
							arrCount.put(nextKey, ++c);

						} else {
							arrCount.put(nextKey, 0);
						}

						parent.put(nextKey, pre);

						String path = getPath2(nextKey);
						String pathmain = mainLocation + path;
						System.out.println("Parent : " + pathmain + " \npre :" + pre + " \ncurrent :" + nextKey);
						String prePath = getPrePath(pathmain);
						String preprepath = getPrePath(prePath);
						System.out.println("Previous path " + prePath);

						if (i == 0) {
							openCloseObject(preprepath);
							WebElement writeInObj = getWritingObjectWebEle(preprepath);
							clearPreVal(writeInObj);
							writeInOBject(writeInObj, nextKey);
							addButton(preprepath);
							closeObject(preprepath);
							WebElement selectTypeOfVariable = selectType(preprepath, nextKey);
							clickArray(selectTypeOfVariable);
							goUp();
							arrayMaker(prePath, i, typeOfArray);
						} else {
							arrayMaker(prePath, i, typeOfArray);
						}

						try {
							getKey(innerjsonObject, nextKey);
							System.out.println("after array :" + nextKey);
						} catch (Exception e) {
							System.out.println("array found");
							// TODO: handle exception
						}

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
					// goUp();

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
					// goUp();
					openCloseObject(prePath);
					WebElement writeInObj = getWritingObjectWebEle(prePath);
					clearPreVal(writeInObj);
					writeInOBject(writeInObj, nextKey);
					addButton(prePath);
					closeObject(prePath);
					WebElement selectTypeOfVariable = selectType(pathmain, nextKey);
					clickNumber(selectTypeOfVariable);

					parseJson2(jsonobj, nextKey, pathmain, "Number");
					// goUp();

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
				+ "	\"audio_video_quality\": {\r\n"
				+ "		\"avod_user\": {\r\n"
				+ "			\"login_concurrency\": 5,\r\n"
				+ "			\"usr_type \": \"Registered User\"\r\n"
				+ "		},\r\n"
				+ "		\"Resolution_Tag\": false,\r\n"
				+ "		\"4kDolby_Flag\": false,\r\n"
				+ "		\"video_performance_tab_count\": 5,\r\n"
				+ "		\"Multiselect\": true,\r\n"
				+ "		\"bufferingDuration\": 10,\r\n"
				+ "		\"audio_video_settings\": {\r\n"
				+ "			\"EV_Config_Value\": [{\r\n"
				+ "				\"app_player_config_sd\": {\r\n"
				+ "					\"device_resolution\": \"SD\",\r\n"
				+ "					\"ID\": 0\r\n"
				+ "				},\r\n"
				+ "				\"app_player_config_hd\": {\r\n"
				+ "					\"device_resolution\": \"HD\",\r\n"
				+ "					\"ID\": 1\r\n"
				+ "				},\r\n"
				+ "				\"app_player_config_ultra_hd\": {\r\n"
				+ "					\"device_resolution\": \"UHD\",\r\n"
				+ "					\"ID\": 3\r\n"
				+ "				},\r\n"
				+ "				\"app_player_config_full_hd\": {\r\n"
				+ "					\"device_resolution\": \"FULL_HD\",\r\n"
				+ "					\"ID\": 2\r\n"
				+ "				}\r\n"
				+ "			}],\r\n"
				+ "			\"top_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/top_icon_android_tv.png\",\r\n"
				+ "			\"title_color\": \"#FFFFFF\",\r\n"
				+ "			\"forward_icon_black\": \"\",\r\n"
				+ "			\"line_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/line_android_tab.png\",\r\n"
				+ "			\"title\": \"Settings\",\r\n"
				+ "			\"login\": {\r\n"
				+ "				\"login_success_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/login_success_icon_android_tv.png\",\r\n"
				+ "				\"login_popup_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/login_popup_bg_image_android_tv.png\"\r\n"
				+ "			},\r\n"
				+ "			\"bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/audio_bg_image_android_tv.png\",\r\n"
				+ "			\"settings_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/audio_settings_icon_android_tv.png\",\r\n"
				+ "			\"forward_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/Android-phone-forward_icon.png\",\r\n"
				+ "			\"info_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/Info_web.png\",\r\n"
				+ "			\"Audio_Language\": {\r\n"
				+ "				\"title\": \"Audio Language\",\r\n"
				+ "				\"bg_image_download_focussed\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/audio_language_bg_focussed_icon_android_tv.png\"\r\n"
				+ "			},\r\n"
				+ "			\"tick_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/audio_tick_icon_android_tv.png\",\r\n"
				+ "			\"logout\": {\r\n"
				+ "				\"logout_title\": \"Logged out\",\r\n"
				+ "				\"logout_skip_bg_image_tv\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/logout_skip_bg_image_tv_android_tv.png\",\r\n"
				+ "				\"logout_popup_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/logout_popup_bg_image_android_tv.png\",\r\n"
				+ "				\"logout_warning_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/logout_warning_icon_android_tv.png\",\r\n"
				+ "				\"logout_login_bg_image_tv\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/logout_login_bg_image_tv_android_tv.png\"\r\n"
				+ "			},\r\n"
				+ "			\"device_management\": {\r\n"
				+ "				\"logut_title\": \"Log Out\",\r\n"
				+ "				\"remove_title\": \"Remove\",\r\n"
				+ "				\"logout_confirmation_title\": \"Logout Confirmation\",\r\n"
				+ "				\"remove_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/remove_bg_image_android_tv.png\",\r\n"
				+ "				\"inactive_subtitle\": \"Inactive devices\",\r\n"
				+ "				\"logout_font_color\": \"#24BFF3\",\r\n"
				+ "				\"title\": \"Device Management\",\r\n"
				+ "				\"active_subtitle\": \"Connected devices ()\",\r\n"
				+ "				\"logout_confirmation_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/logout_confirmation_bg_image_android_tv.png\"\r\n"
				+ "			},\r\n"
				+ "			\"video_res_ladder\": {\r\n"
				+ "				\"app_player_config_hd\": [{\r\n"
				+ "						\"button_cta\": \"[sony://internal/selectPack]\",\r\n"
				+ "						\"playback_ql_title\": \"Full_HD\",\r\n"
				+ "						\"playback_ql_id\": 2,\r\n"
				+ "						\"playback_ql_render_title\": \"1080p\",\r\n"
				+ "						\"playback_q1_sn\": 1\r\n"
				+ "					},\r\n"
				+ "					{\r\n"
				+ "						\"button_cta\": \"[sony://internal/selectPack]\",\r\n"
				+ "						\"playback_ql_title\": \"UHD\",\r\n"
				+ "						\"playback_ql_id\": 3,\r\n"
				+ "						\"playback_ql_render_title\": \"2160p\",\r\n"
				+ "						\"playback_q1_sn\": 2\r\n"
				+ "					},\r\n"
				+ "					{\r\n"
				+ "						\"playback_ql_title\": \"Auto\",\r\n"
				+ "						\"playback_ql_id\": 8,\r\n"
				+ "						\"playback_ql_render_title\": \"720p\",\r\n"
				+ "						\"playback_q1_sn\": 3\r\n"
				+ "					},\r\n"
				+ "					{\r\n"
				+ "						\"button_cta\": \"[sony://internal/selectPack]\",\r\n"
				+ "						\"playback_ql_title\": \"High\",\r\n"
				+ "						\"playback_ql_id\": 6,\r\n"
				+ "						\"playback_ql_render_title\": \"720p\",\r\n"
				+ "						\"playback_q1_sn\": 4\r\n"
				+ "					},\r\n"
				+ "					{\r\n"
				+ "						\"button_cta\": \"[sony://internal/selectPack]\",\r\n"
				+ "						\"playback_ql_title\": \"Data Saver\",\r\n"
				+ "						\"playback_ql_id\": 7,\r\n"
				+ "						\"playback_ql_render_title\": \"360p\",\r\n"
				+ "						\"playback_q1_sn\": 5\r\n"
				+ "					},\r\n"
				+ "					{\r\n"
				+ "						\"button_cta\": \"[sony://internal/selectPack]\",\r\n"
				+ "						\"playback_ql_title\": \"Advance\",\r\n"
				+ "						\"playback_ql_id\": 5,\r\n"
				+ "						\"playback_q1_sn\": 6\r\n"
				+ "					}\r\n"
				+ "				]\r\n"
				+ "			},\r\n"
				+ "			\"Subtitles\": {\r\n"
				+ "				\"title\": \"Subtitles\"\r\n"
				+ "			},\r\n"
				+ "			\"stream_concurrency\": {\r\n"
				+ "				\"stream_concurrency_popup_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/stream_concurrency_popup_bg_image_android_tv.png\",\r\n"
				+ "				\"stream_concurrency_warning_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/stream_concurrency_warning_icon_android_tv.png\"\r\n"
				+ "			},\r\n"
				+ "			\"device_capability_mapping\": {\r\n"
				+ "				\"UHD\": \"2160\",\r\n"
				+ "				\"SD\": \"0\",\r\n"
				+ "				\"HD\": \"720\",\r\n"
				+ "				\"FULL_HD\": \"1080\"\r\n"
				+ "			},\r\n"
				+ "			\"video_downlaod_quality\": {\r\n"
				+ "				\"network_error_tv_option1_bg_title\": \"Switch to Auto\",\r\n"
				+ "				\"tooltip_description_download\": \"Available qualities are subject to device capabilities.\",\r\n"
				+ "				\"subscribe_color\": \"#000000\",\r\n"
				+ "				\"lock_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/lock_image_android_tv.png\",\r\n"
				+ "				\"upgrade_title\": \"UPGRADE\",\r\n"
				+ "				\"downloading_inprogress_title\": \"Downloading video...\",\r\n"
				+ "				\"title\": \"Video Quality\",\r\n"
				+ "				\"tooltip_title\": \"Video Quality\",\r\n"
				+ "				\"downlading_completed_title\": \"Your video has been downloaded\",\r\n"
				+ "				\"tootip_close_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/tootip_close_icon_web.png\",\r\n"
				+ "				\"network_error_tv_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/network_error_tv_icon_web.png\",\r\n"
				+ "				\"downloading_inprogress_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/downloading_inprogress_icon_android_tab.png\",\r\n"
				+ "				\"downlaod_warning_title_font_color\": \"#FFC900\",\r\n"
				+ "				\"advanced_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/advanced_bg_image_android_tv.png\",\r\n"
				+ "				\"network_error_tv_option1_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/network_error_tv_option1_bg_image_web.png\",\r\n"
				+ "				\"tooltip_title_color\": \"#FFC900\",\r\n"
				+ "				\"downlaoding _ad_subscribenow_font_color\": \"#111111\",\r\n"
				+ "				\"confirmation_msg_tick_bg_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/confirmation_msg_tick_bg_icon.png\",\r\n"
				+ "				\"downloading_success_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/downloading_success_icon_android_tab.png\",\r\n"
				+ "				\"crown_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/crown_icon_android_tv.png\",\r\n"
				+ "				\"confirmation_msg_tick_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/confirmation_msg_tick_icon_android_tv.png\",\r\n"
				+ "				\"network_error_tv_option2_bg_title\": \"Dismiss\",\r\n"
				+ "				\"network_selection_button_selection_image\": \"\",\r\n"
				+ "				\"subscribe_title\": \"SUBSCRIBE\",\r\n"
				+ "				\"download_preferreneces_title\": \"Download Preferences\",\r\n"
				+ "				\"downloading_ad_crown_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/Android_phone-crown_icon.png\",\r\n"
				+ "				\"downlaoding _ad_title_font_color\": \"#111111\",\r\n"
				+ "				\"network_error_tv_option2_bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/network_error_tv_option2_bg_image_web.png\",\r\n"
				+ "				\"tooltip_description\": \"Availability of the video qualities mentioned subject to device capability and network\",\r\n"
				+ "				\"confirmation_msg bg_image\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/confirmation_msg_bg_image_android_tv.png\"\r\n"
				+ "			},\r\n"
				+ "			\"back_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/New_Final_Icons_30052020/audio_back_icon_android_tv.png\",\r\n"
				+ "			\"downlaod_tick_icon\": \"\"\r\n"
				+ "		},\r\n"
				+ "		\"nodeAutoDismissTime\": 3,\r\n"
				+ "		\"Audio_Tag\": false,\r\n"
				+ "		\"Video_Tag\": false,\r\n"
				+ "		\"Video_Performance\": true\r\n"
				+ "	},\r\n"
				+ "	\"plan_comparison\": {\r\n"
				+ "		\"audio_video_quality\": {\r\n"
				+ "			\"app_player_config\": {\r\n"
				+ "				\"advertisement\": \"With Ads\",\r\n"
				+ "				\"audio\": \"Stereo\",\r\n"
				+ "				\"max_video_quality\": \"app_player_config_hd\",\r\n"
				+ "				\"accessible_devices\": \"ALL\"\r\n"
				+ "			}\r\n"
				+ "		}\r\n"
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

			System.out.println("print path " + mainLocation);

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

			arrCount.clear();

			openConfigUrl(configURL[loop]);

			try {
				JSONObject jsonObject = new JSONObject(json);
				getKey(jsonObject, firstNode);
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