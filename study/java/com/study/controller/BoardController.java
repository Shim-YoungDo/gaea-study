package com.study.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.study.service.BoardService;
import com.study.vo.BoardVO;
import com.study.vo.Criteria;
import com.study.vo.PageMakeDTO;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public void boardListGET(Model model, Criteria cri) {
		log.info("[BOARD] PARAM pagenum : {}", cri.getPageNum()); //debug로 거는게 남. -> 상용에서 불필요한 데이터 입력 파라미터 필수. 검색안되는 로그는 쓰레기
		
		log.info("[BOARD] PARAM amount : {}", cri.getAmount()); 

	
//		model.addAttribute("list", service.getList());
//		cri.setAmount(10);
		
		//페이징 적용
		model.addAttribute("list", service.getBoardPagingList(cri));
		
		int boardTotal = service.getBoardTotal(cri);
		
		PageMakeDTO pageMake = new PageMakeDTO(cri, boardTotal);
		
		model.addAttribute("pageMake", pageMake);
	}
	
	
	@RequestMapping(value="/registeView", method=RequestMethod.GET)
	public void enrollView(Model model, Criteria cri) {
		//log.info("게시판 등록페이지 진입");
		model.addAttribute("cri", cri);
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String boardRegiste(BoardVO board) {
		log.info("[BOARD] PARAM board : {} : ", board.toString());
		service.boardRegister(board);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/getPage", method=RequestMethod.GET)
	public void getPage(int boardNumber, Model model, Criteria cri) {
		//log.info("게시글 조회페이지 진입");
		model.addAttribute("pageInfo", service.boardInquire(boardNumber));
		
		model.addAttribute("cri", cri);
	}
	
	@RequestMapping(value="/modifyView", method=RequestMethod.GET)
	public void modifyView(int boardNumber, Model model, Criteria cri) {
		//log.info("수정페이지 진입");
		model.addAttribute("pageInfo", service.boardInquire(boardNumber));
		
		model.addAttribute("cri", cri);
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyPost(BoardVO board) {
		service.boardModify(board);
//		rttr.addFlashAttribute("result", "modify success");
		log.info("[BOARD] PARAM modify : {} : ", board.toString());
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deletePOST(int boardNumber) {
		
		service.boardDelete(boardNumber);
		
		//log.info("삭제완료");
		
		return "redirect:/board/list";
	}

}
