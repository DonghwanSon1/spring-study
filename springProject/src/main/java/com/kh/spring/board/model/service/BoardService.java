package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {
	
	
	// 게시판 리스트 조회 서비스 + 페이징 처리
	// 게시글 총 갯수 조회
	int selectListCount();
	// 게시글 리스트 조회
	ArrayList<Board> selectList(PageInfo pi);
	
	// 게시글 작성하기 서비스(insert)
	int insertBoard(Board b);
	
	// 게시글 상세조회 서비스
	Board selectBoard(int boardNo);
	
	// 게시글 조회수 증가
	int increaseCount(int boardNo);
	
	 
	// 게시글 삭제 서비스
	int deleteBoard(int boardNo);
	
	// 게시글 수정 서비스
	int updateBoard(Board b);
	
	// 댓글 리스트 조회 서비스
	ArrayList<Reply> selectReplyList(int bno);
	
	// 댓글 작성 서비스
	int insertReply(Reply r);
	
	// Top-N분석
	ArrayList<Board> selectTopBoardList();
	
	
	
	
	
	
	
	
	
	
	
	
	

}
