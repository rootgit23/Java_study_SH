package com.iu.start.bankbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/bankbook/*")
public class BankBookController {
	
	@Autowired
	private BankBookService bankBookService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		//ModelAndView mv = new ModelAndView();
		System.out.println("list GET 실행");
		List<BankBookDTO> ar = bankBookService.getList();
		model.addAttribute("list",ar);
		return "/bankbook/list";
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(BankBookDTO bankBookDTO) throws Exception {
		ModelAndView mv = new ModelAndView();
		System.out.println("detail GET 실행");
		bankBookDTO = bankBookService.getDetail(bankBookDTO);
		//return "/bankbook/detail";
		mv.setViewName("bankbook/detail");
		mv.addObject("detail", bankBookDTO);
		return mv;
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public void add() {
		System.out.println("add get 실행");
		//return "/bankbook/add";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ModelAndView add(BankBookDTO bankBookDTO) throws Exception {
		System.out.println("add post 실행");
		bankBookDTO.setBookNum(bankBookDTO.getBookNum());
		bankBookDTO.setBookName(bankBookDTO.getBookName());
		bankBookDTO.setBookRate(bankBookDTO.getBookRate());
		bankBookDTO.setBookSale(bankBookDTO.getBookSale());
		int result = bankBookService.setBankBook(bankBookDTO);
		if(result == 1) {
			System.out.println("성공");
		}
		else
			System.out.println("실패");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:./list.file");
		return mv;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public void update(BankBookDTO bankBookDTO,Model model) throws Exception{
		System.out.println("update GET 실행");
		bankBookDTO = bankBookService.getDetail(bankBookDTO);
		model.addAttribute("detail", bankBookDTO);
		
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(BankBookDTO bankBookDTO) throws Exception{
		bankBookDTO.setBookName(bankBookDTO.getBookName());
		bankBookDTO.setBookRate(bankBookDTO.getBookRate());
		bankBookDTO.setBookNum(bankBookDTO.getBookNum());
		int result = bankBookService.setUpdate(bankBookDTO);
		if(result == 1) {
			System.out.println("성공");
		}
		else
			System.out.println("실패");
		return "redirect:./detail.file?bookNum=" + bankBookDTO.getBookNum();
		
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(BankBookDTO bankBookDTO) throws Exception{
		bankBookDTO.setBookNum(bankBookDTO.getBookNum());
		int result = bankBookService.setDelete(bankBookDTO);
		if(result == 1) {
			System.out.println("성공");
		}
		else
			System.out.println("실패");
		return "redirect:./list.file";
	}

}
