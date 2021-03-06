package com.study.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.service.MemberService;
import com.study.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	//회원가입 view
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void join(MemberVO member) throws Exception {

//		log.info("[BOARD] PARAM member : {}", member.toString());
	}

	//회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String memberJoin(MemberVO member) {
		
//		log.info("[MEMBER] PARAM member : {}", member.toString());

		
		//필수항목 유효성검사
		try {
			memberService.memberJoin(member);
			
		}catch(Exception e) {
			
			log.info("[/member/join] error : {}", e);
//			return "redirect:/member/join";
		}
//		memberService.memberJoin(member);
		log.info("[MEMBER] PARAM member : {}", member.toString());
		return "redirect:/board/list";
	}
	
	//유효성검사 함수
	/*
	static boolean joinNullCheck(String str) {
		return str == null || str.trim().isEmpty(); //null값일 시 true
	}*/

	//ID 중복검사
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdCheck(String memberID) throws Exception {
		log.info("[MEMBER] PARAM memberId : {}", memberID);

		int result = memberService.idCheck(memberID);
		log.info("[MEMBER] PARAM id Check Result : {}", result);
		
		//id 중복검사
		if (result != 0) {
			return "fail"; //중복 아이디 존재
		} else {
			return "success";  //중복 아이디 존재x
		}

	}

	
	//메일 인증번호 검사
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public String mailCheck(String email) throws Exception {
		log.info("[MEMBER] PARAM mail Check", email);
//		log.info("인증번호:"+email);

		int mailCheckNumber = memberService.authRandomNumber();
		log.info("인증번호" + mailCheckNumber);
		memberService.sendMail(email, mailCheckNumber);

		String sendMailCheckNumber = Integer.toString(mailCheckNumber);

		return sendMailCheckNumber;
	}

	// 로그인 페이지 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGET() {

		//log.info("로그인 페이지 진입");

	}
	
	//로그인
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginPOST(MemberVO member, HttpServletRequest request, RedirectAttributes redirect) throws Exception {
//		System.out.println("로그인데이터: "+member);
//		
//		return null;
		
		HttpSession session = request.getSession();
		MemberVO loginMember = memberService.memberLogin(member);
		
		//db에 저장된 member id,pw값과 사용자가 입력한 member id,pw값 일치 여부 판단
		if(loginMember == null) {
			int result = 0;
			redirect.addFlashAttribute("result", result);
			return "redirect:/member/login";
		}
		session.setAttribute("member", loginMember);
		
		return "redirect:/board/list";
		}
	
	//로그아웃
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String memberLogout(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/board/list";
	}

}
