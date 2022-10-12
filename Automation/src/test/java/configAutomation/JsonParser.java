package configAutomation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;




public class JsonParser {
	static Map<String, String> parent = new HashMap<String, String>();

	static Map<String, Integer> arrCount = new HashMap<String, Integer>();
	static Map<String, Integer> arraycountnumber = new HashMap<String, Integer>();

	static String mainLocation="root";
	static String firstNode="";
	
	
	
	public static void parseJson(JSONObject jsonobj, String key) {
		
		System.out.println(jsonobj.get(key));
	}
	

	public static void getKey(JSONObject jsonobj, String pre) {
		Iterator<?> keys;
		String nextKey;

		keys = jsonobj.keys();
		while (keys.hasNext()) {
			nextKey = (String) keys.next();
			// System.out.println(nextKey);
			// parseJson(jsonobj , nextKey);
			// System.out.println(jsonobj.get(nextKey));
			
			
			try {
				
			
				
				if (jsonobj.get(nextKey) instanceof JSONObject) {
					System.out.println();
					System.out.println(nextKey);
					System.out.println("object");

					parent.put(nextKey, pre);
					String path = "";
					String path1 = "";
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
					path1 = mainLocation+path;
					System.out.println("Parent : " + path1 + " pre " + pre +" current "+nextKey);
					path = path.substring(1);
					
					String path_1 = "";
					Boolean iterate_1 = true;
					String con_1 = nextKey;
					
					do {
						if (parent.get(con_1) != firstNode) {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
						} else {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
							iterate_1 = false;
						}
					} while (iterate_1);
					
					path_1 = path_1.substring(1);
					
					String prePath="";
					String s1=path_1;  
					String[] words=s1.split("\\.");	
					for(int i=0;i<words.length-1;i++)
					{
						prePath=prePath+"."+words[i];
					}
					prePath = mainLocation+ prePath;
				
					System.out.println("Previous path "+prePath);
					
					
					
					getKey(jsonobj.getJSONObject(nextKey), nextKey);

				} else if (jsonobj.get(nextKey) instanceof JSONArray) {

					System.out.println();
					System.out.println(nextKey);
					System.out.println("array");
					JSONArray jsonArray = jsonobj.getJSONArray(nextKey);

					for (int i = 0; i < jsonArray.length(); i++) {

						String jsonArrayString = jsonArray.get(i).toString();

						JSONObject innerjsonObject = new JSONObject(jsonArrayString);

						parent.put(nextKey, pre);

						String path = "";
						String path1 = "";
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

						path1 = mainLocation + path;

						path = path.substring(1);

						if (arrCount.containsKey(nextKey)) {
							int c = arrCount.get(nextKey);
							arrCount.put(nextKey, ++c);

						} else {
							arrCount.put(nextKey, 0);
						}

						System.out.println("Parent array : " + path1 + " pre " + pre + " current " + nextKey);

						String path_1 = "";
						Boolean iterate_1 = true;
						String con_1 = nextKey;

						do {
							if (parent.get(con_1) != firstNode) {
								path_1 = "." + con_1 + path_1;
								con_1 = parent.get(con_1);
							} else {
								path_1 = "." + con_1 + path_1;
								con_1 = parent.get(con_1);
								iterate_1 = false;
							}
						} while (iterate_1);

						path_1 = path_1.substring(1);

						String prePath = "";
						String s1 = path_1;
						String[] words = s1.split("\\.");
						for (int j = 0; j < words.length - 1; j++) {
							prePath = prePath + "." + words[j];
						}
						prePath = mainLocation + prePath;

						System.out.println("Previous path array " + prePath);

//						if (arraycountnumber.containsKey(nextKey)) {
//							int countNoOfArray = arraycountnumber.get(nextKey);
//							if (countNoOfArray == 1) {
//
//							}
//
//						} else
//
//						{
//							openCloseObject(prePath);
//							WebElement writeInObj = getWritingObjectWebEle(prePath);
//							clearPreVal(writeInObj);
//							writeInOBject(writeInObj, nextKey);
//							addButton(prePath);
//							closeObject(prePath);
//							WebElement selectTypeOfVariable = selectType(path1, nextKey);
//							clickArray(selectTypeOfVariable);
//
//							int level = incrementarrcouhnt();
//							//level=level-1;
//							String level1=String.valueOf(level);
//							clickOnAddArrayButton(path1 , level1 );

						
						arraycountnumber.put(nextKey, 1);

						try {
							System.out.println("before array recution ");
							getKey(innerjsonObject, nextKey);
							System.out.println("after array recurtion");

							// after array recurtion code

						} catch (Exception e) {
							System.out.println("its an array ");
							// TODO: handle exception
						}

				}

				} else if (jsonobj.get(nextKey) instanceof Boolean) {
					System.out.println();
					System.out.println(nextKey);
					System.out.println("boolean");

					parseJson(jsonobj, nextKey);

					parent.put(nextKey, pre);
					String path = "";
					String path1 = "";

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
							// path = "." + newcon + path;
							con = parent.get(con);
							iterate = false;
						}
					} while (iterate);

					path1 = mainLocation+path;
					System.out.println("Parent : " + path1 + " pre " + pre +" current "+nextKey);
					path = path.substring(1);
					// System.out.println("Parent 1111111: " + path +" pre "+pre);
					
					
					
					
					String path21 = "";
					String path22="";
					String s121 = path1;
					String[] words21 = s121.split("\\.");
					for (int i = 0; i < words21.length; i++) {
						if(i==0)
						{
							path21 = words21[i];
						}
						else if(i<words21.length-1)
						{
							path21=path21+"["+words21[i]+"]";
						}
						else
						{
							path22=path21+"["+words21[i]+"]";
						}
						
					}
					System.out.println("print path " + path22 + "\n print pre path "+path21);
					
					
					
					
					String path_1 = "";
					Boolean iterate_1 = true;
					String con_1 = nextKey;
					
					do {
						if (parent.get(con_1) != firstNode) {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
						} else {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
							iterate_1 = false;
						}
					} while (iterate_1);
					
					path_1 = path_1.substring(1);
					
					String prePath="";
					String s1=path_1;  
					String[] words=s1.split("\\.");	
					for(int i=0;i<words.length-1;i++)
					{
						prePath=prePath+"."+words[i];
					}
				prePath = mainLocation+ prePath;
				
					System.out.println("Previous path "+prePath);
					
					
					
					String path2 = "";
					String s12 = path1;
					String[] words2 = s12.split("\\.");
					for (int i = 0; i < words2.length-2; i++) {
						if(i==0)
						{
							path2 = words2[i];
						}
						else
						{
							path2=path2+"["+words2[i]+"]";
						}
						
					}
					System.out.println("print pre pre path " + path2);
					
					
					
					try {
					getKey(jsonobj.getJSONObject(nextKey), nextKey);
					}catch (Exception e) {
						System.out.println("handled boolean exception");
						// TODO: handle exception
					}

				} else if (jsonobj.get(nextKey) instanceof String) {
					System.out.println();
					System.out.println(nextKey);
					System.out.println("string");

					parseJson(jsonobj, nextKey);

					parent.put(nextKey, pre);
					String path = "";
					String path1 = "";

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
							// path = "." + newcon + path;
							con = parent.get(con);
							iterate = false;
						}
					} while (iterate);

