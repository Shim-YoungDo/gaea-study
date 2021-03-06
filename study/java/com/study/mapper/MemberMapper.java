package com.study.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.study.vo.MemberVO;

@Mapper
public interface MemberMapper {

	//회원가입
	public void memberJoin(MemberVO member);
	
	//아이디 중복 체크
	public int idCheck(String memberID);
	
	//로그인
	public MemberVO memberLogin(MemberVO member);
}
