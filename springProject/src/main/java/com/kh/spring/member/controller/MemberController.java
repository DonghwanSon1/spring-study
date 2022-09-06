package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // Controller타입의 어노테이션을 빈스캐닝을 통해 자동으로 빈등록
public class MemberController {

	// MemberService mService = new MemberServiceImpl();
	
	/*
	 * 기존 객체 생성 방식
	 * 객체간의 결합도가 높아짐 (소스코드의 수정이 일어날 경우 하나하나 전부 다 바꿔줘야한다.)
	 * 서비스가 동시에 매우 많은 횟수가 요청될 경우 그 만큼 객체가 생성된다.
	 * 
	 * Spring의 DI(Dependency Injection)를 이용한 방식
	 * 객체를 생성해서 주입해줌
	 * new 라는 키워드 없이 선언만 !!! @Autowired라는 어노테이션을 반드시 사용해야함!!
	 * 
	 */
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	
	/*
	@RequestMapping(value="login.me") // RequestMapping타입의 어노테이션을 붙여줌으로써 HandlerMapping등록!
	public void loginMember() {
		
	}
	*/
	
	/*
	 * Spring에서 파라미터(요청 시 전달 값)를 받는 방법
	 * 
	 * 1. HttpServletRequest를 이용해서 전달받기 (JSP/Servlet때의 방식)
	 * 			해당 메서드의 매개변수로 HttpServletRequest를 작성해두면
	 * 			스프링 컨테이너가 해당 메서드를 호출 시 자동으로 해당 객체를 생성해서 매개변수로 주입해줌!
	 * 
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("userId : " + userId);
		System.out.println("userPwd : " + userPwd);
		
		return "main";
		
	}
	*/
	
	/*
	 * @Requestparam 어노테이션을 이용하는 방법
	 * request.getParameter("키")로 밸류를 뽑아오는 역할을 대신해주는 어노테이션
	 * value속성의 밸류로 jsp에서 작성했던 name속성값을 담으면 알아서 해당 매개변수로 받아올 수 있다.
	 * 만약, 넘어온 값이 비어있는 형태라면 defaultValue 속성으로 기본값을 지정해줄수 있다.
	 * 
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(@RequestParam(value="id", defaultValue="aaa") String userId, @RequestParam(value="pwd") String userPwd) {
		
		System.out.println("userId : " + userId);
		System.out.println("userPwd : " + userPwd);
		
		return "main";
	}
	*/
	
	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * 단, 매개변수명을 jsp의 name속성값(요청 시 전달하는 값의 키값)과 동일하게 세팅해줘야 자동으로 값이 주입됌
	 * 위에서 썼던 defaultValue 추가 속성은 사용할 수 없음
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(String id, String pwd) {
		
		System.out.println("userId : " + id);
		System.out.println("userPwd : " + pwd);
		
		return "main";
	}
	*/
	
	/*
	 * 4. 커맨드 객체 방식
	 * 
	 * 해당 메서드의 매개변수로
	 * 요청 시 전달값을 담고자하는 VO클래스의 타입을 셋팅 후
	 * 요청 시 전달값의 키값(jsp의 name속성값)을 VO클래스에 담고자하는 필드명으로 작성
	 * 
	 * ** 반드시!! name속성값과 담고자하는 필드명이 동일해야함!! + 기본생성자  + 세터
	 * 
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m) {
		
		// System.out.println("userId : " + m.getUserId());
		// System.out.println("userPwd : " + m.getUserPwd());
		Member loginUser = memberService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패 => 에러 문구를 requestScope에 담고 에러페이지로 포워딩
			System.out.println("로그인 실패");
		} else { // 로그인 성공 => loginUser를 sessionScope에 담고 메인페이지로 url 재요청
			System.out.println("로그인 성공");
		}
		
		return "main"; => /WEB-INF/views/main.jsp
	}
	*/
	
	/*
	 * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 url재요청 하는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model객체를 사용하는 방법
	 * 포워딩할 응답뷰로 전달하고자하는 데이터를 맵 형식 (key-value)으로 담을 수 있는 영역
	 * Model객체는 requestScope
	 * 단, setAttribute가 아닌 addAttribute메서드를 이용해야한다.!!
	 * 
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {
		
		Member loginUser = memberService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패 => 에러문구를 requestScope에 담아서 에러페이지로 포워딩
			
			model.addAttribute("errorMsg", "에러입니다.");
			
			// * 포워딩 방식 (파일 경로를 포함한 파일명을 제시)
			// return "XXXX";
			// servlet-context.xml의 주소의 자동완성을 등록해주는 bean(도구) : view resolver
			// 접두어 : /WEB-INF/views/
			// - 내가 적을 중간 : XXXX(==파일명)
			// 접미어 : .jsp
			
			// /WEB-INF/views/   common/errorPage   .jsp
			return "common/errorPage";
			
		} else { // 로그인 성공 => loginUser를 sessionScope에 담고 메인페이지로 url 재요청
			
			session.setAttribute("loginUser", loginUser);
			
			// * url 재요청 방식 == sendRedirect 방식 (url제시)
			// redirect : 요청할 url 
			
			// localhost:8006/spring       +          /
			
			return "redirect:/";
			
		}
	}
	 */
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 사용하는 방법
	 * Model은 데이터를 key-value세트로 담을 수 있는 공간이라고 한다면
	 * View는 응답뷰에 대한 정보를 담을 수 있는 공간
	 * 
	 * 단, Model객체는 단독 존재가 가능하지만, View객체는 그럴 수 없다.
	 * Model하고 View가 결합된 형태
	 */
	
	
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		
		// 암호화 작업 전
		/*
		Member loginUser = memberService.loginMember(m);
		
		if(loginUser == null) {
			// model.addAttirubte("키", 밸류);
			mv.addObject("errorMsg","로그인 에러입니다.");
			mv.setViewName("common/errorPage");
		} else {
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		}
		 */
		