					path1 =  mainLocation+ path;
					
					
					
					
					
					System.out.println("Parent : " + path1 + " pre " + pre +" current "+nextKey);
					path = path.substring(1);
					// System.out.println("Parent 1111111: " + path +" pre "+pre);
				
					
					
					
					
					String path21 = "";
					String path22="";
					String s121 = path1;
					String[] words21 = s121.split("\\.");
					for (int i = 0; i < words21.length; i++) {
						if(i==0)
						{
							path21 = words21[i];
						}
						else if(i<words21.length-1)
						{
							path21=path21+"["+words21[i]+"]";
						}
						else
						{
							path22=path21+"["+words21[i]+"]";
						}
						
					}
					System.out.println("print path " + path22 + "\n print pre path "+path21);
					
					
					
					
					
					
					
					
					
					
					String path_1 = "";
					Boolean iterate_1 = true;
					String con_1 = nextKey;
					
					do {
						if (parent.get(con_1) != firstNode) {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
						} else {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
							iterate_1 = false;
						}
					} while (iterate_1);
					
					path_1 = path_1.substring(1);
					
					String prePath="";
					String s1=path_1;  
					String[] words=s1.split("\\.");	
					for(int i=0;i<words.length-1;i++)
					{
						prePath=prePath+"."+words[i];
					}
				prePath = mainLocation+ prePath;
				
					System.out.println("Previous path "+prePath);
					
					
					
					
					String path2 = "";
					String s12 = path1;
					String[] words2 = s12.split("\\.");
					for (int i = 0; i < words2.length-2; i++) {
						if(i==0)
						{
							path2 = words2[i];
						}
						else
						{
							path2=path2+"["+words2[i]+"]";
						}
						
					}
					System.out.println("print pre pre path " + path2);
					
					String splitpath1 = path1;
					String[] allword = splitpath1.split("\\.");
					Boolean containsarray = false;
					for (int i = 0; i < allword.length; i++) 
					{
					
						
						try {
							int x=Integer.parseInt(allword[i]);
							containsarray=true;
							System.out.println(x +"is a number : flag :"+containsarray);
						
							break;
							
						}catch (Exception e) 
						{
							System.out.println(allword[i] +" is not  a number ");
						
							// TODO: handle exception
						}
						
						System.out.println(" "+allword[i]);
					}
					
