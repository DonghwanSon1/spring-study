package kh.com.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	
	public static final String SERVICE_KEY = "Wnwgvn6GcehcUmun0Fr%2FEUSw9cRIm8a4DJWSuamz%2F5CS8daU8NZV8B7aLq%2Bxdg8xw406QolmoYeOere%2BADg2vA%3D%3D";
	
	
	@ResponseBody
	@RequestMapping(value="air.do", produces="application/json; charset=UTF-8")
	public String airPollution(String location) throws IOException {
		
		// OpenAPI로 서버로 요청하는 url 만들기
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
		url += "&returnType=json";
		
		//System.out.println(url);
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		while((line = br.readLine()) != null) {
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();
		
		
		return responseText;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
