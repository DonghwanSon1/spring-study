package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Lombok(롬복)
 * - 자동 코드 생성 라이브러리
 * - 반복되는 getter, setter, toString등의 메서드 작성코드를 줄여주는 코드 다이어트 라이브러리
 * 
 * Lombok 설치방법
 * 1. 라이브러리 다운 후 적용(Maven pom.xml)
 * 2. 다운로드 된 jar파일을 찾아서 설치(작업할 IDE를 선택)
 * 3. IDE재실행
 * 
 * Lombok 사용 시 주의사항
 * - uName, bTitle과 같은 앞글자가 외자인 필드명은 만들지 말것!!
 * 	 필드명 작성 시 적어도 소문자 두글자 이상으로 시작할것!!
 * 
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Member {
	
	private String userId; //USER_ID	VARCHAR2(30 BYTE)
	private String userPwd; //USER_PWD	VARCHAR2(100 BYTE)
	private String userName; //USER_NAME	VARCHAR2(15 BYTE)
	private String email; //EMAIL	VARCHAR2(100 BYTE)
	private String gender; //GENDER	VARCHAR2(1 BYTE)
	private String age; //AGE	NUMBER
	private String phone; //PHONE	VARCHAR2(13 BYTE)
	private String address; //ADDRESS	VARCHAR2(100 BYTE)
	private Date enrollDate; //ENROLL_DATE	DATE
	private Date modifyDate; //MODIFY_DATE	DATE
	private String status; //STATUS	VARCHAR2(1 BYTE)
	
	

}