		// 암호화 작업 후
		// Member m의 userId : 사용자가 입력한 아이디
		//			 userPwd : 사용자가 입력한 비밀번호(평문)
		
		Member loginUser = memberService.loginMember(m);
		
		// loginUser : 오로지 아이디만을 가지고 조회된 회원
		// loginUser의 userPwd필드 : DB에 기록된 암호화된 비밀번호
		
		// BCryptPasswordEncoder 객체 matches()
		// matches(평문, 암호문)을 작성하면 내부적으로 복호화 등의 작업이 이루어진다.
		// 두 구문이 일치하는지 비교 후 일치하면 true
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
			// 로그인 성공
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		} else {
			// 로그인 실패
			mv.addObject("errorMsg","로그인 실패 오류입니다.").setViewName("common/errorPage");
		}
		return mv;
	}

	
	@RequestMapping("logout.me")
	public ModelAndView logoutMember(HttpSession session, ModelAndView mv) {
		
		session.invalidate();
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		
		// /WEB-INF/views/   member/memberEnrollForm     .jsp
		
		return "member/memberEnrollForm";
	}
	
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
		
		// System.out.println(m);
		// 1. 한글 깨짐 문제 발생 => web.xml에 스프링에서 제공하는 인코딩 필터 등록
		// 2. 나이를 입력하지 않았을 경우 int자료형에 빈 문자열이 넘어와 자료형이 맞지 않는 문제 발생
		// (400 Bad Request Error 발생)
		// => Member클래스의 age필드의 int형 => String형 변경
		// 3. 비밀번호가 사용자가 입력한 있는 그대로의 평문
		// Bcrypt방식 사용할거임
		// => 스프링 시큐리티 모듈에서 제공(pom.xml에 라이브러리 추가)
		
		
		// System.out.println("평문 : " + m.getUserPwd());
		
		// 암호화 작업(암호문을 만들어내는 과정)
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		
		// System.out.println("암호문 : " + encPwd);
		
		m.setUserPwd(encPwd); // Member객체에 userPwd필드에 평문이 아닌 암호문으로 변경
		
		int result = memberService.insertMember(m);
		
		if(result > 0) { // 성공 -> 메인페이지 url재요청
			session.setAttribute("alertMsg", "성공적으로 회원가입이 되었습니다.");
			return "redirect:/";
		} else { // 실패 => 에러문구를 담아서 에러페이지로 포워딩
			model.addAttribute("errorMsg", "회원가입 실패");
			return "common/errorPage";
		}
	}
	
	
	@RequestMapping("myPage.me")
	public String myPage() {
		// /WEB-INF/views/      member/myPage           .jsp
		return "member/myPage";
	}
	
	
	@RequestMapping("update.me")
	public String updateMember(Member m, Model model, HttpSession session) {
		
		int result = memberService.updateMember(m);
		
		if(result > 0) {
			// 수정 성공
			// 마이페이지 url 재요청
			// DB로부터 수정된 회원정보를 다시 조회해서
			// session에 loginUser라는 키값으로 덮어씌워야함!!!
			session.setAttribute("loginUser", memberService.loginMember(m));
			
			// session에 일회성 알람문구도 담기
			session.setAttribute("alertMsg", "회원정보 수정 성공!!");
			
			// 다시 마이페이지 조회
			return "redirect:myPage.me";
		} else {
			// 수정 실패 => 에러문구 담아서 에러페이지로 포워딩
			model.addAttribute("errorMsg", "정보수정 실패 오류 났습니다.");
			return "common/errorPage";
		}
	}
	
	
	@RequestMapping("delete.me")
	public String deleteMember(String userId, String userPwd, Model model, HttpSession session) {
		
		// userPwd : 회원탈퇴 요청 시 사용자가 입력한 비밀번호 평문
		// session의 loginUser Member객체의 userPwd필드 : DB에 기록된 암호화된 비밀번호
		String encPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
		if(bcryptPasswordEncoder.matches(userPwd, encPwd)) {
			// 비번이 맞을 경우 => 탈퇴처리
			int result = memberService.deleteMember(userId);
			if(result > 0) {
				// 탈퇴처리 성공 => session에서 loginUser 지움, alert문구 담고 메인페이지로 넘김
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "회원탈퇴 성공했습니다.");
				return "redirect:/";
			} else {
				model.addAttribute("errorMsg", "회원 탈퇴 실패 했습니다.");
				return "common/errorPage";
			}
		} else {
			// 비번 틀림
			session.setAttribute("alertMsg", "비밀번호가 틀렸습니다!");
			return "redirect:myPage.me";
		}
	}
	
	@ResponseBody
	@RequestMapping("idCheck.me")
	public String idCheck(String checkId) {
		
		/*
		int result = memberService.idCheck(checkId);
		
		if(result > 0) { // 이미 존재하는 아이디 => 사용불가능(NNNNN)
			return "NNNNN";
		} else { // 사용가능(NNNNY)
			return "NNNNY";
		}
		*/
		
		return memberService.idCheck(checkId) > 0 ? "NNNNN" : "NNNNY";
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
