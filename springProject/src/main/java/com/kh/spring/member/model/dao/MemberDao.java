package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

// Repository : 저장소
// 주로 DB(저장소)와 관련된작업(=="영속성"작업)을 처리하겠다는 뜻
@Repository
public class MemberDao {

	
	public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.loginMember", m);
	}
	
	
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}
	
	
	public int updateMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateMember", m);
	}
	
	
	public int deleteMember(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.update("memberMapper.deleteMember", userId);
	}
	
	
	public int idCheck(SqlSessionTemplate sqlSession, String checkId) {
		return sqlSession.selectOne("memberMapper.idCheck", checkId);
	}
	
	
	
	
	
	
	
	
	
	
	
}
