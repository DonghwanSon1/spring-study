package com.kh.spring.board.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 헤더의 게시판 클릭시 => list.bo(기본적으로 1번 페이지를 요청)
	// 페이징바 클릭 시 => list.bo?currentPage=요청하는 페이지의 번호
	/*
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, Model model) {
		//System.out.println("cpage : " + currentPage);
		
//		int listCount = boardService.selectListCount();
//		int pageLimit = 10;
//		int boardLimit = 5;
		
		PageInfo pi = Pagination.getPageInfo(boardService.selectListCount(), currentPage, 10, 5);
		
		ArrayList<Board> list = boardService.selectList(pi);
		
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		
		// forwarding (/WEB-INF/views/   board/boardListView    .jsp
		return "board/boardListView";
	}
	*/
	
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue = "1") int currentPage, ModelAndView mv) {
		
		PageInfo pi = Pagination.getPageInfo(boardService.selectListCount(), currentPage, 10, 5);
		
		//ArrayList<Board> list = boardService.selectList(pi);
		
		mv.addObject("pi", pi)
		  .addObject("list", boardService.selectList(pi))
		  .setViewName("board/boardListView");
		
		return mv;
	}
	
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		
		//System.out.println(b);
		//System.out.println(upfile);
		
		// 전달된 파일이 있을 경우 => 파일명 수정 후 서버 업로드 => 원본명, 서버 업로드된 경로를 b에 담기(파일이 있을 때만)
		
		if(!upfile.getOriginalFilename().equals("")) { // getOriginalFilename() == filename 필드값을 반환해줌
			/*
			// 파일명  수정 후 서버에 업로드시키기("flower4.jpg" => "2022~~~~.jpg")
			String originName = upfile.getOriginalFilename();
			// 2022072202251238012(년월일시분초)
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			// 12312(5자리 랜덤값)
			int ranNum = (int)(Math.random() * 90000) + 10000;
			// 확장자
			String ext = originName.substring(originName.lastIndexOf("."));
			
			String changeName = currentTime + ranNum + ext;
			
			//System.out.println(changeName);
			
			// 업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			*/
			String changeName = saveFile(upfile, session);
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		// 넘어온 첨부파일이 없을 경우 b : 제목, 작성자, 내용
		// 넘어온 첨부파일이 있을 경우 b : 제목, 작성자, 내용  + 파일원본명, 파일저장경로
		int result = boardService.insertBoard(b);
		
		if(result > 0) {
			// 성공 => 게시글 리스트 페이지(boardListView.jsp)
			session.setAttribute("alertMsg", "게시글 작성하였습니다.");
			return "redirect:list.bo";
			
		} else {
			// 실패 => 에러페이지
			model.addAttribute("errorMsg", "게시글 작성 오류입니다.");
			return "common/errorPage";
		}
	}
	
	
	public String saveFile(MultipartFile upfile, HttpSession session) { // => 실제 넘어온 파일을 이름을 변경해서 서버에 업로드
		
		// 파일명  수정 후 서버에 업로드시키기("flower4.jpg" => "2022~~~~.jpg")
		String originName = upfile.getOriginalFilename();
		// 2022072202251238012(년월일시분초)
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 12312(5자리 랜덤값)
		int ranNum = (int)(Math.random() * 90000) + 10000;
		// 확장자
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		
		//System.out.println(changeName);
		
		// 업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return changeName;
	}
	
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(ModelAndView mv, int bno, HttpSession session, Model model) {
	
		int result = boardService.increaseCount(bno);

		if(result > 0) {
			Board b = boardService.selectBoard(bno);
			mv.addObject("b", b).setViewName("board/boardDetailView");
		} else {
			mv.addObject("eroorMsg", "상세조회 실패!").setViewName("common/errorPage");
		}
		return mv;
	}
	
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String filePath, HttpSession session, Model model) {
		
		int result = boardService.deleteBoard(bno);
		
		if(result > 0) {
			// 삭제 성공 , 만약에 첨부파일이 있엇을 경우 삭제하기
			if(!filePath.equals("")) {
				// 기존에 존재하는 첨부파일을 삭제
				// "resources/uploadFiles/xxxxx.jpg" 요걸 찾으려면 물리적인 경로가 필요함 -> session
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			// 게시판 리스트페이지로 list.bo url재요청
			session.setAttribute("alertMsg", "게시글 삭제하였습니다");
			return "redirect:list.bo";
		}else {
			model.addAttribute("errorMsg", "게시글 삭제 실패 오류");
			return "common/errorPage";
		}
	}
	
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {
		
		// 현재 내가 수정하기를 클릭한 게시글에 대한 정보를 가지고
		model.addAttribute("b", boardService.selectBoard(bno));
		return "board/boardUpdateForm";
	}
	
	
	@RequestMapping("update.bo")
	public String updateBoard(@ModelAttribute/*->가시성을 위해 쓸 수 있음(안적어도됌) */ Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
		// 새로 첨부파일이 넘어온 경우
		if(!reupfile.getOriginalFilename().equals("")) {
			// 기존에 첨부파일이 있었다면??    ==> 기존의 첨부파일을 지우기
			if(b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			// 새로 넘어온 첨부파일을 서버에 업로드 시키기
			// saveFile()을 호출해서 현재 넘어온 첨부파일을 서버에 저장시키자!
			String changeName = saveFile(reupfile, session);
			
			// b라는 Board객체에 새로운 정보(원본명, 저장경로) 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		/*
		 * b에 boardTitle, boardContent
		 * 
		 * 1. 새로 첨부된 파일 X, 기존 첨부 파일 X
		 * 		=> originName : null, changeName = null
		 * 
		 * 2. 새로 첨부된 파일 X, 기존 첨부 파일 O
		 * 		=> originName : 기존 첨부 파일의 이름, changeName : 기존 첨부파일의 경로
		 * 
		 * 3. 새로 첨부된 파일 O, 기존 첨부 파일 X
		 * 		=> originName : 새로 첨부 파일의 이름, changeName : 새로 첨부파일의 경로
		 * 
		 * 4. 새로 첨부된 파일 O, 기존 첨부 파일 O
		 * 		=> originName : 새로 첨부 파일의 이름, changeName : 새로 첨부파일의 경로
		 * 		
		 */
		
		int result = boardService.updateBoard(b);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "게시글 수정하셨습니다");
			return "redirect:detail.bo?bno=" + b.getBoardNo();
		} else {
			model.addAttribute("errorMsg", "수정 오류입니다.");
			return "common/errorPage";
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="rlist.bo", produces="application/json; charset=UTF-8")
	public String ajaxSelectReplyList(int bno) {
		
		//ArrayList<Reply> list = boardService.selectReplyList(bno);
		
		return new Gson().toJson(boardService.selectReplyList(bno));
		
	}
	
	
	@ResponseBody
	@RequestMapping("rinsert.bo")
	public String ajaxInsertReply(Reply r) {
		// 성공했을때는 success 실패했을때는 fail
		
		return boardService.insertReply(r) > 0 ? "success" : "fail";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "topList.bo", produces="application/json; charset=UTF-8")
	public String ajaxTopBoardList() {
		
		return new Gson().toJson(boardService.selectTopBoardList());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
