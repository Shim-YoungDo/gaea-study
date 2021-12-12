package com.study.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void join(MemberVO member) throws Exception {

//		log.info("[BOARD] PARAM member : {}", member.toString());
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String memberJoin(MemberVO member, @RequestParam Map<String, Object> param) {
		
//		log.info("[MEMBER] PARAM member : {}", member.toString());

		try {
			
			String id = (String) param.get("memberID");
			String pw = (String) param.get("memberPW");
			String name = (String) param.get("memberName");
			String mail = (String) param.get("memberMail");
		
//			memberService.memberJoin(member);
			
			if(stringNullCheck(id) == true) {
				throw new Exception();
			}
			if(stringNullCheck(pw) == true) {
				throw new Exception();
			}
			if(stringNullCheck(name) == true) {
				throw new Exception();
			}
			if(stringNullCheck(mail) == true) {
				throw new Exception();
			}
			else {
				memberService.memberJoin(member);
			}
//			memberService.memberJoin(member);
			
		}catch(Exception e) {
			log.info("[/member/join] error : {}", e);
//			return "redirect:/member/join";
		}
		return "redirect:/board/list";
	}
	
	static boolean stringNullCheck(String str) {
		return str == null || str.trim().isEmpty(); //null값일 시 true
	}

	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdCheck(String memberID) throws Exception {
		log.info("[MEMBER] PARAM memberId : {}", memberID);

		int result = memberService.idCheck(memberID);
		log.info("[MEMBER] PARAM id Check Result : {}", result);
		if (result != 0) {
			return "fail";
		} else {
			return "success";
		}

	}

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

		log.info("로그인 페이지 진입");

	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginPOST(MemberVO member, HttpServletRequest request, RedirectAttributes redirect) throws Exception {
//		System.out.println("로그인데이터: "+member);
//		
//		return null;
		
		HttpSession session = request.getSession();
		MemberVO loginMember = memberService.memberLogin(member);
		
		if(loginMember == null) {
			int result = 0;
			redirect.addFlashAttribute("result", result);
			return "redirect:/member/login";
		}
		session.setAttribute("member", loginMember);
		
		return "redirect:/board/list";
		
//		try {
//			if(loginMember == null) {
//				
//			}
//		}catch(Exception e) {
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String memberLogout(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/board/list";
	}

}
