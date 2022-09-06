package kh.com.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kh.com.opendata.model.vo.AirVO;

public class Run {
	
	
	public static final String SERVICE_KEY = "Wnwgvn6GcehcUmun0Fr%2FEUSw9cRIm8a4DJWSuamz%2F5CS8daU8NZV8B7aLq%2Bxdg8xw406QolmoYeOere%2BADg2vA%3D%3D";

	public static void main(String[] args) throws IOException {

		
		// OpenAPI로 서버로 요청하는 url 만들기
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8");
		url += "&returnType=json";
		
		//System.out.println(url);
		
		// 자바코드로 요청보내야함
		
		// ** HttpURLConnection 객체를 활용해서 OpenAPI요청 절차 **
		// 1. 요청하고자하는 url을 전달하면서 java.net.URL 객체 생성
		URL requestUrl = new URL(url);
		
		// 2. 1번과정으로 생성된 URL객체 가지고 HttpURLConnection 객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		// 3. 요청에 필요한 Header설정하기
		urlConnection.setRequestMethod("GET");
		
		// 4. 해당 OpenAPI서버로 요청을 보낸 후 입력데이터로 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		while((line = br.readLine()) != null) {
			responseText += line;
		}
		
		//System.out.println(responseText);
		
		// JSONObjcet, JSONArray -> JSON라이브러리에서 제공하는 애들 밑에 애들하고 다르다.
		// JsonObject, JsonArray, JsonElement 이용해서 파싱할 수 있음 -> GSON에서 제공
		// 각각의 item정보를 -> AirVO에 담고 => ArrayList에 차곡차곡 쌓기
		
		/*
		{"response":
					{"body":
							{"totalCount":40,
									"items":
											[
											{"so2Grade":"1",
											"coFlag":null,
											"khaiValue":"47",
											"so2Value":"0.004",
											"coValue":"0.6",
											"pm10Flag":null,
											"o3Grade":"1",
											"pm10Value":"19",
											"khaiGrade":"1",
											"sidoName":"서울",
											"no2Flag":null,
											"no2Grade":"1",
											"o3Flag":null,
											"so2Flag":null,
											"dataTime":"2022-07-29 10:00",
											"coGrade":"1",
											"no2Value":"0.022",
											"stationName":"중구",
											"pm10Grade":"1",
											"o3Value":"0.024"},{"so2Grade":"1","coFlag":null,"khaiValue":"43","so2Value":"0.003","coValue":"0.5","pm10Flag":null,"o3Grade":"1","pm10Value":"36","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.017","stationName":"한강대로","pm10Grade":"1","o3Value":"0.012"},{"so2Grade":"1","coFlag":null,"khaiValue":"50","so2Value":"0.003","coValue":"0.4","pm10Flag":null,"o3Grade":"1","pm10Value":"16","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.014","stationName":"종로구","pm10Grade":"1","o3Value":"0.030"},{"so2Grade":"1","coFlag":null,"khaiValue":"45","so2Value":"0.004","coValue":"0.5","pm10Flag":null,"o3Grade":"1","pm10Value":"20","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.027","stationName":"청계천로","pm10Grade":"1","o3Value":"0.021"},{"so2Grade":"1","coFlag":null,"khaiValue":"38","so2Value":"0.004","coValue":"0.5","pm10Flag":null,"o3Grade":"1","pm10Value":"20","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.023","stationName":"종로","pm10Grade":"1","o3Value":"0.021"},{"so2Grade":"1","coFlag":null,"khaiValue":"47","so2Value":"0.003","coValue":"0.3","pm10Flag":null,"o3Grade":"1","pm10Value":"26","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.025","stationName":"용산구","pm10Grade":"1","o3Value":"0.026"},{"so2Grade":"1","coFlag":null,"khaiValue":"42","so2Value":"0.003","coValue":"0.4","pm10Flag":null,"o3Grade":"1","pm10Value":"17","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.012","stationName":"광진구","pm10Grade":"1","o3Value":"0.025"},{"so2Grade":"1","coFlag":null,"khaiValue":"50","so2Value":"0.003","coValue":"0.3","pm10Flag":null,"o3Grade":"1","pm10Value":"23","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.014","stationName":"성동구","pm10Grade":"1","o3Value":"0.030"},{"so2Grade":null,"coFlag":null,"khaiValue":"40","so2Value":"-","coValue":"0.6","pm10Flag":null,"o3Grade":"1","pm10Value":"16","khaiGrade":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":"자료이상","dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.024","stationName":"강변북로","pm10Grade":"1","o3Value":"0.015"},{"so2Grade":"1","coFlag":null,"khaiValue":"51","so2Value":"0.002","coValue":"0.3","pm10Flag":null,"o3Grade":"2","pm10Value":"21","khaiGrade":"2","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"so2Flag":null,"dataTime":"2022-07-29 10:00","coGrade":"1","no2Value":"0.013","stationName":"중랑구","pm10Grade":"1","o3Value":"0.031"}],"pageNo":1,"numOfRows":10},"header":{"resultMsg":"NORMAL_CODE","resultCode":"00"}}}
	  */
		
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		//System.out.println(totalObj);
		JsonObject responseObj = totalObj.getAsJsonObject("response"); // response 속성에 접근 : {} JsonObject
		//System.out.println(responseObj);
		JsonObject bodyObj = responseObj.getAsJsonObject("body"); // body속성에 접근 : {} JsonObject
		//System.out.println(bodyObj);
		int totalCount = bodyObj.get("totalCount").getAsInt(); // totalCount 속성에 접근 : int
		//System.out.println(totalCount);
		JsonArray itemArr = bodyObj.getAsJsonArray("items"); // items 속성 접근 : [] JsonArray
		//System.out.println(itemArr);
		
		ArrayList<AirVO> list = new ArrayList();
		for(int i = 0; i < itemArr.size(); i++) {
			
			JsonObject item = itemArr.get(i).getAsJsonObject();
			//System.out.println(item);
			
			AirVO air = new AirVO();
			air.setStationName(item.get("stationName").getAsString());
			air.setDataTime(item.get("dataTime").getAsString());
			air.setKhaiValue(item.get("khaiValue").getAsString());
			air.setPm10Value(item.get("pm10Value").getAsString());
			air.setSo2Value(item.get("so2Value").getAsString());
			air.setCoValue(item.get("coValue").getAsString());
			air.setNo2Value(item.get("no2Value").getAsString());
			air.setO3Value(item.get("o3Value").getAsString());
			
			list.add(air);
		}
		
		// 5. 다 사용한 스트림 반납
		br.close();
		urlConnection.disconnect();
		
		for(AirVO a : list) {
			System.out.println(a);
		}
		
		
		
		
		
		
		
		
	}

}
