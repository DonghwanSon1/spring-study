package com.kh.ajax.controller;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

@Controller
public class AjaxController {

	
	/*
	 * 1. HttpServletResponse 객체로 응답데이터 응답하기
	 * 
	 */
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1 (String name, int age, HttpServletResponse response) throws IOException { // 파싱할때 무조건 값이 존재해야함!!
		
		System.out.println(name);
		System.out.println(age);
		// 요청처리를 위한 서비스 호출
		
		// 요청처리가 끝났다는 가정하에 요청한 그 페이지에 응답할 데이터가 있을 경우??
		String responseData = "응답 문자열 : " + name + "은(는)" + age + "살 입니다.";
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseData);
	}
	*/
	
	
	
	/*
	 * 2. 응답할 데이터를 문자열로 리턴
	 * 	  => HttpServletResponse를 안쓸 수 있음
	 * 문자열을 리턴하면? => 포워딩 => 응답뷰로 인식해서 해당 뷰페이지를 찾음
	 * 따라서 내가 리턴하는 문자열이 응답뷰가 아닌 응답데이터야 라는걸 선언하는 어노테이션 "@ResponseBody"를 붙여야함
	 */
	/*
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		String responseData = "응답 문자열 : " + name + "은(는)" + age + "살 입니다.";
		
		return responseData;
		// /WEB-INF/views/            응답문자열 : 홍길동 은(는) 10살 입니다.         .jsp
	}
	*/
	
	/*
	// 다수의 응답데이터가 있을 경우에는??
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		
		// 요청처리가 끝났다는 가정하에 데이터 응답
		//response.setContentType("text/html; charset=UTF-8");
		//response.getWriter().print(name);
		//response.getWriter().print(age);
		// 연이어서 출력한 데이터가 하나의 문자열로 만들어져있음
		
		// JSON(JavaScript Object Notation)형태로 담아서 응답
		// JSONArray -> [값, 값, 값] => 자바에서의 ArrayList와 유사
		// JSONObject => {키:값, 키:값, 키:값} => 자바에서의 HashMap과 유사
		
		// 첫번째 방법
		/*
		JSONArray jArr = new JSONArray();
		jArr.add(name); // ["홍길동"]
		jArr.add(age); // ["홍길동", 10]
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jArr);
		*/
		
		// 두번째 방법
		/*
		JSONObject jObj = new JSONObject();
		jObj.put("name", name); // {name : "홍길동"}
		jObj.put("age", age); // {name : "홍길동" , age : 10}
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);
	}
	  */
	
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		JSONObject jObj	= new JSONObject();
		jObj.put("name", name); // {name : "홍길동"}
		jObj.put("age", age); // {name : "홍길동" , age : 10}
		
		return jObj.toJSONString();
	}
	
	/*
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		// 잘됐다고 치면
		//Member m = memberSerivce.selectMember(num);
		
		Member m = new Member("user01","pass01", "홍길동", 20, "01012345678");
		
		// JSON형태로 만들어서 응답
		JSONObject jObj = new JSONObject();
		
		jObj.put("userId", m.getUserId());
		jObj.put("userPwd", m.getUserPwd());
		jObj.put("userName", m.getUserName());
		jObj.put("age", m.getAge());
		jObj.put("phone", m.getPhone()); // <- 이작업을 하기가 귀찮다
		
		return jObj.toJSONString();
	}
	*/
	
	
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		Member m = new Member("user01","pass01", "홍길동", 20, "01012345678");

		
		return new Gson().toJson(m); // 키값은 내부적으로 Member객체의 필드명으로 알아서 잡힌다.
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "ajax3.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod3() {
		
		//ArrayList<Member> list = memberService.selectList();
		
		ArrayList<Member> list = new ArrayList();
		list.add(new Member("user01", "pass01", "홍길동", 10, "01022223333"));
		list.add(new Member("user02", "pass02", "김길동", 20, "01011113333"));
		list.add(new Member("user03", "pass03", "마길동", 30, "01033333333"));
		
		return new Gson().toJson(list); // "[{}, {}, {}]"
		
	}
	
	
	
	
	
	
	
	
	
}