					try {
					getKey(jsonobj.getJSONObject(nextKey), nextKey);
					}catch (Exception e) {
						System.out.println("handled string exception");
						// TODO: handle exception
					}

				} else if (jsonobj.get(nextKey) instanceof Number) {
					System.out.println();
					System.out.println(nextKey);
					System.out.println("Number");

					parseJson(jsonobj, nextKey);

					parent.put(nextKey, pre);
					
				
		
					
					String path = "";
					String path1 = "";
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
							// path = "." + newcon + path;
							con = parent.get(con);
							iterate = false;
						}
					} while (iterate);

					path1 =  mainLocation+ path;
					
					path = path.substring(1);
					System.out.println("Parent : " + path1 + " pre " + pre +" current "+nextKey);
					
					String path21 = "";
					String path22="";
					String dotPath="";
					String s121 = path1;
					String[] words21 = s121.split("\\.");
					for (int i = 0; i < words21.length; i++) {
						if(i==0)
						{
							path21 = words21[i];
							dotPath=words21[i];
						}
						else if(i<words21.length-1)
						{
							path21=path21+"["+words21[i]+"]";
							dotPath=dotPath+"."+words21[i];
						}
						else
						{
							path22=path21+"["+words21[i]+"]";
						}
						
					}
					
					System.out.println("print path " + path22 + "\n print pre path "+path21 + "dot pre Path " +dotPath);
					
					
				
					String path_1 = "";
					Boolean iterate_1 = true;
					String con_1 = nextKey;
					
					do {
						if (parent.get(con_1) != firstNode) {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
						} else {
							path_1 = "." + con_1 + path_1;
							con_1 = parent.get(con_1);
							iterate_1 = false;
						}
					} while (iterate_1);
					
					path_1 = path_1.substring(1);
					
					String prePath="";
					String s1=path_1;  
					String[] words=s1.split("\\.");	
					for(int i=0;i<words.length-1;i++)
					{
						prePath=prePath+"."+words[i];
					}
					prePath = mainLocation + prePath;
				
					System.out.println("Previous path "+prePath);
					
					
					
					String path2 = "";
					String s12 = path1;
					String[] words2 = s12.split("\\.");
					for (int i = 0; i < words2.length-2; i++) {
						if(i==0)
						{
							path2 = words2[i];
						}
						else
						{
							path2=path2+"["+words2[i]+"]";
						}
						
					}
					System.out.println("print pre pre path " + path2);
					
					try {
					getKey(jsonobj.getJSONObject(nextKey), nextKey);
					}catch (Exception e) {
						System.out.println("handled number exception");
						// TODO: handle exception
					}
				}
				else
				{
					System.out.println("either nool , number , string ");
				}

			} catch (Exception e) {
				System.out.println(e);
			
				//System.out.println("make it as an object");
			
				// TODO: handle exception
			}

		}

	}

	
	
	public static void main(String[] args) {
		String json = "{\r\n"
				+ "	\"main\": {\r\n"
				+ "		\"id\": \"0001\",\r\n"
				+ "		\"type\": \"donut\",\r\n"
				+ "		\"name\": \"Cake\",\r\n"
				+ "		\"ppu\": 0.55,\r\n"
				+ "		\"batters\": {\r\n"
				+ "			\"batter\": [{\r\n"
				+ "					\"id\": 1001,\r\n"
				+ "					\"type\": \"Regular\"\r\n"
				+ "				},\r\n"
				+ "				{\r\n"
				+ "					\"id\": 1002,\r\n"
				+ "					\"type\": \"Chocolate\"\r\n"
				+ "				},\r\n"
				+ "				{\r\n"
				+ "					\"id\": \"1004\",\r\n"
				+ "					\"type\": \"Devil's Food\"\r\n"
				+ "				}\r\n"
				+ "			]\r\n"
				+ "		},\r\n"
				+ "		\"topping\": [{\r\n"
				+ "				\"id\": 74001,\r\n"
				+ "				\"type\": \"None\"\r\n"
				+ "			},\r\n"
				+ "			{\r\n"
				+ "				\"id\": 5002,\r\n"
				+ "				\"type\": \"Glazed\"\r\n"
				+ "			},\r\n"
				+ "			{\r\n"
				+ "				\"id\": \"5004\",\r\n"
				+ "				\"type\": \"Maple\"\r\n"
				+ "			}\r\n"
				+ "		]\r\n"
				+ "	}\r\n"
				+ "}";

	
		String json1="{\r\n"
				+ "	\"main\": {\r\n"
				+ "		\"contentSubType\": \"LIVE_CHANNEL\",\r\n"
				+ "		\"button_configuration\": [{\r\n"
				+ "				\"type\": \"watch\",\r\n"
				+ "				\"title\": \"Watch\",\r\n"
				+ "				\"focus_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/focused_play_icon.png\",\r\n"
				+ "				\"normal_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/watch_normal.png\",\r\n"
				+ "				\"preview_title\": \"Watch $$x mins free\"\r\n"
				+ "			},\r\n"
				+ "			{\r\n"
				+ "				\"type\": \"mylist\",\r\n"
				+ "				\"title\": \"My List\",\r\n"
				+ "				\"focus_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/Add_Focused.png\",\r\n"
				+ "				\"normal_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/Add_non_focused.png\",\r\n"
				+ "				\"tick_focus_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/Tick_focused.png\",\r\n"
				+ "				\"tick_normal_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/Tick_non_focused.png\"\r\n"
				+ "			},\r\n"
				+ "			{\r\n"
				+ "				\"type\": \"trailer\",\r\n"
				+ "				\"title\": \"Watch Trailer\",\r\n"
				+ "				\"focus_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/watch_trailer_focusicon.png\",\r\n"
				+ "				\"normal_icon\": \"https://origin-staticv2.sonyliv.com/UI_icons/details/watchtrailer_normal_icon.png\"\r\n"
				+ "			}\r\n"
				+ "		]\r\n"
				+ "	}\r\n"
				+ "}";
		
		String json5 ="{\r\n"
				+ "	\"widget\": [{\r\n"
				+ "			\"title\": \"Sample Konfabulator Widget\",\r\n"
				+ "			\"name\": \"main_window\",\r\n"
				+ "			\"width\": 500,\r\n"
				+ "			\"height\": 500\r\n"
				+ "		},\r\n"
				+ "		{\r\n"
				+ "			\"src\": \"Images/Sun.png\",\r\n"
				+ "			\"name\": \"sun1\",\r\n"
				+ "			\"hOffset\": 250,\r\n"
				+ "			\"vOffset\": 250,\r\n"
				+ "			\"alignment\": \"center\"\r\n"
				+ "		},\r\n"
				+ "		{\r\n"
				+ "			\"data\": \"Click Here\",\r\n"
				+ "			\"size\": 36,\r\n"
				+ "			\"style\": \"bold\",\r\n"
				+ "			\"name\": \"text1\",\r\n"
				+ "			\"hOffset\": 250,\r\n"
				+ "			\"vOffset\": 100,\r\n"
				+ "			\"alignment\": \"center\",\r\n"
				+ "			\"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\r\n"
				+ "		}\r\n"
				+ "	]\r\n"
				+ "}";
		
		String json4 ="{\r\n"
				+ "	\"main\": {\r\n"
				+ "		\"id\": 1001,\r\n"
				+ "		\"available\": true,\r\n"
				+ "		\"ing\": [\"rice\", \"dal\", \"sambar\"],\r\n"
				+ "\r\n"
				+ "		\"batters\": {\r\n"
				+ "			\"batter\": [{\r\n"
				+ "					\"id\": 1003,\r\n"
				+ "					\"type\": \"Blueberry\"\r\n"
				+ "				},\r\n"
				+ "				{\r\n"
				+ "					\"id\": 1004,\r\n"
				+ "					\"type\": \"Devil's Food\"\r\n"
				+ "				}\r\n"
				+ "			]\r\n"
				+ "		},\r\n"
				+ "		\"topping\": [{\r\n"
				+ "				\"id\": 5001,\r\n"
				+ "				\"type\": \"None\"\r\n"
				+ "			},\r\n"
				+ "			{\r\n"
				+ "				\"id\": 5004,\r\n"
				+ "				\"type\": \"Maple\"\r\n"
				+ "			}\r\n"
				+ "		]\r\n"
				+ "	}\r\n"
				+ "}";


		try {
			JSONObject jsonObject = new JSONObject(json5);
			firstNode="main";
			getKey(jsonObject, "main");
		
		} catch (Exception e) {
			System.out.println("INVALID JSON");// TODO: handle exception
		}

		for (Map.Entry<String, String> paiEntry : parent.entrySet()) {
			System.out.println(paiEntry.getKey() + "->" + paiEntry.getValue());
		}
		System.out.println();
		for (Map.Entry<String, Integer> paiEntry : arrCount.entrySet()) {
			System.out.println(paiEntry.getKey() + "->" + paiEntry.getValue());
		}
		
		

	}

}


//(//div[@data-schemapath='root.app_player_config.playback_quality_cfg.playback_ql_options.2'])[3]//button[@class='json-editor-btn-add json-editor-btntype-add']
